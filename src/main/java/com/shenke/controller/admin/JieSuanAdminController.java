package com.shenke.controller.admin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shenke.entity.JieSuanDan;
import com.shenke.entity.JieSuanDanDetail;
import com.shenke.service.JieSuanDanDetailService;
import com.shenke.service.JieSuanDanService;
import com.shenke.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/jiesuan")
public class JieSuanAdminController {

    @Autowired
    private JieSuanDanService jieSuanDanService;

    @Autowired
    private JieSuanDanDetailService jieSuanDanDetailService;

    @RequestMapping("/saveJieSuan")
    public Map<String, Object> saveJieSuan(String saleDate, String clientname, String chehao, String songhuoren, String tel, String fuzeren, String tianzhiren, String baoguanyuan, String xiaoshouyuan, String lankman, String jieSuanJson, Double yukuan, Double qiankuan, Double xianjin, Double piaoju, Double jyukuan, Double jqiankuan) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        JieSuanDan jieSuanDan = new JieSuanDan();
        if (StringUtil.isNotEmpty(saleDate)) {
            jieSuanDan.setData(new SimpleDateFormat("yyyy-MM-dd").parse(saleDate));
        }
        if (StringUtil.isNotEmpty(clientname)) {
            jieSuanDan.setClientName(clientname);
        }
        if (StringUtil.isNotEmpty(chehao)) {
            jieSuanDan.setChehao(chehao);
        }
        if (StringUtil.isNotEmpty(songhuoren)) {
            jieSuanDan.setSonghuoren(songhuoren);
        }
        if (StringUtil.isNotEmpty(tel)) {
            jieSuanDan.setLankman(tel);
        }
        if (StringUtil.isNotEmpty(fuzeren)) {
            jieSuanDan.setFuzeren(fuzeren);
        }
        if (StringUtil.isNotEmpty(tianzhiren)) {
            jieSuanDan.setTianzhiren(tianzhiren);
        }
        if (StringUtil.isNotEmpty(baoguanyuan)) {
            jieSuanDan.setBaoguanyuan(baoguanyuan);
        }
        if (StringUtil.isNotEmpty(xiaoshouyuan)) {
            jieSuanDan.setXiaoshouyuan(xiaoshouyuan);
        }
        if (StringUtil.isNotEmpty(lankman)) {
            jieSuanDan.setShouhuoren(lankman);
        }
        if (yukuan != null) {
            jieSuanDan.setYukuan(yukuan);
        }
        if (qiankuan != null) {
            jieSuanDan.setQiankuan(qiankuan);
        }
        if (xianjin != null) {
            jieSuanDan.setXianjin(xianjin);
        }
        if (piaoju != null) {
            jieSuanDan.setPiaoju(piaoju);
        }
        if (jyukuan != null) {
            jieSuanDan.setJyukuan(jyukuan);
        }
        if (jqiankuan != null) {
            jieSuanDan.setJqiankuan(jqiankuan);
        }
        jieSuanDan.setData(new Date());
        Integer jieSuanDanId = jieSuanDanService.add(jieSuanDan);
        Gson gson = new Gson();
        List<JieSuanDanDetail> plgList = (List)gson.fromJson(jieSuanJson, (new TypeToken<List<JieSuanDanDetail>>() {
        }).getType());
        for (JieSuanDanDetail jieSuanDanDetail : plgList) {
            jieSuanDanDetail.setJieSuanDan(new JieSuanDan(jieSuanDanId));
        }
        jieSuanDanDetailService.adds(plgList);
        System.out.println(plgList);
        map.put("success", true);
        return map;
    }
}
