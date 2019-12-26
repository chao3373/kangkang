package com.shenke.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.shenke.entity.Log;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 系统日志Repository接口
 * @author Administrator
 *
 */
public interface LogRepository extends JpaRepository<Log, Integer>, JpaSpecificationExecutor<Log>{

    @Modifying
    @Query(value = "delete from t_log where user_id = ?1", nativeQuery = true)
    void deleteByUserId(Integer id);
}
