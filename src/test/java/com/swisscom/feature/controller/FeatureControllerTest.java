package com.swisscom.feature.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import com.swisscom.feature.dao.FeatureRequest;
import com.swisscom.feature.dao.FeatureResponse;
import com.swisscom.feature.entity.FeatureEntity;
import com.swisscom.feature.exceptionhandler.ResourceNotFoundException;
import com.swisscom.feature.services.FeatureService;

public class FeatureControllerTest {

	@Mock
	private FeatureService featureService;

	@InjectMocks
	private FeatureController featureController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testGetAllFeature_Success() throws Exception {
		// Arrange
		FeatureResponse mockFeatureResponse = new FeatureResponse();
		FeatureEntity features = new FeatureEntity("my-feature-a", "my-feature-a", new Date(), "discription", false,
				true);
		mockFeatureResponse.setFeatures(Arrays.asList(features));
		when(featureService.getAllFeature()).thenReturn(mockFeatureResponse);

		// Act
		ResponseEntity<FeatureResponse> response = featureController.getAllFeature();

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockFeatureResponse, response.getBody());
	}

	@Test
	void testGetAllFeature_ResourceNotFoundException() throws Exception {
		// Arrange
		when(featureService.getAllFeature()).thenThrow(new ResourceNotFoundException("Feature not found"));

		// Act & Assert
		ResponseStatusException exception = org.junit.jupiter.api.Assertions.assertThrows(ResponseStatusException.class,
				() -> featureController.getAllFeature());
		assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
		assertEquals("Feature not found", exception.getReason());
	}

	@Test
	void testAddNewFeature_Success() throws Exception {
		// Arrange
		FeatureEntity features = new FeatureEntity("my-feature-a", "my-feature-a", new Date(), "discription", false,
				true);
		FeatureRequest mockFeatureRequest = new FeatureRequest("1234", Arrays.asList(features));

		// Act
		ResponseEntity<FeatureEntity> response = featureController.addNewFeature(mockFeatureRequest);

		// Assert
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		verify(featureService, times(1)).saveFeature(mockFeatureRequest);
	}

	@Test
	void testAddNewFeature_DataIntegrityViolationException() throws Exception {
		// Arrange
		FeatureRequest mockFeatureRequest = new FeatureRequest();
		doThrow(new DataIntegrityViolationException("Duplicate record")).when(featureService)
				.saveFeature(mockFeatureRequest);

		// Act & Assert
		ResponseStatusException exception = org.junit.jupiter.api.Assertions.assertThrows(ResponseStatusException.class,
				() -> featureController.addNewFeature(mockFeatureRequest));
		assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		assertEquals("Duplicate record: Duplicate record", exception.getReason());
	}

	@Test
	void testAddNewFeature_InternalServerError() throws Exception {
		FeatureRequest mockFeatureRequest = new FeatureRequest();
		doThrow(new RuntimeException("Internal server error")).when(featureService).saveFeature(mockFeatureRequest);

		// Act & Assert
		ResponseStatusException exception = org.junit.jupiter.api.Assertions.assertThrows(ResponseStatusException.class,
				() -> featureController.addNewFeature(mockFeatureRequest));
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
		assertEquals("Internal server error: Internal server error", exception.getReason());
	}

}
