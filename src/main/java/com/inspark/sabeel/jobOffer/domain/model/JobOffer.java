package com.inspark.sabeel.jobOffer.domain.model;

import com.inspark.sabeel.common.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class JobOffer extends BaseModel {

    private String title;
    private String description;
    private Date date;
    private String place;
}
