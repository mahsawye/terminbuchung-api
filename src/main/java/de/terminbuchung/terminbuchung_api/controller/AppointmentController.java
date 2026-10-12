package de.terminbuchung.terminbuchung_api.controller;

import de.terminbuchung.terminbuchung_api.dto.AppointmentRequest;
import de.terminbuchung.terminbuchung_api.dto.AppointmentResponse;
import de.terminbuchung.terminbuchung_api.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @GetMapping
    public List<AppointmentResponse> getAll() {
        return service.findAll().stream()
                .map(AppointmentResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public AppointmentResponse getById(@PathVariable Long id) {
        return AppointmentResponse.from(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentResponse book(@Valid @RequestBody AppointmentRequest request) {
        return AppointmentResponse.from(
                service.book(request.userId(), request.treatmentId(), request.startTime()));
    }

    @PatchMapping("/{id}/cancel")
    public AppointmentResponse cancel(@PathVariable Long id) {
        return AppointmentResponse.from(service.cancel(id));
    }
}