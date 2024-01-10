package com.swisscom.feature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import com.swisscom.feature.dao.FeatureRequest;
import com.swisscom.feature.dao.FeatureResponse;
import com.swisscom.feature.entity.FeatureEntity;
import com.swisscom.feature.exceptionhandler.ResourceNotFoundException;
import com.swisscom.feature.repos.FeatureRepo;

@SpringBootTest
class FeatureServiceTest {

	@Mock
	private FeatureRepo featureRepo;

	@Mock
	private FeatureResponse featureResponse;

	@InjectMocks
	private FeatureService featureService;

	@Test
	void testGetAllFeature_Success() throws ResourceNotFoundException {
		// Arrange
		List<FeatureEntity> mockFeatures = new ArrayList<>();
        mockFeatures.add(new FeatureEntity());
        when(featureResponse.getFeatures()).thenReturn(mockFeatures);
        when(featureRepo.findAll()).thenReturn(mockFeatures);

        // Act
        FeatureResponse result = featureService.getAllFeature();

        // Assert
        assertEquals(mockFeatures, result.getFeatures());
	}

	@Test
	void testGetAllFeature_ResourceNotFoundException() {
		// Arrange
		when(featureRepo.findAll()).thenReturn(new ArrayList<>());

		// Act & Assert
		assertThrows(ResourceNotFoundException.class, () -> featureService.getAllFeature());
	}

	@Test
	void testSaveFeature_Success() throws Exception {
		// Arrange
		FeatureEntity features = new FeatureEntity("my-feature-a", "my-feature-a", new Date(), "discription", false,
				true);
		FeatureRequest mockFeatureRequest = new FeatureRequest("1234", Arrays.asList(features));

		// Act
		featureService.saveFeature(mockFeatureRequest);

		// Assert
		verify(featureRepo, times(1)).saveAll(anyList());
	}

	@Test
	void testSaveFeature_Exception() {
		// Arrange
		FeatureRequest mockFeatureRequest = new FeatureRequest();
		doThrow(new RuntimeException("Some error")).when(featureRepo).saveAll(anyList());

		// Act & Assert
		assertThrows(Exception.class, () -> featureService.saveFeature(mockFeatureRequest));
	}
}
