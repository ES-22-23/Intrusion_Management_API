package es.module2.imapi.model;

import lombok.Data;

@Data
public class HealthStatus {

    private final boolean isAvailable;
    private final boolean databaseAvailable;

}