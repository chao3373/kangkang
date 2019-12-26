package com.shenke.repository;

import com.shenke.entity.JieSuanDan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JieSuanDanRepository extends JpaSpecificationExecutor<JieSuanDan>, JpaRepository<JieSuanDan, Integer> {

}
