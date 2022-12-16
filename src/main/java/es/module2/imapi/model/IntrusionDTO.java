package es.module2.imapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntrusionDTO {
    
    private long id;

    private long propertyId;

    private String cameraId;

    private String timestamp;

    private String videoKey;
}
