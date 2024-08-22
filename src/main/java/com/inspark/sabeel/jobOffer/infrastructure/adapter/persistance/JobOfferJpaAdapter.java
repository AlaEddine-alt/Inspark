package com.inspark.sabeel.jobOffer.infrastructure.adapter.persistance;

import com.inspark.sabeel.jobOffer.domain.model.JobOffer;
import com.inspark.sabeel.jobOffer.domain.port.output.JobOffers;
import com.inspark.sabeel.jobOffer.infrastructure.adapter.specification.JobOfferSpec;
import com.inspark.sabeel.jobOffer.infrastructure.mapper.JobOfferMapper;
import com.inspark.sabeel.jobOffer.infrastructure.repository.JobOfferRepository;
import com.inspark.sabeel.jobOffer.infrastructure.utils.JobRecommendationService;
import com.inspark.sabeel.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JobOfferJpaAdapter implements JobOffers {

    private final JobOfferRepository jobOfferRepository;
    private final JobOfferMapper jobOfferMapper;
    private final JobRecommendationService jobRecommendationService;


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

    @Override
    public Set<JobOffer> findRecommanded(User user) {

        // Retrieve and map all JobOfferEntity objects to JobOffer objects
        List<JobOffer> jobOfferList = jobOfferRepository.findAll()
                .stream()
                .map(jobOfferMapper::toJobOffer)
                .collect(Collectors.toList());

        // Return the recommended jobs based on the User object
        return jobRecommendationService.recommendJobs(user, jobOfferList);
    }

}
