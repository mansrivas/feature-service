package com.swisscom.feature.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.swisscom.feature.entity.FeatureEntity;

@Component
public class FeatureResponse {
	private List<FeatureEntity> features;

	public List<FeatureEntity> getFeatures() {
		return features;
	}

	public void setFeatures(List<FeatureEntity> features) {
		this.features = features;
	}
	

}
