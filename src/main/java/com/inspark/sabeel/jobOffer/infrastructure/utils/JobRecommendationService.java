package com.inspark.sabeel.jobOffer.infrastructure.utils;

import com.inspark.sabeel.jobOffer.domain.model.JobOffer;
import com.inspark.sabeel.jobOffer.infrastructure.entity.JobOfferEntity;
import com.inspark.sabeel.user.domain.model.User;
import com.inspark.sabeel.user.infrastructure.entity.UserEntity;
import org.apache.commons.text.similarity.JaccardSimilarity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class JobRecommendationService {
    private final JaccardSimilarity jaccardSimilarity = new JaccardSimilarity();

    // Compare job offer description and user skills
    public double calculateSimilarity(String jobDescription, String userSkills) {
        return jaccardSimilarity.apply(jobDescription, userSkills);
    }

    // Recommend job offers based on similarity
    public Set<JobOffer> recommendJobs(User user, List<JobOffer> allJobOffers) {
        Set<JobOffer> recommendedJobs = new HashSet<>();
        for (JobOffer jobOffer : allJobOffers) {
            double similarityScore = calculateSimilarity(jobOffer.getDescription(), user.getSkills());
            if (similarityScore > 0.7) { // threshold for recommendation
                recommendedJobs.add(jobOffer);
            }
        }
        return recommendedJobs;
    }
}
