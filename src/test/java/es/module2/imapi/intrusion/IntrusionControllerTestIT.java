package es.module2.imapi.intrusion;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.is;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;

import es.module2.imapi.ImapiApplication;
import es.module2.imapi.model.Intrusion;
import es.module2.imapi.repository.IntrusionRepository;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = ImapiApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class IntrusionControllerTestIT {
    
    @Autowired
    private MockMvc mvc;

    @Autowired
    private IntrusionRepository intrusionRepository;

    Intrusion intrusion1;
    Intrusion intrusion2;
    Intrusion intrusion3;

    @BeforeEach
    void setUp() throws JsonProcessingException{

        RestAssuredMockMvc.mockMvc( mvc );

        intrusion1 = new Intrusion(1, "1", "timestamp1", "prop1/cam1/timestamp1");
        intrusion2 = new Intrusion(1, "2", "timestamp2", "prop1/cam2/timestamp2");
        intrusion3 = new Intrusion(2, "3", "timestamp3", "prop2/cam3/timestamp3");

        intrusion1 = intrusionRepository.saveAndFlush(intrusion1);
        intrusion2 = intrusionRepository.saveAndFlush(intrusion2);
        intrusion3 = intrusionRepository.saveAndFlush(intrusion3);
    }

    @AfterEach
    void cleanUp(){
        intrusionRepository.deleteAll();
    }

    @Test
     void testgetAllVideos() throws IOException, Exception {

        given().get("/intrusions")
        .then().log().body().assertThat()
        .status(HttpStatus.OK).and()
        .contentType(ContentType.JSON).and()
        .body("[0]", is(intrusion1.getVideoKey())).and()
        .body("[1]", is(intrusion2.getVideoKey())).and()
        .body("[2]", is(intrusion3.getVideoKey()));
    }

    @Test
     void testgetAllVideosFromProperty() throws IOException, Exception {
       
        given().get("/intrusions/property/1")
        .then().log().body().assertThat()
        .status(HttpStatus.OK).and()
        .contentType(ContentType.JSON).and()
        .body("[0]", is(intrusion1.getVideoKey())).and()
        .body("[1]", is(intrusion2.getVideoKey()));
    }

    @Test
    void testgetAllVideosFromCamera() throws IOException, Exception {
       
        given().get("/intrusions/camera/1")
        .then().log().body().assertThat()
        .status(HttpStatus.OK).and()
        .contentType(ContentType.JSON).and()
        .body("[0]", is(intrusion1.getVideoKey()));
    }

    @Test
    void testgetAllVideosFromMultipleProperties() throws IOException, Exception {
       
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);
        list.add(3L);

        given().contentType(ContentType.JSON).body(list)
        .get("/intrusions/properties")
        .then().log().body().assertThat()
        .status(HttpStatus.OK).and()
        .contentType(ContentType.JSON).and()
        .body("[0]", is(intrusion1.getVideoKey())).and()
        .body("[1]", is(intrusion2.getVideoKey())).and()
        .body("[2]", is(intrusion3.getVideoKey()));
    }
}
