package com.shenke.controller.admin;

import com.shenke.entity.JieSuanDanDetail;
import com.shenke.service.JieSuanDanDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/jiesuandandetail")
public class JieSuanDanDetailAdminController {

    @Autowired
    private JieSuanDanDetailService jieSuanDanDetailService;

    @RequestMapping("/selectByJieSuanDanId")
    public Map<String, Object> selectByJieSuanDanId(Integer id){
        Map<String, Object> map = new HashMap<>();
        List<JieSuanDanDetail> list = jieSuanDanDetailService.selectByJieSuanDanId(id);
        map.put("success", true);
        map.put("rows", list);
        return map;
    }
}
