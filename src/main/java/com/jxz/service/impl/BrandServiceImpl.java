package com.jxz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxz.dao.BrandDao;
import com.jxz.domain.Brand;
import com.jxz.service.IBrandService;
@Transactional
@Service
public class BrandServiceImpl implements IBrandService {
	
	@Autowired
	private BrandDao brandDao;

	@Override
	public Brand findById(Long id) {
		// TODO Auto-generated method stub
		Optional<Brand> brand = this.brandDao.findById(id);
		if(brand.isPresent()) {
			return brand.get();
		}
		return null;
	}

	@Override
	public List<Brand> findListByUserId(Long userId) {
		// TODO Auto-generated method stub
		Optional<List<Brand>> list = this.brandDao.findListByUserId(userId);
		if(list.isPresent()) {
			return list.get();
		}
		return new ArrayList<>();
	}

	@Override
	public void save(Brand brand) {
		// TODO Auto-generated method stub
		this.brandDao.save(brand);
		
	}

	@Override
	public void update(Brand brand) {
		// TODO Auto-generated method stub
		this.brandDao.save(brand);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		this.brandDao.deleteById(id);
		
	}

	@Override
	public Page<Brand> pageList(Long userId) {
		// TODO Auto-generated method stub
		Sort sort = new Sort(Sort.Direction.ASC,"addTime"); 
		Specification<Brand> specification = (root, query, cb)->{
			List<Predicate> predicate = new ArrayList<>();
			if(userId!=null) {
				predicate.add(cb.equal(root.get("userId").as(Long.class), userId));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			return query.where(predicate.toArray(pre)).getRestriction();
			
		};
        Pageable pageable = PageRequest.of(0, 10,sort);
        Page<Brand> page =  brandDao.findAll(specification,pageable);  
        return page;  
	}

	

}
