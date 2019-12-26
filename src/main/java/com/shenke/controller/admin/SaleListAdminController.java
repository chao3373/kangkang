//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.shenke.controller.admin;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shenke.entity.Clerk;
import com.shenke.entity.Client;
import com.shenke.entity.Log;
import com.shenke.entity.SaleList;
import com.shenke.entity.SaleListProduct;
import com.shenke.entity.Sell;
import com.shenke.service.LogService;
import com.shenke.service.SaleListProductService;
import com.shenke.service.SaleListService;
import com.shenke.service.UserService;
import com.shenke.util.DateUtil;
import com.shenke.util.StringUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/admin/saleList"})
public class SaleListAdminController {
    @Resource
    private SaleListService saleListService;
    @Resource
    private UserService userService;
    @Resource
    private SaleListProductService saleListProductService;
    @Resource
    private LogService logService;

    public SaleListAdminController() {
    }

    @RequestMapping({"/genCode"})
    public String genCode() throws Exception {
        StringBuffer code = new StringBuffer("XS");
        code.append(DateUtil.getCurrentDateStr());
        String saleNumber = this.saleListService.getTodayMaxSaleNumber();
        if (saleNumber != null) {
            code.append(StringUtil.formatCode(saleNumber));
        } else {
            code.append("0001");
        }

        return code.toString();
    }

    @RequestMapping({"/save"})
    @RequiresPermissions({"销售订货单"})
    public Map<String, Object> save(String saleDate, String saleNumber, Integer clientId, Integer sellId, Integer clerkId, String lankman, String tel, String address, String deliveryDate, String goodsJson, Double zongjine) throws Exception {
        System.out.println("总金额");
        System.out.println(zongjine);
        System.out.println(goodsJson);
        Map<String, Object> map = new HashMap();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SaleList saleList = new SaleList();
        saleList.setSaleDate(sdf.parse(saleDate));
        if (clerkId != null) {
            Clerk clerk = new Clerk();
            clerk.setId(clerkId);
            saleList.setClerk(clerk);
        }

        if (sellId != null) {
            Sell sell = new Sell();
            sell.setId(sellId);
            saleList.setSell(sell);
        }

        Client client = new Client();
        client.setId(clientId);
        saleList.setClient(client);
        saleList.setLankman(lankman);
        saleList.setTel(tel);
        saleList.setAddress(address);
        if (StringUtil.isNotEmpty(deliveryDate)) {
            saleList.setDeliveryDate(sdf.parse(deliveryDate));
        }

        saleList.setSaleNumber(saleNumber);
        saleList.setUser(this.userService.findByUserName((String)SecurityUtils.getSubject().getPrincipal()));
        Gson gson = new Gson();
        List<SaleListProduct> plgList = (List)gson.fromJson(goodsJson, (new TypeToken<List<SaleListProduct>>() {
        }).getType());
        Iterator var18 = plgList.iterator();

        while(var18.hasNext()) {
            SaleListProduct saleListProduct = (SaleListProduct)var18.next();
            System.out.println(saleListProduct);
            saleListProduct.setSaleList(saleList);
            saleListProduct.setState("下单");
            saleListProduct.setDaBaoShu(1);
            saleListProduct.setStorageid(0);
            saleListProduct.setLingshou(false);
            saleListProduct.setLevel(0);
        }

        this.saleListService.saveOne(saleList);
        this.saleListProductService.saveList(plgList);
        this.logService.save(new Log("添加操作", "添加销售单"));
        map.put("success", true);
        return map;
    }

    @RequestMapping({"/list"})
    public Map<String, Object> list(String saleNumber, String clientId, String clerkId, String bSaleDate, String eSaleDate) throws ParseException {
        SaleList saleList = new SaleList();
        if (StringUtil.isNotEmpty(saleNumber)) {
            saleList.setSaleNumber(saleNumber);
        }

        if (StringUtil.isNotEmpty(clientId)) {
            Client client = new Client();
            client.setId(Integer.parseInt(clientId));
            saleList.setClient(client);
        }

        if (StringUtil.isNotEmpty(clerkId)) {
            Clerk clerk = new Clerk();
            clerk.setId(Integer.parseInt(clerkId));
            saleList.setClerk(clerk);
        }

        if (eSaleDate != null && bSaleDate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date eDate = sdf.parse(eSaleDate);
            Date bDate = sdf.parse(bSaleDate);
            saleList.setbSaleDate(bDate);
            saleList.seteSaleDate(eDate);
        }

        System.out.println(saleList);
        Map<String, Object> map = new HashMap();
        List<SaleList> list = this.saleListService.list(saleList, Direction.DESC, new String[]{"saleDate"});
        System.out.println(list);
        map.put("rows", list);
        return map;
    }

    @RequestMapping({"/listProduct"})
    public String listGoods(Integer saleListId) {
        Map<String, Object> map = new HashMap();
        if (saleListId == null) {
            return null;
        } else {
            List<SaleListProduct> saleListProducts = this.saleListProductService.listBySaleListIdNoHeBing(saleListId);
            System.out.println(saleListProducts);
            System.out.println(saleListProducts.size());
            map.put("rows", saleListProducts);
            System.out.println(map);
            JSON parse = JSONUtil.parse(map);
            String s = parse.toString();
            System.out.println(s);
            return s;
        }
    }

    @RequestMapping({"/delete"})
    public Map<String, Object> delete(Integer id) {
        Map<String, Object> map = new HashMap();
        this.saleListProductService.deleteBySaleListId(id);
        this.saleListService.deleteByid(id);
        map.put("success", true);
        return map;
    }

    @RequestMapping({"/listProductByState"})
    public Map<String, Object> listProductByState(Integer id, String state) {
        Map<String, Object> map = new HashMap();
        if (id == null) {
            map.put("rows", this.saleListService.listProductByState(state));
        } else {
            map.put("rows", this.saleListService.listProductByStateAndId(id, state));
        }

        map.put("success", true);
        return map;
    }

    @RequestMapping({"/getSaleListNo"})
    public Map<String, Object> getSaleListNo(int id) {
        Map<String, Object> map = new HashMap();
        List<Integer> saleListNo = this.saleListService.getSaleListNo(id);
        map.put("success", true);
        map.put("arr", saleListNo);
        return map;
    }

    @RequestMapping({"/saveTwo"})
    public Map<String, Object> saveTwo(SaleList saleList, String saleDateq, String deliveryDateq) throws ParseException {
        saleList.setSaleDate((new SimpleDateFormat("yyyy-MM-dd")).parse(saleDateq));
        saleList.setDeliveryDate((new SimpleDateFormat("yyyy-MM-dd")).parse(deliveryDateq));
        if (StringUtil.isNotEmpty(deliveryDateq)) {
        }

        System.out.println(saleDateq);
        System.out.println(deliveryDateq);
        System.out.println(saleList);
        Map<String, Object> resultMap = new HashMap();
        this.saleListService.saveTwo(saleList);
        resultMap.put("success", true);
        System.out.println(resultMap);
        return resultMap;
    }

    @RequestMapping({"/add"})
    public Map<String, Object> add(SaleListProduct saleListProduct) {
        System.out.println(saleListProduct.getDao());
        System.out.println(saleListProduct.getLetter());
        Map<String, Object> map = new HashMap();
        Integer id = saleListProduct.getId();
        SaleList saleList = this.saleListService.findById(id);
        SaleListProduct newSaleListProduct = new SaleListProduct();
        newSaleListProduct.setState("未审核");
        newSaleListProduct.setNum(saleListProduct.getNum());
        newSaleListProduct.setName(saleListProduct.getName());
        if (saleListProduct.getOneweight() == null) {
            Double oneweight = 0.93 * saleListProduct.getLength() * saleListProduct.getModel() * saleListProduct.getMeter();
            newSaleListProduct.setOneweight(oneweight);
            newSaleListProduct.setSumwight(oneweight * (double)saleListProduct.getNum());
        } else {
            newSaleListProduct.setOneweight(saleListProduct.getOneweight());
            newSaleListProduct.setSumwight((double)saleListProduct.getNum() * saleListProduct.getOneweight());
        }
        newSaleListProduct.setLength(saleListProduct.getLength());
        newSaleListProduct.setDao(saleListProduct.getDao());
        newSaleListProduct.setLetter(saleListProduct.getLetter());
        newSaleListProduct.setStorageid(0);
        newSaleListProduct.setModel(saleListProduct.getModel());
        newSaleListProduct.setDaBaoShu(1);
        newSaleListProduct.setDemand(saleListProduct.getDemand());
        newSaleListProduct.setPrice(saleListProduct.getPrice());
        newSaleListProduct.setMeter(saleListProduct.getMeter());
        newSaleListProduct.setColor(saleListProduct.getColor());
        newSaleListProduct.setPeasant(saleListProduct.getPeasant());
        newSaleListProduct.setSaleList(saleList);
        newSaleListProduct.setClientname(saleList.getClient().getName());
        newSaleListProduct.setLevel(0);
        System.out.println(saleListProduct);
        System.out.println(newSaleListProduct);
        this.saleListProductService.save(newSaleListProduct);
        map.put("success", true);
        return map;
    }

    @RequestMapping({"/updateDingjin"})
    public Map<String, Object> updateDingjin(Integer id, Double dingjin) {
        Map<String, Object> map = new HashMap();
        this.saleListService.updateDingjin(dingjin, id);
        map.put("success", true);
        return map;
    }
}
