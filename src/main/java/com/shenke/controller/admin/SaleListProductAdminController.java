package com.shenke.controller.admin;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shenke.entity.*;
import com.shenke.service.*;
import com.shenke.util.LogUtil;
import com.shenke.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/saleListProduct")
public class SaleListProductAdminController {

    @Resource
    private LogService logService;

    @Resource
    private SaleListService saleListService;

    @Autowired
    private LingShouService lingShouService;

    @Resource
    private ClientService clientService;

    @Resource
    private SaleListProductService saleListProductService;

    @Resource
    private StorageService storageService;

    @Resource
    private PeiFangShouService peiFangShouService;

    @Resource
    private JiTaiService jiTaiService;

    /**
     * 订单审核通过
     *
     * @return
     */
    @RequestMapping("/passTheAudit")
    public Map<String, Object> passTheAudit(String ids) {
        Map<String, Object> map = new HashMap<String, Object>();
        String[] idArr = ids.split(",");
        for (int i = 0; i < idArr.length; i++) {
            int id = Integer.parseInt(idArr[i]);

            logService.save(new Log(Log.AUDIT_ACTION, "审核通过"));
            saleListProductService.passTheAudit(id);
        }
        map.put("success", true);
        return map;
    }

    /**
     * 订单审核失败
     *
     * @param ids
     * @return
     */
    @RequestMapping("/auditFailure")
    public Map<String, Object> auditFailure(String ids, String cause) {
        Map<String, Object> map = new HashMap<String, Object>();
        String[] idArr = ids.split(",");
        for (int i = 0; i < idArr.length; i++) {
            int id = Integer.parseInt(idArr[i]);
            logService.save(new Log(Log.AUDIT_ACTION, "审核失败"));
            saleListProductService.auditFailure(id, cause);
        }
        map.put("success", true);
        return map;
    }

    /**
     * 查询所有审核成功的销售商品信息
     *
     * @return
     */
    @RequestMapping("/listProductSucceed")
    public Map<String, Object> listProductSucceed() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rows", saleListProductService.listProductSucceed());
        return map;
    }

    /**
     * 下拉框模糊查询所有幅宽
     *
     * @return
     */
    @RequestMapping("/breadthList")
    public List<SaleListProduct> breadthList(String q) {
        if (q == null) {
            q = "";
        }
        return saleListProductService.breadthList(q);
    }

    /**
     * 根据条件查询所有订单商品信息
     *
     * @param modeSort
     * @param priceSort
     * @param lengthSort
     * @param client
     * @return
     */
    @RequestMapping("/screen")
    public Map<String, Object> screen(String modeSort, String priceSort, String lengthSort, String client, String realityprice,
                                      String oneweight, String sumwight, String realitymodel, String model) {

        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> condition = new HashMap<String, Object>();

        condition.put("modeSort", modeSort);
        condition.put("model", model);
        condition.put("priceSort", priceSort);
        condition.put("lengthSort", lengthSort);
        condition.put("client", client);
        condition.put("realityprice", realityprice);
        condition.put("oneweight", oneweight);
        condition.put("sumwight", sumwight);
        condition.put("realitymodel", realitymodel);


        List<SaleListProduct> screen = saleListProductService.screen(condition);
        map.put("success", true);
        map.put("rows", screen);
        return map;
    }

    /**
     * 根据机台id，下发状态，通知单号查询
     *
     * @Description:
     * @Param:
     * @return:
     * @Author: Andy
     * @Date:
     */
    @RequestMapping("/selectByJitaiIdAndIssueStateAndInformNumber")
    public Map<String, Object> selectByJitaiIdAndIssueStateAndInformNumber(Integer jitaiId, String state, String informNumber) {
        Map<String, Object> map = new HashMap<String, Object>();
        Long infLong = null;
        System.out.println(informNumber);
        if (StringUtil.isNotEmpty(informNumber)) {
            infLong = Long.parseLong(informNumber);
        }
        if (saleListProductService.selectByJitaiIdAndIssueStateAndInformNumber(jitaiId, state, infLong) != null) {
            map.put("success", true);
            map.put("rows", saleListProductService.selectByJitaiIdAndIssueStateAndInformNumber(jitaiId, state, infLong));
        } else {
            map.put("success", false);
        }

        return map;
    }

    /**
     * 查询该机台没有所有未完成的生产通知单号
     *
     * @Description:
     * @Param:
     * @return:
     * @Author: Andy
     * @Date:
     */
    @RequestMapping("/selectNoAccomplish")
    public Map<String, Object> selectNoAccomplish(Integer jitaiId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        List<SaleListProduct> saleListProducts = saleListProductService.selectNoAccomplish(jitaiId);
        /*List<Long> informNumber = new ArrayList<>();
        for (SaleListProduct saleListProduct : saleListProducts) {
            informNumber.add(saleListProduct.getInformNumber());
        }*/

        map.put("rows", saleListProducts);
        return map;
    }

    /**
     * 根据订单状态查询订单
     */
    @RequestMapping("/listProductByState")
    public Map<String, Object> listProductByState(String state) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("rows", saleListProductService.listProductByState(state));
        return map;
    }


    /**
     * 添加或修改仓库信息
     */
    @RequestMapping("/saveInfo")
    public Map<String, Object> save(SaleListProduct saleListProduct) {
        System.out.println("****************************************");
        System.out.println(saleListProduct);
        System.out.println("controller");
        Map<String, Object> map = new HashMap<>();
        saleListProductService.save(saleListProduct);
        map.put("success", true);
        return map;
    }

    /***
     * 修改机台
     */
    @RequestMapping("/alertJitai")
    public Map<String, Object> alertJitai(String idsStr, Integer jitai) {
        Map<String, Object> map = new HashMap<>();
        String[] ids = idsStr.split(",");
        for (int i = 0; i < ids.length; i++) {
            saleListProductService.updateJitaiId(Integer.parseInt(ids[i]), jitai);
        }
        map.put("success", true);
        return map;
    }

    /***
     * 下发机台
     * @return
     */
    @RequestMapping("/issue")
    public Map<String, Object> issue(String idStr) {
        Map<String, Object> map = new HashMap<>();
        String[] ids = idStr.split(",");
        for (int i = 0; i < ids.length; i++) {
            SaleListProduct saleListProduct = saleListProductService.findById(Integer.parseInt(ids[i]));
            saleListProductService.updateState("下发机台：" + saleListProduct.getJiTai().getName(), saleListProduct.getId());
            saleListProductService.updateIussueState("下发机台：" + saleListProduct.getJiTai().getName(), saleListProduct.getId());
        }
        map.put("success", true);
        return map;
    }


    @RequestMapping("/findAll")
    public Map<String, Object> findAll() {
        Map<String, Object> map = new HashMap<>();
        List<SaleListProduct> list = saleListProductService.fandAll();
        map.put("rows", list);
        System.out.println(map);
        return map;
    }

    @RequestMapping("/findJitaiProduct")
    public Map<String, Object> findJitaiProduct() {
        Map<String, Object> map = new HashMap<>();
        List<SaleListProduct> list = saleListProductService.findJitaiProduct();
        map.put("rows", list);
        return map;
    }

    @RequestMapping("/searchJitai")
    public Map<String, Object> searchLiftMoney(String saleNumber, Integer jitai, String saleDate, String deliveryDate, String allorState, String state) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();

        map1.put("saleNumber", saleNumber);
        map1.put("jitai", jitai);
        map1.put("saleDate", saleDate);
        map1.put("allorState", allorState);
        map1.put("deliveryDate", deliveryDate);
        map1.put("state", state);


        map.put("success", true);
        map.put("rows", saleListProductService.searchJitai(map1));

        System.out.println(map);

        return map;
    }

    /**
     * 修改完成数量
     *
     * @param id
     */
    @RequestMapping("/updateAccomplishNumber")
    public synchronized Map<String, Object> update(Integer id, Integer jiTai, String clerkName, Double realityweight, String type, Double changdu, String groupName) {
        SaleListProduct saleListProduct = saleListProductService.findById(id);
        Integer daBaoShu = saleListProduct.getDaBaoShu();
        System.out.println(jiTai);
        JiTai jiTaiServiceById = jiTaiService.findById(jiTai);
        saleListProductService.findById(id);
        Storage storage = new Storage();
        storage.setJiTai(jiTaiServiceById);
        storage.setSaleListProduct(saleListProduct);
        if (type.equals("快速")) {
            storage.setRealityweight(saleListProduct.getOneweight());
            LogUtil.printLog("快速打码===重量：" + saleListProduct.getOneweight() + "===员工：" + clerkName);
        } else {
            LogUtil.printLog("称重===重量：" + realityweight + "===员工：" + clerkName);
            storage.setRealityweight(realityweight);
        }
        Map<String, Object> map = new HashMap<>();
        String result = saleListProductService.updateAccomplishNumber(id);
        if (result.split(":").length > 1) {
            int num = Integer.parseInt(result.split(":")[1]);
            LogUtil.printLog("===打包数量:" + num);
            storage.setDabaonum(num);
        }
        if (result.startsWith("生产完成")) {
            if (changdu != null) {
                storageService.add(storage, clerkName, groupName, changdu, null);
            } else {
                storageService.add(storage, clerkName, groupName);
            }
            map.put("success", true);
            map.put("id", storageService.selectByMaxId().getId());
        } else if (result.startsWith("打包完成")) {
            if (changdu != null) {
                storageService.add(storage, clerkName, groupName, changdu, null);
            } else {
                storageService.add(storage, clerkName, groupName);
            }
            map.put("success", true);
            map.put("id", storageService.selectByMaxId().getId());
        } else if (result.startsWith("生产已完成")) {
            map.put("success", true);
            map.put("msg", result);
        } else {
            map.put("success", false);
        }
        return map;
    }
//    @RequestMapping("/updateAccomplishNumber")
//    public String update(Integer id) {
//        Map<String, Object> map = new HashMap<>();
//        String result = saleListProductService.updateAccomplishNumber(id);
//        return result;
//    }

    @RequestMapping("/hebingJian")
    public Map<String, Object> hebingJian(Integer count, Integer id) {
        Map<String, Object> map = new HashMap<>();
        saleListProductService.hebingJian(count, id);
        map.put("success", true);
        return map;
    }

    /***
     * 修改机台
     * @return
     */
    @RequestMapping("/UpdateJiTai")
    public String UpdateJiTai(Integer[] idarr, Integer jitai) {
        for (int i = 0; i < idarr.length; i++) {
            SaleListProduct saleListProduct = saleListProductService.findById(idarr[i]);
            if (saleListProduct.getJiTai() != null) {
                System.out.println(saleListProduct);
                JiTai jiTai = saleListProduct.getJiTai();
                JiTai byId = jiTaiService.findById(jitai);
                String state = saleListProduct.getState();
                String issueState = saleListProduct.getIssueState();
                String state1 = state.replace(jiTai.getName(), byId.getName());
                String issuestate1 = issueState.replace(jiTai.getName(), byId.getName());
                System.out.println(jiTai);
                System.out.println(byId);
                System.out.println(state);
                System.out.println(state1);
                System.out.println(issueState);
                System.out.println(issuestate1);
                saleListProduct.setState(state1);
                saleListProduct.setIssueState(issuestate1);
                saleListProduct.setJiTai(byId);
                System.out.println(saleListProduct);
                saleListProductService.save(saleListProduct);
            } else {
                return "存在未下发机台信息，无法修改！";
            }
        }
        return "修改成功！！";
    }

    @RequestMapping("/hebingOne")
    public Map<String, Object> hebingOne(String ids) {
        StringBuilder hebingLength = new StringBuilder();
        StringBuilder hebingId = new StringBuilder();
        System.out.println(ids);
        Map<String, Object> map = new HashMap<>();
        String[] idArr = ids.split(",");
        System.out.println(idArr.length);
        SaleListProduct saleListProduct = saleListProductService.findById(Integer.parseInt(idArr[0]));
        Double length = saleListProduct.getLength();
        Integer num = saleListProduct.getNum();
        hebingLength.append(length + "*" + num);
        Double oneweight = saleListProduct.getOneweight();
        if (idArr.length > 1) {
            SaleListProduct newSaleListProduct = new SaleListProduct();
            BeanUtils.copyProperties(saleListProduct, newSaleListProduct);
            hebingId.append(saleListProduct.getId());
            Double sumLength = length * num;
            Double sumWeight = oneweight * num;
            for (int i = 1; i < idArr.length; i++) {
                SaleListProduct slp = saleListProductService.findById(Integer.parseInt(idArr[i]));
                hebingId.append("," + slp.getId());
                if (slp.getNum() > 1) {
                    hebingLength.append("+" + slp.getLength() + "*" + slp.getNum());
                } else {
                    hebingLength.append("+" + slp.getLength());
                }
                sumLength += slp.getLength() * slp.getNum();
                sumWeight += slp.getOneweight() * slp.getNum();
                slp.setState("合并件");
                saleListProductService.save(slp);
            }
            newSaleListProduct.setId(null);
            newSaleListProduct.setHeBingId(hebingId.toString());
            newSaleListProduct.setHebingLength(hebingLength.toString());
            newSaleListProduct.setLength(sumLength);
            newSaleListProduct.setOneweight(sumWeight);
            newSaleListProduct.setSumwight(sumWeight);
            newSaleListProduct.setNum(1);
            saleListProduct.setHeBingId(hebingId.toString());
            saleListProduct.setState("合并件");
            saleListProductService.save(saleListProduct);
            saleListProductService.save(newSaleListProduct);
        } else {
            SaleListProduct newSaleListProduct = new SaleListProduct();
            BeanUtils.copyProperties(saleListProduct, newSaleListProduct);
            newSaleListProduct.setId(null);
            newSaleListProduct.setLength(length * num);
            newSaleListProduct.setOneweight(oneweight * num);
            newSaleListProduct.setHebingLength(length + "*" + num);
            newSaleListProduct.setNum(1);
            newSaleListProduct.setHeBingId(saleListProduct.getId().toString());
            saleListProduct.setHeBingId(saleListProduct.getId().toString());
            saleListProduct.setState("合并件");
            saleListProductService.save(saleListProduct);
            saleListProductService.save(newSaleListProduct);
        }
//        Double sumLength = 0.0;
//        Double length = byId.getLength();
//        Integer num1 = byId.getNum();
//        sumLength += (length * num1);
//        if (num1 > 1) {
//            hebingLength.append(length + "*" + byId.getNum());
//        } else {
//            hebingLength.append(length);
//        }
//        for (int i = 1; i < idArr.length; i++) {
//            SaleListProduct saleListProduct = saleListProductService.findById(Integer.parseInt(idArr[i]));
//            Integer num = saleListProduct.getNum();
//            Double sLength = saleListProduct.getLength() * saleListProduct.getNum();
//            sumLength += new BigDecimal(sLength).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//            if (num > 1) {
//                hebingLength.append(" + " + saleListProduct.getLength() + "*" + saleListProduct.getNum());
//            } else {
//                hebingLength.append(" + " + saleListProduct.getLength());
//            }
//            saleListProductService.deleteById(Integer.parseInt(idArr[i]));
//        }
//        byId.setLength(sumLength);
//        byId.setHebingLength(hebingLength.toString());
//        byId.setNum(1);
//        save(byId);
        map.put("success", true);
        System.out.println(map);
        return map;
    }

    /**
     * 产品加急
     *
     * @param
     * @param jiajidengji
     * @return
     */
    @RequestMapping("/chanpinjiaji")
    public Map<String, Object> chanpinjiaji(String idsStr, String jiajidengji) {
        System.out.println("**********");
        System.out.println(idsStr);
        System.out.println("**********");
        Map<String, Object> map = new HashMap<>();
        String[] ids = idsStr.split(",");
        for (int i = 0; i < ids.length; i++) {
            saleListProductService.chanpinjiaji(Integer.parseInt(ids[i]), jiajidengji);
        }
        map.put("success", true);
        return map;
    }

    /***
     * 订单加急
     * @param id
     * @param jiajidengji
     * @return
     */
    @RequestMapping("/dingdanjiaji")
    public Map<String, Object> dingdanjiaji(Integer id, String jiajidengji) {
        Map<String, Object> map = new HashMap<>();
        saleListProductService.dingdanjiaji(id, jiajidengji);
        map.put("success", true);
        return map;
    }

    /***
     * 按条件查询
     * @return
     */
    @RequestMapping("/condition")
    public Map<String, Object> condition(SaleListProduct saleListProduct) {
        System.out.println(saleListProduct);
        Map<String, Object> map = new HashMap<>();
        List<SaleListProduct> condition = saleListProductService.condition(saleListProduct);
        Integer num = 0;
        Double weight = 0.0;

        for (SaleListProduct saleListProduct1 : condition) {
            System.out.println(saleListProduct1);
            Integer wancheng = 0;
            if (saleListProduct1.getAccomplishNumber() != null) {
                wancheng = saleListProduct1.getAccomplishNumber();
            }
            System.out.println("完成数量：" + wancheng);
            Integer num1 = saleListProduct1.getNum() - wancheng;
            num += num1;
            System.out.println("剩余数量：" + num);
            weight += num1 * saleListProduct1.getOneweight();
            System.out.println("剩余总重量：" + weight);
        }

        if (condition.size() != 0) {
            map.put("success", true);
            map.put("num", num);
            map.put("weight", weight);
        } else {
            map.put("success", false);
            map.put("msg", "没有符合条件的商品");
        }
        return map;
    }

    @RequestMapping("/findByJitaiId")
    public Map<String, Object> findByJitaiId(SaleListProduct saleListProduct) {
        Map<String, Object> map = new HashMap<>();
        System.out.println(saleListProduct);
        List<SaleListProduct> byJitaiId = saleListProductService.findByJitaiId(saleListProduct);
        map.put("success", true);
        map.put("rows", byJitaiId);
        return map;
    }

    @RequestMapping("/deleteByIdArr")
    public Map<String, Object> deleteByIdArr(String idArr) {
        Map<String, Object> map = new HashMap<>();
        String[] ids = idArr.split(",");
        List<Storage> storages = storageService.findBySaleListProductIds(ids);
        if (storages.size() != 0) {
            map.put("success", false);
            map.put("msg", "已经存在生产完成的商品，无法删除！");
            return map;
        }
        for (int i = 0; i < ids.length; i++) {
            saleListProductService.deleteById(Integer.parseInt(ids[i]));
        }
        map.put("success", true);
        return map;
    }

    /***
     * 修改订单数量
     * @param num
     * @return
     */
    @RequestMapping("/updateNum")
    public String updateNum(Integer num, Integer id) {
        return saleListProductService.updateNum(num, id);
    }

    /***
     * 根据id修改完成数量
     * @param id
     * @param wancheng
     * @return
     */
    @RequestMapping("/updateWanCehng")
    public String updateWanCehng(Integer id, Integer wancheng) {
        SaleListProduct saleListProduct = saleListProductService.findById(id);
        Integer num = saleListProduct.getNum();
        Integer count = storageService.findCountBySaleListProductId(saleListProduct.getId());
        if (count == null) {
            count = 0;
        }
        System.out.println(count);
        System.out.println(wancheng);
        if (count > wancheng) {
            return "库存中已完成比该数量多的件数，请删除库存！库存中的数量：" + count;
        } else if (count < wancheng) {
            return "库存中改订单的货物完成数量不足，请进行生产！当前库存中的数量：" + count;
        }
        if (wancheng > num) {
            return "完成数量大于总数量无法修改！";
        } else if (wancheng < num) {
            saleListProduct.setAccomplishNumber(wancheng);
            saleListProduct.setState("下发机台：" + saleListProduct.getJiTai().getName());
            saleListProductService.save(saleListProduct);
            return "完成数量小于总数量修改状态为下发机台！";
        } else {
            saleListProduct.setAccomplishNumber(wancheng);
            saleListProduct.setState("生产完成：" + saleListProduct.getJiTai().getName());
            saleListProductService.save(saleListProduct);
            return "完成数量等于总数量修改状态为生产完成！";
        }
    }

    /***
     * 修改打包数量
     * @return
     */
    @RequestMapping("/updateDaBaoShu")
    public boolean updateDaBaoShu(Integer id, Integer dabaoshu) {
        System.out.println(id);
        System.out.println(dabaoshu);
        SaleListProduct byId = saleListProductService.findById(id);
        byId.setDaBaoShu(dabaoshu);
        saleListProductService.save(byId);
        return true;
    }

    @RequestMapping("/find")
    public Map<String, Object> find(SaleListProduct saleListProduct) {
        Map<String, Object> map = new HashMap<>();
        System.out.println(saleListProduct);
        List<SaleListProduct> saleListProducts = saleListProductService.find(saleListProduct);
        Long informNumber = saleListProduct.getInformNumber();
        List<PeiFangShou> peiFangShous = peiFangShouService.findByInfornNumber(informNumber);
        if (peiFangShous != null && peiFangShous.size() != 0) {
            map.put("peifang", peiFangShous);
        }
        map.put("success", true);
        map.put("rows", saleListProducts);
        return map;
    }

    @RequestMapping("/addpeifang")
    public Map<String, Object> addpeifang(SaleListProduct saleListProduct, String peifangjson) {
        System.out.println(peifangjson);
        Long informNumber = saleListProduct.getInformNumber();
        Gson gson = new Gson();
        List<PeiFangShou> peiFangShous = peiFangShouService.findByInfornNumber(informNumber);
        if (peiFangShous != null && peiFangShous.size() > 0) {
            peiFangShouService.deleteList(peiFangShous);
        }
        List<PeiFangShou> plgList = gson.fromJson(peifangjson, new TypeToken<List<PeiFangShou>>() {
        }.getType());
        System.out.println(plgList);
        System.out.println(saleListProduct);
        for (PeiFangShou peiFangShou : plgList) {
            peiFangShou.setInformNumber(informNumber);
        }
        Map<String, Object> map = new HashMap<>();
        peiFangShouService.saveList(plgList);
        map.put("success", true);
        return map;
    }

    /**
     * 添加零售商品
     *
     * @return
     */
    @RequestMapping("/addlingshou")
    public Map<String, Object> addlingshou(String danhao, String clientname, Integer jitai, String xiaoshouDatee, String goodsJson) throws ParseException {
        System.out.println(danhao);
        SaleList saleList = saleListService.findBySaleNumber(danhao);
        System.out.println(saleList);
        System.out.println(saleList);
        Map<String, Object> map = new HashMap<>();
        Map<Integer, Double> length = new HashMap<>();
        List<Storage> storages = new ArrayList<>();
        Long infornNumber = saleListProductService.findMaxInfornNumber();
        if (infornNumber == null) {
            infornNumber = 0l;
        }
        Gson gson = new Gson();
        List<SaleListProduct> saleListProducts = gson.fromJson(goodsJson, new TypeToken<List<SaleListProduct>>() {
        }.getType());
        System.out.println(saleListProducts);
        System.out.println(saleListProducts.size());
        for (SaleListProduct saleListProduct : saleListProducts) {
            System.out.println(saleListProduct);
            Integer id = saleListProduct.getStorageid();
            System.out.println(id);
            LingShou findone = lingShouService.findone(saleListProduct.getId());
            System.out.println(findone);
            findone.setStorageid(id);
            findone.setXiaoshouDate(saleList.getSaleDate());
            lingShouService.saveone(findone);
            if (length.get(id) != null) {
                length.put(id, length.get(id) + (saleListProduct.getLength() * saleListProduct.getNum()));
            } else {
                length.put(id, saleListProduct.getLength() * saleListProduct.getNum());
            }
            Storage storage = storageService.findById(saleListProduct.getStorageid());
            saleListProduct.setColor(storage.getColor());
            saleListProduct.setDao(storage.getDao());
            System.out.println(saleList.getClient().getName());
            saleListProduct.setClientname(saleList.getClient().getName());
            saleListProduct.setLingshou(true);
            saleListProduct.setPeasant(findone.getPeasant());
            saleListProduct.setUnitPrice(findone.getDanjia());
            saleListProduct.setDemand(findone.getBeizhu());
            saleListProduct.setJiTai(jiTaiService.findById(jitai));
            saleListProduct.setInformNumber(infornNumber + 1);
            saleListProduct.setDaBaoShu(1);
            saleListProduct.setDemand(findone.getBeizhu());
            saleListProduct.setPrice(storage.getPrice());
            saleListProduct.setOneweight(storage.getRealityweight() / storage.getLength() * saleListProduct.getLength());
            saleListProduct.setSumwight((saleListProduct.getOneweight() * saleListProduct.getNum()));
            saleListProduct.setLevel(0);
            saleListProduct.setId(null);
            saleListProduct.setState("下发机台：" + jiTaiService.findById(jitai).getName());
            saleListProduct.setSaleList(saleList);
        }

        for (Integer key : length.keySet()) {
            System.out.println("=======");
            System.out.println(key);
            Storage storage = storageService.findById(key);
            if (length.get(key) > storage.getShengyulength()) {
                map.put("success", false);
                map.put("msg", "编号为" + key + "的总长度超出剩余长度");
                return map;
            }
            Double shengyu = storage.getShengyulength() - length.get(key);
            System.out.println("剩余：" + shengyu);
            System.out.println(shengyu == 0);
            if (shengyu == 0) {
                System.out.println("等于0");
                storage.setState("出售");
            } else if (shengyu < 0) {
                map.put("success", false);
                map.put("msg", "未知错误！");
                return map;
            }
            storage.setShengyulength(shengyu);
            storages.add(storage);
        }
        System.out.println(saleListProducts);
        System.out.println(saleListProducts.size());
//        Client client = clientService.findName("零售");
//        if (client == null) {
//            map.put("success", false);
//            map.put("msg", "不存在客户名为零售的客户，请先添加");
//            return map;
//        }
//        saleList.setClient(client);
        saleListService.saveOne(saleList);
        saleListProductService.saveList(saleListProducts);
        storageService.save(storages);
        map.put("success", true);
        return map;
    }

    @PostMapping("/updateName")
    public Map<String, Object> updateName(Integer[] ids, String name) {
        Map<String, Object> map = new HashMap<>();
        System.out.println(name);

        saleListProductService.updateName(ids, name);
        map.put("success", true);
        return map;
    }

    /***
     * 根据id数组修改长度
     * @param ids
     * @param length
     * @return
     */
    @RequestMapping("/updatLength")
    public Map<String, Object> updateLength(Integer[] ids, Double length) {
        Map<String, Object> map = new HashMap<>();
        saleListProductService.updatLength(ids, length);
        map.put("success", true);
        return map;
    }

    /***
     * 根据id修改幅宽
     * @param ids
     * @param model
     * @return
     */
    @RequestMapping("/updatModel")
    public Map<String, Object> updatemodel(Integer[] ids, Double model) {
        Map<String, Object> map = new HashMap<>();
        saleListProductService.updatemodel(ids, model);
        map.put("success", true);
        return map;
    }

    /***
     * 根据id数组修改厚度
     * @param ids
     * @param price
     * @return
     */
    @RequestMapping("/updatPrice")
    public Map<String, Object> updatPrice(Integer[] ids, Double price) {
        Map<String, Object> map = new HashMap<>();
        saleListProductService.updatPrice(ids, price);
        map.put("success", true);
        return map;
    }

    /***
     * 根据id数组修改实际厚度
     * @param ids
     * @param meter
     * @return
     */
    @RequestMapping("/updatMeter")
    public Map<String, Object> updatMeter(Integer[] ids, Double meter) {
        Map<String, Object> map = new HashMap<>();
        saleListProductService.updatMeter(ids, meter);
        map.put("success", true);
        return map;
    }

    /***
     * 根据id数组修改单件
     * @param ids
     * @param oneweight
     * @return
     */
    @RequestMapping("/updatOneweight")
    public Map<String, Object> updatOneweight(Integer[] ids, Double oneweight) {
        Map<String, Object> map = new HashMap<>();
        saleListProductService.updatOneweight(ids, oneweight);
        map.put("success", true);
        return map;
    }

    /***
     * 根据id数组修改农户名
     * @param ids
     * @param peasant
     * @return
     */
    @RequestMapping("/updatPeasant")
    public Map<String, Object> updatPeasant(Integer[] ids, String peasant) {
        Map<String, Object> map = new HashMap<>();
        saleListProductService.updatPeasant(ids, peasant);
        map.put("success", true);
        return map;
    }

    /***
     * 根据id数组修改颜色
     * @param ids
     * @param color
     * @return
     */
    @RequestMapping("/updatColor")
    public Map<String, Object> updatColor(Integer[] ids, String color) {
        Map<String, Object> map = new HashMap<>();
        saleListProductService.updatColor(ids, color);
        map.put("success", true);
        return map;
    }

    /**
     * 订单追踪界面查询
     * @return
     */
    @RequestMapping("/dingDanZhuiZong")
    public Map<String,Object> dingDanZhuiZong(SaleListProduct saleListProduct){
        Map<String,Object> map = new HashMap<>();
        map.put("rows",saleListProductService.dingDanZhuiZongg(saleListProduct));
        map.put("success",true);
        return map;
    }

    /***
     * 根据通知单号查询
     * @param danhao
     * @return
     */
    @RequestMapping("/findByDanhao")
    public Map<String, Object> findByDanhao(Long danhao){
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("rows", saleListProductService.findByDanhao(danhao));
        return map;
    }

    /**
     *修改通知单号
     * @param
     */
    @RequestMapping("/updatInfo")
    public Map<String, Object> updatInfo(Integer[] ids, Integer info){
        Map<String, Object> map = new HashMap<>();
        saleListProductService.updatInfo(ids, info);
        map.put("success",true);
        return map;
    }

    /**
     * 查询配方界面的查询
     * @return
     */
    @RequestMapping("/chaxunpeifang")
    public Map<String,Object> chaxunpeifang(String client){
        Map<String,Object> map = new HashMap<>();
        map.put("rows",saleListProductService.chaxunpeifang(client));
        map.put("success",true);
        return map ;
    }

    /**
     * 生产通知单下发时候插入到已有的通知单号中
     * @param danhao
     * @param Ids
     * @return
     */
    @RequestMapping("/chaDanInformNum")
    public Map<String,Object> chaDanInformNum(Long danhao,Integer []Ids){
        Map<String,Object> map = new HashMap<>();
        List<SaleListProduct> list = saleListProductService.findByDanhao(danhao);
        if(list.size() == 0){
            map.put("success",true);
            map.put("error","无此通知单号");
        }else {
            SaleListProduct saleListProduct = list.get(0);
            String state = "分配机台：" + saleListProduct.getJiTai().getName();
            String iussueState = "未下发";
            saleListProductService.jitaiAllocation(Ids,state,saleListProduct.getJiTai().getId(),danhao,iussueState);
            map.put("success",true);
        }
        return map;
    }

}
