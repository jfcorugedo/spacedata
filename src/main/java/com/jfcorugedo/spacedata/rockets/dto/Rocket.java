package com.jfcorugedo.spacedata.rockets.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Rocket {

    @JsonProperty("rocket_name")
    private String rocketName;
}
