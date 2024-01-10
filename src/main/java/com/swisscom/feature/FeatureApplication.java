package com.swisscom.feature;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.swisscom.feature.entity.FeatureEntity;
import com.swisscom.feature.repos.FeatureRepo;

@SpringBootApplication
public class FeatureApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeatureApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(FeatureRepo featureRepo) {
		return (args) -> {

			FeatureEntity featurea = new FeatureEntity("my-feature-a", "my-feature-a", new Date(), "discription", false,
					true);
			FeatureEntity featureb = new FeatureEntity("my-feature-b", "my-feature-b", new Date(), "discription", true,
					false);
			FeatureEntity featurec = new FeatureEntity("my-feature-c", "my-feature-c", new Date(), "discription", false,
					true);
			FeatureEntity featured = new FeatureEntity("my-feature-d", "my-feature-d", new Date(), "discription", false,
					true);
			featureRepo.save(featurea);
			featureRepo.save(featureb);
			featureRepo.save(featurec);
			featureRepo.save(featured);
		};
	}

}
