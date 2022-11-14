package es.module2.imapi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.module2.imapi.model.Intrusion;
import es.module2.imapi.model.Video;
import es.module2.imapi.service.IMAPIService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestPart;

@RestController
@RequestMapping("")
@Validated
//@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
//@CrossOrigin(origins = "http://panel.admin.hgsoft.me:3000/", allowedHeaders = "*")
class Controller {
    private static final Logger log = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private IMAPIService service;

    @PostMapping("/intrusion")
    public ResponseEntity<?> intrusion(@RequestBody Intrusion intrusion) {
        log.info("POST Request -> Send Message to get Camera Videos");
        try {
            service.intrusion(intrusion);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/videoClips", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> sendVideo( @RequestParam String name, @RequestPart MultipartFile document) {
        log.info("POST Request -> Store Camera Videos");
        
        //return new ResponseEntity<>(HttpStatus.OK);
        // log.info("file size" + video.isEmpty());

        String fileName = name; //video.getName();
         
        log.info("FileName: " + fileName);
        log.info("Original FileName: " + document.getOriginalFilename());

         
        try {
            service.uploadFile(fileName, document.getInputStream());//video.getVideo_parts().getInputStream());
            log.info("Upload Successful: " + fileName);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            log.info("Upload Error: " + fileName);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }    
}