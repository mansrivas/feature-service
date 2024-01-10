package com.swisscom.feature.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swisscom.feature.entity.FeatureEntity;

@Repository
public interface FeatureRepo extends JpaRepository<FeatureEntity, String> {

}
