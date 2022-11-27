package es.module2.imapi.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Intrusion {

    private long cameraId;

    @NonNull
    private String timestamp;


    public long getCameraId() {
        return this.cameraId;
    }

    public void setCameraId(long cameraId) {
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
            " \"cameraId\":" + getCameraId()  +
            ", \"timestamp\":\"" + getTimestamp() + "\"" +
            "}";
    }

}
