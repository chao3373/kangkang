package com.shenke.util;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class GetResultUtils {

    //根据sql语句返回list结果集
    public static List getResult(String sql, EntityManager entityManager) {
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List resultList = query.getResultList();
        return resultList;
    }

    /***
     * 返回查询结果为Integer
     * @param sql 执行的sql语句
     * @param entityManager
     * @return
     */
    public static Integer getInteger(String sql, EntityManager entityManager) {
        Query countQuery = entityManager.createNativeQuery(sql);
        Object countResult = countQuery.getSingleResult();
        Integer it = 0;
        if (countResult != null && StringUtil.isNotEmpty(countResult.toString())) {
            it = Integer.parseInt(countResult.toString());
        }
        return it;
    }

    /***
     * 返回查询结果为Double
     * @param sql
     * @param entityManager
     * @return
     */
    public static Double getDouble(String sql, EntityManager entityManager) {
        Query sumWeightQuery = entityManager.createNativeQuery(sql);
        Object sumWeightResult = sumWeightQuery.getSingleResult();
        Double db = 0.0;
        if (sumWeightResult != null && StringUtil.isNotEmpty(sumWeightResult.toString())) {
            db = Double.parseDouble(sumWeightResult.toString());
        }

        return db;
    }

}
