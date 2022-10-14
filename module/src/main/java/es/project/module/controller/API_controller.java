package es.project.module.controller;


import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class API_controller {

    @Autowired
    private final VideoClipRepository repository;

    @Autowired
    private API_service service;

    API_controller(VideoClipRepository repository) {
        this.repository = repository;
    }



    @GetMapping("/newIntrusion")
    void newIntrusion() {
        service.newIntrusion();
    }
    // end::get-aggregate-root[]

    @PostMapping("/videoClip")
    Employee newEmployee(@RequestBody VideoClip newVideo) {
        return repository.save(newVideo);
    }
}