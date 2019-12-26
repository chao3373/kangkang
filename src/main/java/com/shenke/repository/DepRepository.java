package com.shenke.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.shenke.entity.Dep;

/**
 * 部门管理Repository
 * @author Administrator
 *
 */
public interface DepRepository extends JpaRepository<Dep, Integer>, JpaSpecificationExecutor<Dep>{

	/**
	 * 根据父节点查询所有子节点
	 * @param parentId
	 * @return
	 */
	@Query(value="select * from t_dep where p_id=?1", nativeQuery=true)
	public List<Dep> findByParentId(Integer parentId);
	
}
