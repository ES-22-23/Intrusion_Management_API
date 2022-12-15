package es.module2.imapi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="events")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Intrusion implements Serializable{

    @Id 
    @Column(name = "event_id", nullable = false, unique=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private long id;

    private long propertyId;

    private String cameraId;

    private String timestamp;

    private String videoKey; 

    public Intrusion(long propertyId, String cameraId, String timestamp, String videoKey) {
        this.propertyId = propertyId;
        this.cameraId = cameraId;
        this.timestamp = timestamp;
        this.videoKey = videoKey;
    }
}