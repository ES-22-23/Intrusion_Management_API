package es.module2.imapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.module2.imapi.model.Intrusion;
public interface IntrusionRepository extends JpaRepository<Intrusion, Long> {
    
    List<Intrusion> findByPropertyId(long propertyId);
    List<Intrusion> findByCameraId(String cameraId);
}