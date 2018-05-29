package com.jxz.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jxz.domain.Brand;
import com.jxz.service.IBrandService;

@Controller
public class BrandAction {
	
	@Autowired
	private IBrandService brandService;
	
	@RequestMapping(value = "/brandList")
	@ResponseBody
    public List<Brand> getUserList(Long userId) {
        return brandService.findListByUserId(userId);
    }

	@RequestMapping(value = "/brand")
    public ModelAndView getUserInfoByName(Long id) {
		Brand brand = this.brandService.findById(id);
		ModelAndView mv = new ModelAndView("brand");
		mv.addObject("brand", brand);
        return mv;
    }
	
	@RequestMapping(value = "/brandPage")
	@ResponseBody
    public Page<Brand> brandPage(Long userId) {
        return brandService.pageList(userId);
    }

}
