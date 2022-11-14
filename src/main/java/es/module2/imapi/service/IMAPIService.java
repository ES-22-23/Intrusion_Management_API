package es.module2.imapi.service;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.amazonaws.SdkClientException;
import com.amazonaws.util.Base64;

import es.module2.imapi.model.Intrusion;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Service
public class IMAPIService {

    private static final Logger log = LoggerFactory.getLogger(Service.class);

    // @Autowired
    // private AmazonS3 amazonS3;

    @Autowired
    private Producer producer;

    @Value("${s3.bucket.name}")
    private static String s3BucketName;

    public void uploadFile(String fileName, InputStream inputStream)
            throws S3Exception, AwsServiceException, SdkClientException, IOException {
        log.info("Service -> Upload file method");
        
        S3Client client = S3Client.builder().build();
         
        PutObjectRequest request = PutObjectRequest.builder()
                                    .bucket(s3BucketName)
                                    .key(fileName)
                                    .build();
        client.putObject(request,
                RequestBody.fromInputStream(inputStream, inputStream.available()));
    }

    public void intrusion(Intrusion intrusion){
        log.info("Service -> intrusion method");
        //add rabbit mq
        producer.send(intrusion);
    }

}