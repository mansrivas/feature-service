package com.swisscom.feature.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.swisscom.feature.entity.FeatureEntity;

@Component
public class FeatureRequest {
	private String customerId;
	private List<FeatureEntity> features;
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public List<FeatureEntity> getFeatures() {
		return features;
	}
	public void setFeatures(List<FeatureEntity> features) {
		this.features = features;
	}
	public FeatureRequest(String customerId, List<FeatureEntity> features) {
		super();
		this.customerId = customerId;
		this.features = features;
	}
	public FeatureRequest() {
		super();
	}
	
	
	
}
