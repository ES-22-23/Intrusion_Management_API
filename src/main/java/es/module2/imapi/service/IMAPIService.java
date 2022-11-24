package es.module2.imapi.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;

import es.module2.imapi.model.HealthStatus;
import es.module2.imapi.model.Intrusion;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
public class IMAPIService {

    private static final Logger log = LoggerFactory.getLogger(Service.class);

    @Value("${s3.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private Producer producer;

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

        s3Client.putObject(new PutObjectRequest(bucketName, firstPart + "/" + middlePart + "/" + lastPart, fileObj));
        fileObj.delete();
    }

    public void intrusion(Intrusion intrusion) {
        log.info("Service -> intrusion method");
        // add rabbit mq
        producer.send(intrusion);
    }

    public HealthStatus getHealthStatus() {
        return new HealthStatus(true, true);
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