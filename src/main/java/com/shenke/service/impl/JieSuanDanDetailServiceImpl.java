package com.shenke.service.impl;

import com.shenke.entity.JieSuanDanDetail;
import com.shenke.repository.JieSuanDanDetailRepository;
import com.shenke.service.JieSuanDanDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("jieSuanDanDetailService")
public class JieSuanDanDetailServiceImpl implements JieSuanDanDetailService {

    @Autowired
    private JieSuanDanDetailRepository jieSuanDanDetailRepository;

    @Override
    public void adds(List<JieSuanDanDetail> jieSuanDanDetails) {
        jieSuanDanDetailRepository.save(jieSuanDanDetails);
    }

    @Override
    public List<JieSuanDanDetail> selectByJieSuanDanId(Integer id) {
        return jieSuanDanDetailRepository.selectByJieSuanDanId(id);
    }
}
