package de.terminbuchung.terminbuchung_api.dto;

import de.terminbuchung.terminbuchung_api.model.User;

public record UserResponse(
        Long id,
        String name,
        String email,
        String phone
) {
    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getPhone());
    }
}