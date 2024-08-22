package com.inspark.sabeel.jobOffer.domain.service;

import com.inspark.sabeel.exception.ConflictException;
import com.inspark.sabeel.exception.NotFoundException;
import com.inspark.sabeel.jobOffer.domain.model.JobOffer;
import com.inspark.sabeel.jobOffer.domain.port.input.JobOfferUseCases;
import com.inspark.sabeel.jobOffer.domain.port.output.JobOffers;
import com.inspark.sabeel.user.domain.model.User;
import com.inspark.sabeel.user.domain.port.output.Users;
import lombok.RequiredArgsConstructor;
import org.hibernate.StaleObjectStateException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JobOfferService implements JobOfferUseCases {

    private final JobOffers jobOffers;

    @Override
    public Page<JobOffer> findAllWithPaginationAndFiltering(String criteria, Pageable pageable) {
        return jobOffers.findAll(criteria, pageable);
    }

    @Override
    public JobOffer findJobOfferById(UUID id) {
        return jobOffers.findById(id)
                .orElseThrow(() -> new NoSuchElementException("JobOffer with ID " + id + " not found"));
    }


    @Override
    public JobOffer createJobOffer(JobOffer jobOffer) {
        return jobOffers.create(jobOffer);
    }

    @Override
    public JobOffer updateJobOffer(UUID id, JobOffer updatedJobOffer) {
        var jobOfferToUpdate = jobOffers.findById(id)
                .orElseThrow(() -> new NoSuchElementException("JobOffer with ID " + id + " not found"));
        try {
            return jobOffers.update(jobOfferToUpdate);
        } catch (ObjectOptimisticLockingFailureException | StaleObjectStateException ex) {
            throw new ConflictException(ConflictException.ConflictExceptionType.CONFLICT_LOCK_VERSION);
        }

    }

    @Override
    public void deleteJobOffer(UUID id) {
        jobOffers.deleteById(id);
    }

    @Override
    public Set<JobOffer> findRecommandedJobs(User user) {
        return jobOffers.findRecommanded(user);
    }
}
