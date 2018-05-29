package com.jxz;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

@SpringBootApplication
@EnableAutoConfiguration
@EnableTransactionManagement
public class DemoDataJpaApplication extends WebMvcConfigurationSupport{

	public static void main(String[] args) {
		SpringApplication.run(DemoDataJpaApplication.class, args);
	}

	@Bean // 使用@Bean注入fastJsonHttpMessageConvert
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		// 1.需要定义一个Convert转换消息的对象
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		// 2.添加fastjson的配置信息，比如是否要格式化返回的json数据
		//
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		//SerializerFeature.PrettyFormat  格式化  可以不开
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.DisableCircularReferenceDetect);
		fastJsonConfig.setDateFormat("YYYY-MM-dd HH:mm:ss");
		fastJsonConfig.setCharset(Charset.forName("utf-8"));
		List<MediaType> mediaTypes = new ArrayList<>();
		mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		fastConverter.setSupportedMediaTypes(mediaTypes);
		// 3.在convert中添加配置信息
		fastConverter.setFastJsonConfig(fastJsonConfig);

		HttpMessageConverter<?> converter = fastConverter;
		return new HttpMessageConverters(converter);
	}
	
	
}
