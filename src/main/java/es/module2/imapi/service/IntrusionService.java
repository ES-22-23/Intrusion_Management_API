package es.module2.imapi.service;

import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import es.module2.imapi.model.Intrusion;
import es.module2.imapi.model.IntrusionDTO;
import es.module2.imapi.repository.IntrusionRepository;

@Service
public class IntrusionService {
    
    @Value("${s3.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private IntrusionRepository intrusionRepository;

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    } 

    public String getVideoUrl(long eventId){

        Optional<Intrusion> eventOptional = intrusionRepository.findById(eventId);

        if (eventOptional.isEmpty()){
            return null;
        }

        String videoKey = eventOptional.get().getVideoKey();

        try {
            // Set the presigned URL to expire after half an  hour.
            java.util.Date expiration = new java.util.Date();
            long expTimeMillis = Instant.now().toEpochMilli();
            expTimeMillis += 1000 * 30 * 60;
            expiration.setTime(expTimeMillis);

            // Generate the presigned URL.
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, videoKey)
                            .withMethod(HttpMethod.GET)
                            .withExpiration(expiration);
            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
            
            return url.toString();
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process 
            // it, so it returned an error response.
            e.printStackTrace();
            return null;
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
            return null;
        }
    }

    public List<IntrusionDTO> getAllVideoKeys(){
          
        List<Intrusion> allEvents = intrusionRepository.findAll();

        List<IntrusionDTO> objectKeys = new ArrayList<>();

        for (Intrusion event : allEvents) {
            objectKeys.add(event.convertToDTO());
        }

        return objectKeys;
    }

    public List<IntrusionDTO> getVideoKeysFromMultipleProperties(List<Long> propertiesIds){
        
        List<IntrusionDTO> objectKeys = new ArrayList<>();

        for (long propertyId : propertiesIds) {
            
            objectKeys.addAll(getVideoKeysFromProperty(propertyId));
        }

        return objectKeys;
    }

    public List<IntrusionDTO> getVideoKeysFromProperty(long propertyId){
        
        List<Intrusion> allIntrusions = intrusionRepository.findByPropertyId(propertyId);

        List<IntrusionDTO> objectKeys = new ArrayList<>();

        for (Intrusion intrusion : allIntrusions) {
            
            objectKeys.add(intrusion.convertToDTO());
        }

        return objectKeys;
    }

    public List<IntrusionDTO> getVideoKeysFromCamera(String cameraId){
        
        List<Intrusion> allEvents = intrusionRepository.findByCameraId(cameraId);

        List<IntrusionDTO> objectKeys = new ArrayList<>();

        for (Intrusion event : allEvents) {
            objectKeys.add(event.convertToDTO());
        }

        return objectKeys;
    }
}