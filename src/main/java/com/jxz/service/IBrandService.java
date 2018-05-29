package com.jxz.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.jxz.domain.Brand;

public interface IBrandService {
	
	Brand findById(Long id);
	
	List<Brand> findListByUserId(Long userId);
	
	void save(Brand brand);
	
	void update(Brand brand);
	
	void delete(Long id);
	
	Page<Brand> pageList(Long userId);

}
