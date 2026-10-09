package de.terminbuchung.terminbuchung_api.service;

import de.terminbuchung.terminbuchung_api.exception.ResourceNotFoundException;
import de.terminbuchung.terminbuchung_api.model.Treatment;
import de.terminbuchung.terminbuchung_api.repository.TreatmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TreatmentService {

    private final TreatmentRepository repository;

    public TreatmentService(TreatmentRepository repository) {
        this.repository = repository;
    }

    public List<Treatment> findAll() {
        return repository.findAll();
    }

    public Treatment findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Treatment", id));
    }

    @Transactional
    public Treatment create(Treatment treatment) {
        return repository.save(treatment);
    }

    @Transactional
    public Treatment update(Long id, Treatment updated) {
        Treatment existing = findById(id);
        existing.setName(updated.getName());
        existing.setDurationMinutes(updated.getDurationMinutes());
        existing.setPrice(updated.getPrice());
        return repository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Treatment existing = findById(id);
        repository.delete(existing);
    }
}