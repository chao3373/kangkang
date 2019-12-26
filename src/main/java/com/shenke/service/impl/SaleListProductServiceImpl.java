package com.shenke.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import com.shenke.service.StorageService;
import com.shenke.util.GetResultUtils;
import com.shenke.util.LogUtil;
import com.shenke.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.shenke.entity.Client;
import com.shenke.entity.SaleList;
import com.shenke.entity.SaleListProduct;
import com.shenke.repository.SaleListProductRepository;
import com.shenke.service.SaleListProductService;

@Service("saleListProductService")
public class SaleListProductServiceImpl implements SaleListProductService {

    @Resource
    private SaleListProductRepository saleListProductRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    private StorageService storageService;

    @Override
    public List<SaleListProduct> listBySaleListId(Integer saleListId) {
        return saleListProductRepository.listBySaleListId(saleListId);
    }

    @Override
    public void deleteBySaleListId(Integer id) {
        saleListProductRepository.deleteBySaleListId(id);
    }

    @Override
    public void passTheAudit(int id) {
        saleListProductRepository.passTheAudit(id);
    }

    @Override
    public void auditFailure(int id, String cause) {
        saleListProductRepository.auditFailure(id, cause);
    }

    @Override
    public List<SaleListProduct> fandAll() {
        return saleListProductRepository.findAll();
    }

    public List<SaleListProduct> fandAll(Iterable<Integer> ids) {
        return saleListProductRepository.findAll(ids);
    }

    @Override
    public List<SaleListProduct> listProductSucceed() {
        return saleListProductRepository.listProductSucceed();
    }

    @Override
    public List<SaleListProduct> breadthList(String q) {
        return saleListProductRepository.breadthList(q);
    }

    @Override
    public List<SaleListProduct> screen(Map<String, Object> condition) {


        List<Order> orders = new ArrayList<Order>();

        Specification<SaleListProduct> specification = new Specification<SaleListProduct>() {
            Predicate predicate = null;

            @Override
            public Predicate toPredicate(Root<SaleListProduct> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                predicate = cb.conjunction();
                predicate.getExpressions().add(cb.like(root.get("state"), "%审核通过%"));
                if (condition.get("client") != null) {

                    Subquery<Integer> subquery2 = query.subquery(Integer.class);
                    Root<Client> fromC = subquery2.from(Client.class);
                    subquery2.select(fromC.get("id")).where(cb.equal(fromC.get("name"), condition.get("client")));

                    Subquery<Integer> subquery = query.subquery(Integer.class);
                    Root<SaleList> fromSL = subquery.from(SaleList.class);
                    subquery.select(fromSL.get("id")).where(fromSL.get("client").get("id").in(subquery2));

                    predicate.getExpressions().add(cb.and(root.get("saleList").get("id").in(subquery)));
                }

                if (condition.get("modeSort") != null) {
                    predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("model"), Double.parseDouble(condition.get("modeSort").toString())));
                }
                if (condition.get("model") != null) {
                    predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("model"), Double.parseDouble(condition.get("model").toString())));
                }
                if (condition.get("priceSort") != null) {
                    predicate.getExpressions().add(cb.and(cb.equal(root.get("price"), condition.get("priceSort"))));
                }
                if (condition.get("lengthSort") != null) {
                    predicate.getExpressions().add(cb.and(cb.equal(root.get("length"), condition.get("lengthSort"))));
                }
                if (condition.get("realityprice") != null) {
                    predicate.getExpressions().add(cb.and(cb.equal(root.get("realityprice"), condition.get("realityprice"))));
                }
                if (condition.get("oneweight") != null) {
                    predicate.getExpressions().add(cb.and(cb.equal(root.get("oneweight"), condition.get("oneweight"))));
                }
                if (condition.get("sumwight") != null) {
                    predicate.getExpressions().add(cb.and(cb.equal(root.get("sumwight"), condition.get("sumwight"))));
                }
                if (condition.get("realitymodel") != null) {
                    predicate.getExpressions()
                            .add(cb.and(cb.equal(root.get("realitymodel"), condition.get("realitymodel"))));
                }

                return predicate;
            }
        };

        return saleListProductRepository.findAll(specification);

    }

    @Override
    public void updateState(String name, Integer id) {
        saleListProductRepository.updateState(name, id);
    }

    @Override
    public void saveList(List<SaleListProduct> plgList) {
        saleListProductRepository.save(plgList);
//        for (SaleListProduct saleListProduct : plgList) {
//            saleListProduct.setDaBaoShu(1);
//            saleListProductRepository.save(saleListProduct);
//        }
    }

    @Override
    public void updateJitaiId(Integer id, Integer id1) {
        saleListProductRepository.updateJitaiId(id, id1);
    }

    @Override
    public List<SaleListProduct> selectByJitaiIdAndIssueStateAndInformNumber(Integer jitaiId, String state, Long infLong) {
        System.out.println(state);
        return saleListProductRepository.findAll(new Specification<SaleListProduct>() {
            @Override
            public Predicate toPredicate(Root<SaleListProduct> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (jitaiId != null) {
                    predicate.getExpressions().add(cb.equal(root.get("jiTai").get("id"), jitaiId));
                }
                if (StringUtil.isNotEmpty(state)) {
                    predicate.getExpressions().add(cb.like(root.get("state"), "%" + state + "%"));
                }
                if (infLong != null) {
                    predicate.getExpressions().add(cb.equal(root.get("informNumber"), infLong));
                }
                return predicate;
            }
        }, new Sort(Sort.Direction.DESC, "level"));
    }

    @Override
    public void updateInformNumber(Long informNumber, int id) {
        saleListProductRepository.updateInformNumber(informNumber, id);
    }

    @Override
    public void updateIussueState(String issueState, int id) {
        saleListProductRepository.updateIussueState(issueState, id);
    }

    @Override
    public List<SaleListProduct> selectNoAccomplish(Integer jitaiId) {
        System.out.println(jitaiId);
        return saleListProductRepository.selectNoAccomplish(jitaiId);
    }

    @Override
    public List<SaleListProduct> listProductByState(String state) {
        return saleListProductRepository.listProductByState(state);
    }

    @Override
    public Long findMaxInfornNumber() {
        return saleListProductRepository.findMaxInfornNumber();
    }

    @Override
    public void save(SaleListProduct saleListProduct) {
        saleListProductRepository.save(saleListProduct);
    }

    @Override
    public SaleListProduct findById(int parseInt) {
        return saleListProductRepository.findOne(parseInt);
    }

    @Override
    public List<SaleListProduct> findJitaiProduct() {
        return saleListProductRepository.findJitaiProduct();
    }


    @Override
    public List<SaleListProduct> searchJitai(Map<String, Object> map) {

        System.out.println(map);

        return saleListProductRepository.findAll(new Specification<SaleListProduct>() {
            @Override
            public Predicate toPredicate(Root<SaleListProduct> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (StringUtil.isNotEmpty((String) map.get("saleDate"))) {
                    try {
                        predicate.getExpressions().add(cb.equal(root.get("saleList").get("saleDate"), new SimpleDateFormat("yyyy-MM-dd").parse((String) map.get("saleDate"))));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (map.get("jitai") != null) {
                    predicate.getExpressions().add(cb.equal(root.get("jiTai").get("id"), map.get("jitai")));
                }
                if (StringUtil.isNotEmpty((String) map.get("deliveryDate"))) {
                    try {
                        predicate.getExpressions().add(cb.equal(root.get("saleList").get("deliveryDate"), new SimpleDateFormat("yyyy-MM-dd").parse((String) map.get("deliveryDate"))));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (StringUtil.isNotEmpty((String) map.get("saleNumber"))) {
                    predicate.getExpressions().add(cb.equal(root.get("saleList").get("saleNumber"), map.get("saleNumber")));
                }
                if (map.get("allorState") != null) {
                    //predicate.getExpressions().add(cb.like(root.get("saleNumber"), (String) map.get("saleNumber")));
                    predicate.getExpressions().add(cb.like(root.get("issueState"), "%" + map.get("allorState") + "%"));
                }
                if (map.get("state") != null) {
                    predicate.getExpressions().add(cb.like(root.get("state"), "%" + map.get("state") + "%"));
                }
                return predicate;

            }
        });
    }


    @Override
    public String updateAccomplishNumber(Integer id) {
        SaleListProduct saleListProduct = this.findById(id);
        //完成数+1
//        Integer count = saleListProduct.getAccomplishNumber() == null ? 0 : saleListProduct.getAccomplishNumber();
        Integer count = storageService.findCountBySaleListProductId(id);
        if (count == null) {
            count = 0;
        }
        Integer num = saleListProduct.getNum();
        Integer daBaoShu = saleListProduct.getDaBaoShu();
        Integer shengyu = num - count;
        Integer countt = count + daBaoShu;
        if (count > num || count == num) {
            saleListProductRepository.updateState("生产完成：" + saleListProduct.getJiTai().getName(), saleListProduct.getId());
            return "生产已完成";
        }
        LogUtil.printLog("当前完成数量：" + count);
        LogUtil.printLog("剩余数量：" + shengyu);
        LogUtil.printLog("当前设置打包数量：" + daBaoShu);
        LogUtil.printLog("当前完成数加打包数量：" + countt);
        if (countt > num) {
            LogUtil.printLog("当前完成数加打包数量大于总数量--生产完成");
            saleListProductRepository.updateAccomplishNumberById(num, saleListProduct.getId());
            saleListProductRepository.updateState("生产完成：" + saleListProduct.getJiTai().getName(), saleListProduct.getId());
            LogUtil.printLog("生产完成---" + "打包数量：" + (num - count) + "机台名称：" + saleListProduct.getJiTai().getName() + "客户名：" + saleListProduct.getClientname() + "长度：" + saleListProduct.getLength() + "幅宽：" + saleListProduct.getModel() + "厚度：" + saleListProduct.getPrice());
            return "生产完成:" + (num - count);
        } else if (countt < num) {
            LogUtil.printLog("当前完成数加打包数量小于总数量--打包完成");
            saleListProductRepository.updateAccomplishNumberById(countt, saleListProduct.getId());
            LogUtil.printLog("打包完成---" + "打包数量：" + daBaoShu + "机台名称：" + saleListProduct.getJiTai().getName() + "客户名：" + saleListProduct.getClientname() + "长度：" + saleListProduct.getLength() + "幅宽：" + saleListProduct.getModel() + "厚度：" + saleListProduct.getPrice());
            return "打包完成:" + daBaoShu;
        } else {
            LogUtil.printLog("当前完成数加打包数量等于总数量--生产完成");
            saleListProductRepository.updateAccomplishNumberById(num, saleListProduct.getId());
            saleListProductRepository.updateState("生产完成：" + saleListProduct.getJiTai().getName(), saleListProduct.getId());
            LogUtil.printLog("生产完成---" + "打包数量：" + daBaoShu + "机台名称：" + saleListProduct.getJiTai().getName() + "客户名：" + saleListProduct.getClientname() + "长度：" + saleListProduct.getLength() + "幅宽：" + saleListProduct.getModel() + "厚度：" + saleListProduct.getPrice());
            return "生产完成:" + daBaoShu;
        }
    }

    /***
     * 指定件数合并
     * @param count
     * @param id
     */
    @Override
    public void hebingJian(Integer count, Integer id) {
        LogUtil.printLog("===订单合并===");
        SaleListProduct saleListProduct = saleListProductRepository.findOne(id);
        //获取合并前的状态，重量长度和数量，并查看是否可以整除
        String state = saleListProduct.getState();
        Integer num = saleListProduct.getNum();
//        SaleList saleList = saleListProduct.getSaleList();
        Integer yushu = num % count;
        Double length = saleListProduct.getLength();
        Double oneweight = saleListProduct.getOneweight();

        //能被整除的放到一个对象
        SaleListProduct newSaleListProduct = new SaleListProduct();
        BeanUtils.copyProperties(saleListProduct, newSaleListProduct);

        //计算能够整数的部分合并后的重量、长度和数量
        Integer shuliang = num / count;
        String heBingLength = length + "*" + count;
        Double changdu = length * count;
        Double zhongliang = oneweight * count;

        //设置能够整除的部分的信息
        newSaleListProduct.setNum(shuliang);
        newSaleListProduct.setId(null);
        newSaleListProduct.setOneweight(zhongliang);
        newSaleListProduct.setSumwight(zhongliang * shuliang);
        newSaleListProduct.setLength(changdu);
        newSaleListProduct.setHebingLength(heBingLength);
        newSaleListProduct.setHebingLength(heBingLength);
        newSaleListProduct.setHeBingId(id.toString());
        LogUtil.printLog(newSaleListProduct.toString());
        saleListProduct.setState("合并件");
        saleListProduct.setHeBingId(id.toString());
        saleListProductRepository.save(newSaleListProduct);
        saleListProductRepository.save(saleListProduct);
        if (yushu != 0) {//无法整除
            LogUtil.printLog("无法整除");
            Integer shengyushuliang = num - shuliang * count;
            System.out.println("剩余数量：" + shengyushuliang);
            SaleListProduct shengyuSaleListProduct = new SaleListProduct();
            BeanUtils.copyProperties(saleListProduct, shengyuSaleListProduct);
            Double shengyuchangdu = shengyushuliang * length;
            System.out.println("剩余长度：" + shengyuchangdu);
            Double shengyuzhongliang = shengyushuliang * oneweight;
            System.out.println("剩余重量：" + shengyuzhongliang);
            String shengyuhebingchangdu = length + "*" + shengyushuliang;
            System.out.println("剩余合并长度：" + shengyuhebingchangdu);
            shengyuSaleListProduct.setId(null);
            shengyuSaleListProduct.setLength(shengyuchangdu);
            shengyuSaleListProduct.setOneweight(shengyuzhongliang);
            shengyuSaleListProduct.setSumwight(shengyuzhongliang);
            shengyuSaleListProduct.setNum(1);
            shengyuSaleListProduct.setHebingLength(shengyuhebingchangdu);
            shengyuSaleListProduct.setHeBingId(id.toString());
            shengyuSaleListProduct.setState(state);
            saleListProductRepository.save(shengyuSaleListProduct);
        }
//        Integer num = saleListProduct.getNum();
//        Double length = saleListProduct.getLength();
//        saleListProduct.setNum(num / count);
//        saleListProduct.setLength(length * num);
//        Integer newNum = num / count;
//        saleListProduct.setHebingLength(length + "*" + num);
//        if (newNum != 0) {
//            saleListProduct.setHebingLength(length + "*" + newNum);
//            SaleListProduct newSaleListProduct = new SaleListProduct();
//            BeanUtils.copyProperties(saleListProduct, newSaleListProduct);
//            newSaleListProduct.setNum(1);
//            newSaleListProduct.setLength(length * newNum);
//            newSaleListProduct.setHebingLength(length + "*" + newNum);
//            newSaleListProduct.setId(null);
//            saleListProductRepository.save(newSaleListProduct);
//        }
//        saleListProductRepository.save(saleListProduct);
    }

    @Override
    public void deleteById(int parseInt) {
        saleListProductRepository.delete(parseInt);
    }

    @Override
    public void dingdanjiaji(Integer id, String jiajidengji) {

        saleListProductRepository.dingdanjiaji(id, jiajidengji);
    }

    @Override
    public void chanpinjiaji(Integer id, String jiajidengji) {
        saleListProductRepository.chanpinjiaji(id, jiajidengji);
    }

    @Override
    public List<SaleListProduct> condition(SaleListProduct saleListProduct) {
        return saleListProductRepository.findAll(new Specification<SaleListProduct>() {
            @Override
            public Predicate toPredicate(Root<SaleListProduct> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (StringUtil.isNotEmpty(saleListProduct.getName())) {
                    predicate.getExpressions().add(cb.equal(root.get("name"), saleListProduct.getName()));
                }
                if (StringUtil.isNotEmpty(saleListProduct.getClientname())) {
                    predicate.getExpressions().add(cb.equal(root.get("clientname"), saleListProduct.getClientname()));
                }
                if (saleListProduct.getMeter() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("price"), saleListProduct.getMeter()));
                }
                if (saleListProduct.getModel() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("model"), saleListProduct.getModel()));
                }
                if (StringUtil.isNotEmpty(saleListProduct.getColor())) {
                    predicate.getExpressions().add(cb.equal(root.get("color"), saleListProduct.getColor()));
                }
                if (StringUtil.isNotEmpty(saleListProduct.getDao())) {
                    predicate.getExpressions().add(cb.equal(root.get("dao"), saleListProduct.getDao()));
                }
                predicate.getExpressions().add(cb.equal(root.get("jiTai"), saleListProduct.getJiTai()));
                predicate.getExpressions().add(cb.like(root.get("state"), "%下发机台%"));
                return predicate;
            }
        });
    }

    @Override
    public List<SaleListProduct> findByJitaiId(SaleListProduct saleListProduct) {
        return saleListProductRepository.findAll(new Specification<SaleListProduct>() {
            @Override
            public Predicate toPredicate(Root<SaleListProduct> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (saleListProduct.getJiTai() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("jiTai").get("id"), saleListProduct.getJiTai().getId()));
                }
                predicate.getExpressions().add(cb.like(root.get("issueState"), "%" + saleListProduct.getIssueState() + "%"));
                return predicate;
            }
        });
    }

    /***
     * 修改数量
     * @param num
     * @return
     */
    @Override
    public String updateNum(Integer num, Integer id) {
        SaleListProduct saleListProduct = saleListProductRepository.findOne(id);
        Integer wc = saleListProduct.getAccomplishNumber();
        if (wc == null) {
            wc = 0;
        }
        if (wc == num) {
            saleListProduct.setNum(num);
            saleListProduct.setState("生产完成：" + saleListProduct.getJiTai().getName());
//            saleListProductRepository.updateNum(num, id);
//            saleListProductRepository.updateState("生产完成：" + saleListProduct.getJiTai().getName(), id);
            saleListProduct.setSumwight(saleListProduct.getOneweight() * num);
            saleListProductRepository.save(saleListProduct);
            return "修改成功，数量等于完成数，修改订单状态为完成";
        } else if (wc > num) {
            return "已经完成比该数量多的件数，无法修改";
        } else {
            saleListProduct.setNum(num);
            saleListProduct.setSumwight(num * saleListProduct.getOneweight());
//            saleListProductRepository.updateNum(num, id);
            String state = saleListProductRepository.findOne(id).getState();
            saleListProductRepository.save(saleListProduct);
            if (state.startsWith("审核成功") || state.startsWith("未审核")) {
                return "修改成功！";
            }
            saleListProduct.setState("下发机台：" + saleListProduct.getJiTai().getName());
//            saleListProductRepository.updateState("下发机台：" + saleListProduct.getJiTai().getName(), id);
            saleListProductRepository.save(saleListProduct);
            return "修改成功，数量大于完成数，修改订单状态为下发机台";
        }
    }

    @Override
    public List<SaleListProduct> find(SaleListProduct saleListProduct) {
        return saleListProductRepository.findAll(new Specification<SaleListProduct>() {
            @Override
            public Predicate toPredicate(Root<SaleListProduct> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (saleListProduct.getJiTai() != null) {
                    if (saleListProduct.getJiTai().getId() != null) {
                        predicate.getExpressions().add(cb.equal(root.get("jiTai").get("id"), saleListProduct.getJiTai().getId()));
                    }
                }
                if (saleListProduct.getInformNumber() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("informNumber"), saleListProduct.getInformNumber()));
                }
                return predicate;
            }
        });
    }

    @Override
    public void updateName(Integer[] ids, String name) {
        saleListProductRepository.updateName(ids, name);
    }

    @Override
    public void updatLength(Integer[] ids, Double length) {
        saleListProductRepository.updatLength(ids, length);
    }

    @Override
    public void updatemodel(Integer[] ids, Double model) {
        saleListProductRepository.updatemodel(ids, model);
    }

    @Override
    public void updatPrice(Integer[] ids, Double price) {
        saleListProductRepository.updatPrice(ids, price);
    }

    @Override
    public void updatMeter(Integer[] ids, Double meter) {
        saleListProductRepository.updatMeter(ids, meter);
    }

    @Override
    public void updatOneweight(Integer[] ids, Double oneWeight) {
        for (int i = 0; i < ids.length; i++) {
            SaleListProduct saleListProduct = saleListProductRepository.findOne(ids[i]);
            Double sunWeight = saleListProduct.getNum() * oneWeight;
            saleListProductRepository.updatOneweight(ids[i], oneWeight, sunWeight);
        }
    }

    @Override
    public void updatPeasant(Integer[] ids, String peasant) {
        saleListProductRepository.updatPeasant(ids, peasant);
    }

    @Override
    public void updatColor(Integer[] ids, String color) {
        saleListProductRepository.updatColor(ids, color);
    }

    @Override
    public List<SaleListProduct> listBySaleListIdNoHeBing(Integer saleListId) {
        return saleListProductRepository.listBySaleListIdNoHeBing(saleListId);
    }

    @Override
    public List<SaleListProduct> findByDanhao(Long danhao) {
        return saleListProductRepository.findByDanhao(danhao);
    }

    @Override
    public void updatInfo(Integer[] ids, Integer info) {
        saleListProductRepository.updatInfo(ids, info);
    }

    @Override
    public void jitaiAllocation(Integer []ids, String state, Integer id, Long informNumber, String iussueState) {
        saleListProductRepository.jitaiAllocation(ids,state,id,informNumber,iussueState);
    }

    @Override
    public List<SaleListProduct> chaxunpeifang(String client) {
        return saleListProductRepository.chaxunpeifang(client);
    }

    @Override
    public List dingDanZhuiZongg(SaleListProduct saleListProduct) {
        String sqlStar = "select sale_number," +
                " clientname, " +
                "peasant, " +
                "a.name, " +
                "code, " +
                "c.name as jitainame, " +
                "length, model, " +
                "a.price, " +
                "IFNULL(accomplish_number,0) as wanchengshu, " +
                "num, IFNULL(num - accomplish_number,0) as shengyushu, " +
                "oneweight, " +
                "sumwight, " +
                "IFNULL(accomplish_number * oneweight,0) as wanchengzhongliang, " +
                "DATE_FORMAT(sale_date,'%Y-%m-%d %H:%i:%s') as sale_date, " +
                "inform_number, " +
                "IFNULL(sumwight-accomplish_number * oneweight,sumwight) as shengyuzhongliang, " +
                "a.state " +
                "from t_sale_list_product a LEFT JOIN t_sale_list b ON a.sale_list_id = b.id " +
                "LEFT JOIN t_jitai c ON a.jitai_id = c.id where true";

        String sql = "";

        if (StringUtil.isNotEmpty(saleListProduct.getQueryName())) {
            sql += " and b.sale_number = '" + saleListProduct.getQueryName() + "'";
        }
        if (StringUtil.isNotEmpty(saleListProduct.getClientname())) {
            sql += " and a.clientname = '" + saleListProduct.getClientname() + "'";
        }
        if (StringUtil.isNotEmpty(saleListProduct.getPeasant())) {
            sql += " and a.peasant = '" + saleListProduct.getPeasant() + "'";
        }
        if (StringUtil.isNotEmpty(saleListProduct.getName())) {
            sql += " and a.name = '" + saleListProduct.getName() + "'";
        }
        if (saleListProduct.getInformNumber() != null) {
            sql += " and a.inform_number = '" + saleListProduct.getInformNumber() + "'";
        }
        if (saleListProduct.getModel() != null) {
            sql += " and a.model = " + saleListProduct.getModel();
        }
        if (saleListProduct.getLength() != null) {
            sql += " and a.length = " + saleListProduct.getLength();
        }
        if (StringUtil.isNotEmpty(saleListProduct.getPrice())) {
            sql += " and a.price = " + saleListProduct.getPrice();
        }
        if (saleListProduct.getJiTai() != null) {
            sql += " and c.id = " + saleListProduct.getJiTai().getId();
        }
        if (saleListProduct.getOneweight() != null) {
            sql += " and a.oneweight = " + saleListProduct.getOneweight();
        }

        LogUtil.printLog("=====销售订单追踪=====");
        LogUtil.printLog("执行的sql：" + sqlStar + sql);

        List result = GetResultUtils.getResult(sqlStar + sql, entityManager);

        System.out.println(result.size());

        return result;
    }

    @Override
    public List<SaleListProduct> dingDanZhuiZong(SaleListProduct saleListProduct) {
        return saleListProductRepository.findAll(new Specification<SaleListProduct>() {
            @Override
            public Predicate toPredicate(Root<SaleListProduct> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (StringUtil.isNotEmpty(saleListProduct.getName())) {
                    predicate.getExpressions().add(cb.equal(root.get("saleList").get("saleNumber"), saleListProduct.getName()));
                }
                if (StringUtil.isNotEmpty(saleListProduct.getClientname())) {
                    predicate.getExpressions().add(cb.equal(root.get("clientname"), saleListProduct.getClientname()));
                }
                if (StringUtil.isNotEmpty(saleListProduct.getPeasant())) {
                    predicate.getExpressions().add(cb.equal(root.get("peasant"), saleListProduct.getPeasant()));
                }
                if (saleListProduct.getInformNumber() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("informNumber"), saleListProduct.getInformNumber()));
                }
                if (saleListProduct.getModel() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("model"), saleListProduct.getModel()));
                }
                if (saleListProduct.getLength() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("length"), saleListProduct.getLength()));
                }
                if (StringUtil.isNotEmpty(saleListProduct.getPrice())) {
                    predicate.getExpressions().add(cb.equal(root.get("price"), saleListProduct.getPrice()));
                }
                if (saleListProduct.getJiTai() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("jiTai").get("id"), saleListProduct.getJiTai().getId()));
                }
                if (saleListProduct.getOneweight() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("oneweight"), saleListProduct.getOneweight()));
                }
                return predicate;
            }
        });
    }
}
