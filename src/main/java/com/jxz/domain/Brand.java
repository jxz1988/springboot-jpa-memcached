package com.jxz.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.repository.Lock;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE) 
@Entity
@Table(name = "wst_goods_brand")
public class Brand implements Serializable{
	/**
	 * 序列化接口，自动生成序列号
	 */
	private static final long serialVersionUID = -7741168269971132706L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;// 域模型id，这里为自增类型 
    @Column(updatable = false, nullable = false,length = 20)  
	private Date addTime;// 添加时间，这里为长时间格式
	@Column(columnDefinition = "int default 0")
	private int deleteStatus;// 是否删除,默认为0未删除，-1表示删除状态

	private Long userId;

	private String userName;

	private String goodsBrandName;
	@Column(columnDefinition = "int default 0")

	private int goodsBrandSequence;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public int getDeleteStatus() {
		return deleteStatus;
	}
	public void setDeleteStatus(int deleteStatus) {
		this.deleteStatus = deleteStatus;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getGoodsBrandName() {
		return goodsBrandName;
	}
	public void setGoodsBrandName(String goodsBrandName) {
		this.goodsBrandName = goodsBrandName;
	}
	public int getGoodsBrandSequence() {
		return goodsBrandSequence;
	}
	public void setGoodsBrandSequence(int goodsBrandSequence) {
		this.goodsBrandSequence = goodsBrandSequence;
	}
	
	

}
