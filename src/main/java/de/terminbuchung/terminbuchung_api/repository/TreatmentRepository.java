package de.terminbuchung.terminbuchung_api.repository;

import de.terminbuchung.terminbuchung_api.model.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
}