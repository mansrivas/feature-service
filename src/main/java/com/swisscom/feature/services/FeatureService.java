package com.swisscom.feature.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swisscom.feature.dao.FeatureRequest;
import com.swisscom.feature.dao.FeatureResponse;
import com.swisscom.feature.entity.FeatureEntity;
import com.swisscom.feature.exceptionhandler.ResourceNotFoundException;
import com.swisscom.feature.repos.FeatureRepo;

@Service
public class FeatureService {

	private final FeatureRepo featureRepo;
	private final FeatureResponse featureResponse;

	@Autowired
	public FeatureService(FeatureRepo featureRepo, FeatureResponse featureResponse) {
		this.featureRepo = featureRepo;
		this.featureResponse = featureResponse;
	}

	public FeatureResponse getAllFeature() throws ResourceNotFoundException {
		List<FeatureEntity> features = featureRepo.findAll();
		if (features.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		featureResponse.setFeatures(features);
		return featureResponse;
	}

	public void saveFeature(FeatureRequest featureRequest) throws Exception {
		try {
			String customerID = featureRequest.getCustomerId();
			List<FeatureEntity> updatedFeatures = featureRequest.getFeatures().stream().map(feature -> {
				feature.setCustomerIds(customerID);
				return feature;
			}).collect(Collectors.toList());

			featureRepo.saveAll(updatedFeatures);
		} catch (Exception e) {
			throw new Exception();
		}

	}

}
