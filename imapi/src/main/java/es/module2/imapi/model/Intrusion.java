package es.module2.imapi.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Intrusion {

    @NonNull
    private String cameraId;

    @NonNull
    private String timestamp;

    @Override
    public String toString() {
        return "{" +
            " cameraId='" + getCameraId() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            "}";
    }

}
