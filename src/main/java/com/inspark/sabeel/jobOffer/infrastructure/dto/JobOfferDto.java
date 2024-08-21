package com.inspark.sabeel.jobOffer.infrastructure.dto;

import java.util.Date;

public record JobOfferDto(
         String title,
         String description,
         Date date,
         String place,
          String source,
          String nb_posts,
          String contact,
          String post_date,
          String send_resume_link
) {
}
