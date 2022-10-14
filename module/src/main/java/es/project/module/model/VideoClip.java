package es.project.module.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
class VideoClip {

  private @Id @GeneratedValue Long id;

  VideoClip() {}


  
}