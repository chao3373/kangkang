package com.shenke.service;

import com.shenke.entity.JieSuanDanDetail;

import java.util.List;

public interface JieSuanDanDetailService {
    void adds(List<JieSuanDanDetail> jieSuanDanDetails);

    /***
     * 根据结算单id查询结算单详情
     * @param id
     * @return
     */
    List<JieSuanDanDetail> selectByJieSuanDanId(Integer id);
}
