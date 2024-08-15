package com.inspark.sabeel.jobOffer.infrastructure.adapter;

import com.inspark.sabeel.auth.infrastructure.adapter.specifications.UserSpec;
import com.inspark.sabeel.jobOffer.domain.model.JobOffer;
import com.inspark.sabeel.jobOffer.domain.port.output.JobOffers;
import com.inspark.sabeel.jobOffer.infrastructure.mapper.JobOfferMapper;
import com.inspark.sabeel.jobOffer.infrastructure.repository.JobOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JobOfferJpaAdapter implements JobOffers {
    private final JobOfferRepository jobOfferRepository;
    private final JobOfferMapper jobOfferMapper;


    @Override
    public Optional<JobOffer> findById(UUID id) {
        return jobOfferRepository.findById(id)
                .map(jobOfferMapper::toJobOffer);
    }

    @Override
    public JobOffer update(JobOffer jobOffer) {
        return jobOfferMapper.toJobOffer(jobOfferRepository.save(jobOfferMapper.toJobOfferEntity(jobOffer)));
    }

    @Override
    public Page<JobOffer> findAll(String criteria, Pageable pageable) {
        return jobOfferRepository.findAll(JobOfferSpec.hasCriteria(criteria), pageable)
                .map(jobOfferMapper::toJobOffer);
    }

    @Override
    public void deleteById(UUID id) {
        jobOfferRepository.deleteById(id);
    }

    @Override
    public JobOffer create(JobOffer jobOffer) {
        return jobOfferMapper.toJobOffer(jobOfferRepository.save(jobOfferMapper.toJobOfferEntity(jobOffer)));
    }
}
