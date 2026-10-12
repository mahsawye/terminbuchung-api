package de.terminbuchung.terminbuchung_api.dto;

import de.terminbuchung.terminbuchung_api.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(

        @NotBlank(message = "Name must not be blank")
        @Size(max = 100, message = "Name must be at most 100 characters")
        String name,

        @NotBlank(message = "Email must not be blank")
        @Email(message = "Email format is invalid")
        String email,

        @Size(max = 30, message = "Phone must be at most 30 characters")
        String phone
) {
    public User toEntity() {
        return new User(name, email, phone);
    }
}