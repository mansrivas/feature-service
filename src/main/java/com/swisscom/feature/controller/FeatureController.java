package com.swisscom.feature.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.swisscom.feature.dao.FeatureRequest;
import com.swisscom.feature.dao.FeatureResponse;
import com.swisscom.feature.entity.FeatureEntity;
import com.swisscom.feature.exceptionhandler.ResourceNotFoundException;
import com.swisscom.feature.services.FeatureService;

@RestController
@RequestMapping("/api/v1/")
public class FeatureController {

	@Autowired
	private FeatureService featureService;

	@GetMapping("/features")
	public ResponseEntity<FeatureResponse> getAllFeature() throws Exception {
		try {
			FeatureResponse features = featureService.getAllFeature();
			return new ResponseEntity<>(features, HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}

	@PostMapping("/features")
	@CrossOrigin("*")
	public ResponseEntity<FeatureEntity> addNewFeature(@RequestBody FeatureRequest featureRequest) throws Exception {
		try {
			featureService.saveFeature(featureRequest);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duplicate record: " + e.getMessage(), e);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Internal server error: " + e.getMessage(), e);
		}

	}

}
