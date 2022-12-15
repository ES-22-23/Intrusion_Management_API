package es.module2.imapi.intrusion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amazonaws.services.s3.AmazonS3;

import es.module2.imapi.model.Intrusion;
import es.module2.imapi.repository.IntrusionRepository;
import es.module2.imapi.service.IntrusionService;

@ExtendWith(MockitoExtension.class)
public class IntrusionServiceTest {

    @Mock(lenient = true)
    private AmazonS3 s3Client;

    @Mock(lenient = true)
    private IntrusionRepository intrusionRepository;

    @InjectMocks
    private IntrusionService service;

    Intrusion intrusion1;
    Intrusion intrusion2;
    Intrusion intrusion3;

    @BeforeEach
    void setUp(){
        service.setBucketName("hdm-bucket");

        intrusion1 = new Intrusion(1, 1, "camera1", "timestamp1", "prop1/cam1/timestamp1");
        intrusion2 = new Intrusion(2, 1, "camera2", "timestamp2", "prop1/cam2/timestamp2");
        intrusion3 = new Intrusion(3, 2, "camera3", "timestamp3", "prop2/cam3/timestamp3");
    }


    @Test
    void testGetVideoUrl() throws IOException{

        Mockito.when(s3Client.generatePresignedUrl(any())).thenReturn(new URL("https://hdm-bucket.s3.eu-west-2.amazonaws.com/propId8/cam111cc11-165a-445a-b062-9b7a16195dd6/Video2022-11-21%2020%3A46%3A12.66666.mp4?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20221214T115939Z&X-Amz-SignedHeaders=host&X-Amz-Expires=1799&X-Amz-Credential=AKIA3QKXUEUECZE33MP4%2F20221214%2Feu-west-2%2Fs3%2Faws4_request&X-Amz-Signature=e96b2dee730857ae1585b053a8e059e92a95b0694701adf651f8f5db66b9a132"));
        Mockito.when(intrusionRepository.findById(any())).thenReturn(Optional.of(intrusion1));

        String expected = "https://hdm-bucket.s3.eu-west-2.amazonaws.com/propId8/cam111cc11-165a-445a-b062-9b7a16195dd6/Video2022-11-21%2020%3A46%3A12.66666.mp4?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20221214T115939Z&X-Amz-SignedHeaders=host&X-Amz-Expires=1799&X-Amz-Credential=AKIA3QKXUEUECZE33MP4%2F20221214%2Feu-west-2%2Fs3%2Faws4_request&X-Amz-Signature=e96b2dee730857ae1585b053a8e059e92a95b0694701adf651f8f5db66b9a132";
        String actual = service.getVideoUrl(1);

        assertEquals(expected, actual);
    }

    @Test
    void testGetVideoKeysFromProperty(){

        List<Intrusion> allEvents = new ArrayList<>();
        allEvents.add(intrusion1);
        allEvents.add(intrusion2);

        Mockito.when(intrusionRepository.findByPropertyId(1)).thenReturn(allEvents);

        List<String> result = service.getVideoKeysFromProperty(1);

        assertEquals(allEvents.size(), result.size());
        assertTrue(result.contains(intrusion1.getVideoKey()));
        assertTrue(result.contains(intrusion2.getVideoKey()));
    }

    @Test
    void testGetAllVideoKeys(){

        List<Intrusion> allEvents = new ArrayList<>();
        allEvents.add(intrusion1);
        allEvents.add(intrusion2);
        allEvents.add(intrusion3);

        Mockito.when(intrusionRepository.findAll()).thenReturn(allEvents);

        List<String> result = service.getAllVideoKeys();

        assertEquals(allEvents.size(), result.size());
        assertTrue(result.contains(intrusion1.getVideoKey()));
        assertTrue(result.contains(intrusion2.getVideoKey()));
        assertTrue(result.contains(intrusion3.getVideoKey()));
    }

    @Test
    void testGetVideoKeysFromCamera(){

        List<Intrusion> allEvents = new ArrayList<>();
        allEvents.add(intrusion2);

        Mockito.when(intrusionRepository.findByCameraId("2")).thenReturn(allEvents);

        List<String> result = service.getVideoKeysFromCamera("2");

        assertEquals(allEvents.size(), result.size());
        assertTrue(result.contains(intrusion2.getVideoKey()));
        assertTrue(result.contains(intrusion2.getVideoKey()));
    }
}
