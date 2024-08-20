package com.inspark.sabeel.jobOffer.domain.port.output;

import com.inspark.sabeel.jobOffer.domain.model.JobOffer;
import com.inspark.sabeel.user.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JobOffers {
    Optional<JobOffer> findById(UUID id);

    JobOffer update(JobOffer jobOffer);

    Page<JobOffer> findAll(String criteria, Pageable pageable);

    void deleteById(UUID id);

    JobOffer create(JobOffer jobOffer);

    List<JobOffer> findRecommanded(UUID id);


}
