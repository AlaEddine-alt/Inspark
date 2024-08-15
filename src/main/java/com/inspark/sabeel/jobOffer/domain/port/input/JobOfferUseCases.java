package com.inspark.sabeel.jobOffer.domain.port.input;

import com.inspark.sabeel.jobOffer.domain.model.JobOffer;
import com.inspark.sabeel.user.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface JobOfferUseCases {

    // Get all job offers
    Page<JobOffer> findAllWithPaginationAndFiltering(String criteria, Pageable pageable);

    // Find a job offer by ID
    JobOffer findJobOfferById(UUID id);

    // Create a new job offer
    JobOffer createJobOffer(JobOffer jobOffer);

    // Update an existing job offer
    JobOffer updateJobOffer(UUID id, JobOffer updatedJobOffer);

    // Delete a job offer by ID
    void deleteJobOffer(UUID id);
}
