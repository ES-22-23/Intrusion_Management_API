package es.module2.imapi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.module2.imapi.model.IntrusionDTO;
import es.module2.imapi.service.IntrusionService;

@RestController
@RequestMapping("/intrusions")
@Validated
public class IntrusionController {
    private static final Logger log = LoggerFactory.getLogger(IntrusionController.class);

    @Autowired
    private IntrusionService service;

    @GetMapping()
    public ResponseEntity<List<IntrusionDTO>> getAllVideos(){
        log.info("GET Request -> Get all videos");

        return new ResponseEntity<>(service.getAllVideoKeys(), HttpStatus.OK);
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<IntrusionDTO>> getVideosFromProperty(@PathVariable long propertyId) {
        log.info("GET Request -> Get videos from Property");
        
        return new ResponseEntity<>(service.getVideoKeysFromProperty(propertyId), HttpStatus.OK);
    }

    @GetMapping("/camera/{cameraId}")
    public ResponseEntity<List<IntrusionDTO>> getVideosFromCamera(@PathVariable String cameraId) {
        log.info("GET Request -> Get videos from Camera");
        
        return new ResponseEntity<>(service.getVideoKeysFromCamera(cameraId), HttpStatus.OK);
    }

    @GetMapping("/properties")
    public ResponseEntity<List<IntrusionDTO>> getVideosFromProperties(@RequestBody List<Long> propertiesIds){
        log.info("GET Request -> Get videos from Multiple Properties");
        
        return new ResponseEntity<>(service.getVideoKeysFromMultipleProperties(propertiesIds), HttpStatus.OK);
    }

    @GetMapping("/url/{eventId}")
    public ResponseEntity<String> getVideoUrl(@PathVariable long eventId) {
        log.info("GET Request -> Get Video");
        String videoUrl = service.getVideoUrl(eventId);
        if (videoUrl == null){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        log.info("Generated URL: " + videoUrl);
        return new ResponseEntity<>(videoUrl, HttpStatus.OK);

    }
}
