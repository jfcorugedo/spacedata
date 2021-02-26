package com.jfcorugedo.spacedata.rockets.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Rocket {

    @JsonProperty("rocket_name")
    private String rocketName;

    @JsonProperty("cost_per_launch")
    private int costPerLaunch;

    @JsonProperty("success_rate_pct")
    private double successRatePct;
}
