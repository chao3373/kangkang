package com.shenke.repository;

import com.shenke.entity.JieSuanDanDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JieSuanDanDetailRepository extends JpaSpecificationExecutor<JieSuanDanDetail>, JpaRepository<JieSuanDanDetail, Integer> {

    /**
     * 根据结算单id查询结算单详情
     * @param id
     * @return
     */
    @Query(value = "select * from t_jiesuandandetail where jiesuandan_id = ?1", nativeQuery = true)
    List<JieSuanDanDetail> selectByJieSuanDanId(Integer id);
}
