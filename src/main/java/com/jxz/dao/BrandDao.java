package com.jxz.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import com.jxz.domain.Brand;

public interface BrandDao extends JpaRepository<Brand, Long>{
	
	Optional<Brand> findById(Long id);
	
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })  
	@Query(value = "select obj from Brand obj where obj.userId=:userId")
	Optional<List<Brand>> findListByUserId(@Param("userId")Long userId);
	
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })  
	Page<Brand> findAll(Specification<Brand> spec, Pageable pageable);

}
