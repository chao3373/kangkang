package com.shenke.service.impl;

import com.shenke.entity.JieSuanDan;
import com.shenke.repository.JieSuanDanRepository;
import com.shenke.service.JieSuanDanService;
import com.shenke.util.DateUtil;
import com.shenke.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.util.List;

@Service("jieSuanDan")
public class JieSuanDanServiceImpl implements JieSuanDanService {

    @Autowired
    private JieSuanDanRepository jieSuanDanRepository;

    @Override
    public Integer add(JieSuanDan jieSuanDan) {
        return jieSuanDanRepository.save(jieSuanDan).getId();
    }

    /**
     * 根据条件查询结算单
     *
     * @param clientName
     * @param startdate
     * @param enddate
     * @return
     */
    @Override
    public List<JieSuanDan> select(String clientName, String startdate, String enddate) {
        return jieSuanDanRepository.findAll(new Specification<JieSuanDan>() {
            @Override
            public Predicate toPredicate(Root<JieSuanDan> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (StringUtil.isNotEmpty(clientName)){
                    predicate.getExpressions().add(cb.equal(root.get("clientName"), clientName));
                }
                if (StringUtil.isNotEmpty(startdate)){
                    try {
                        predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("data"), DateUtil.getDate(startdate + " 00:00:00")));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (StringUtil.isNotEmpty(enddate)){
                    try {
                        predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("data"), DateUtil.getDate(enddate + " 23:59:59")));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                return predicate;
            }
        });
    }
}
