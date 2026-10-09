package de.terminbuchung.terminbuchung_api.controller;

import de.terminbuchung.terminbuchung_api.dto.TreatmentRequest;
import de.terminbuchung.terminbuchung_api.dto.TreatmentResponse;
import de.terminbuchung.terminbuchung_api.service.TreatmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/treatments")
public class TreatmentController {

    private final TreatmentService service;

    public TreatmentController(TreatmentService service) {
        this.service = service;
    }

    @GetMapping
    public List<TreatmentResponse> getAll() {
        return service.findAll().stream()
                .map(TreatmentResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public TreatmentResponse getById(@PathVariable Long id) {
        return TreatmentResponse.from(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TreatmentResponse create(@Valid @RequestBody TreatmentRequest request) {
        return TreatmentResponse.from(service.create(request.toEntity()));
    }

    @PutMapping("/{id}")
    public TreatmentResponse update(@PathVariable Long id,
                                    @Valid @RequestBody TreatmentRequest request) {
        return TreatmentResponse.from(service.update(id, request.toEntity()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}