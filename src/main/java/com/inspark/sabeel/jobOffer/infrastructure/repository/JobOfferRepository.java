package com.inspark.sabeel.jobOffer.infrastructure.repository;

import com.inspark.sabeel.jobOffer.infrastructure.entity.JobOfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface JobOfferRepository extends JpaRepository<JobOfferEntity, UUID>, JpaSpecificationExecutor<JobOfferEntity> {
}
