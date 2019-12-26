package com.shenke.controller.admin;

import com.shenke.entity.JieSuanDan;
import com.shenke.service.JieSuanDanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/jiesuandan")
public class JieSuanDanAdminController {

    @Autowired
    private JieSuanDanService jieSuanDanService;

    @RequestMapping("/select")
    public Map<String, Object> select(String clientName, String startdate, String enddate){
        Map<String, Object> map = new HashMap<>();
        List<JieSuanDan> list = jieSuanDanService.select(clientName, startdate, enddate);
        map.put("success", true);
        map.put("rows", list);
        return map;
    }
}
