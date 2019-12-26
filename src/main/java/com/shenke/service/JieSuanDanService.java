package com.shenke.service;

import com.shenke.entity.JieSuanDan;

import java.util.List;

public interface JieSuanDanService {
    Integer add(JieSuanDan jieSuanDan);

    List<JieSuanDan> select(String clientName, String startdate, String enddate);
}
