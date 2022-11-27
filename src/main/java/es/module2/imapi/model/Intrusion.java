package es.module2.imapi.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Intrusion {

    private String cameraId;

    @NonNull
    private String timestamp;


    public String getCameraId() {
        return this.cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    @Override
    public String toString() {
        return "{" +
            " \"cameraId\":\"" + getCameraId() +"\"" +
            ", \"timestamp\":\"" + getTimestamp() + "\"" +
            "}";
    }

}
