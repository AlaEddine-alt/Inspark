package com.inspark.sabeel.jobOffer.application.dto.response;

import java.util.Date;
import java.util.UUID;

public record JobOfferResponseDto(
        UUID id,
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
