package es.module2.imapi.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

import es.module2.imapi.model.HealthStatus;
import es.module2.imapi.model.Intrusion;
import es.module2.imapi.model.IntrusionDetectedDTO;
import es.module2.imapi.repository.IntrusionRepository;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
@ConditionalOnProperty(name = "keycloak.enabled", havingValue = "true", matchIfMissing = true)
public class IMAPIService {

    private static final Logger log = LoggerFactory.getLogger(Service.class);

    @Value("${s3.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private Producer producer;

    @Autowired
    private IntrusionRepository intrusionRepository;

    public void uploadFile(String fileName, MultipartFile multipartFile)
            throws S3Exception, AwsServiceException, SdkClientException, IOException {
        log.info("Service -> Upload file method");

        // Filename: "cam"+str(self.camera_id)+"Video"+dict["timestamp"]}

        int indexV = fileName.indexOf("V");
        int indexC = fileName.indexOf("c");
        String substring = fileName.substring(0, indexV);
        String lastPart = fileName.substring(indexV);
        String firstPart = substring.substring(0,indexC);
        String middlePart = substring.substring(indexC);

        File fileObj = convertMultiPartFileToFile(multipartFile);
        activate_alarms(firstPart.substring(6));

        s3Client.putObject(new PutObjectRequest(bucketName, firstPart + "/" + middlePart + "/" + lastPart + ".mp4", fileObj));
        fileObj.delete();

        long propertyId = Long.valueOf(firstPart.substring(6)); //PropIdAAA -> we want AAA
        String cameraId = middlePart.substring(3); //camAAA -> we want AAA
        String timestamp = lastPart.substring(5); //VideoAAA -> we want AAA

        Intrusion intrusion = new Intrusion(propertyId, cameraId, timestamp, firstPart + "/" + middlePart + "/" + lastPart + ".mp4");

        intrusionRepository.saveAndFlush(intrusion);
    }

    public void intrusion(IntrusionDetectedDTO intrusion) {
        log.info("Service -> intrusion method");
        // add rabbit mq
        producer.send(intrusion);
    }

    public void activate_alarms(String PropId) {
        log.info("Service -> activate alarms method");
        // add rabbit mq


        producer.activate_alarms(PropId);
    }

    public HealthStatus getHealthStatus() {
        return new HealthStatus(true, new ArrayList<>());
    }

    private File convertMultiPartFileToFile(MultipartFile multipartFile){
        File convertedFile = new File(multipartFile.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(multipartFile.getBytes());
        } catch (IOException e){
            System.out.println("Error converting multipartFIle to file " + e);
        }
        return convertedFile;
    }
}
