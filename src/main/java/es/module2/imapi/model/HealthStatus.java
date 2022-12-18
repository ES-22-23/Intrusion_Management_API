package es.module2.imapi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class HealthStatus {

    @JsonProperty("isHealthy")
    private final boolean isHealthy;
    private final List<?> additionalProperties;

}
