package com.inspark.sabeel.jobOffer.application.dto.request;

import java.util.Date;

public record JobOfferRequestDto(
        String title,
        String description,
        Date date,
        String place,
        String source,
        String nbPosts,
        String contact,
        String postDate,
        String sendResumeLink
) {
}
