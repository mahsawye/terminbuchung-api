package de.terminbuchung.terminbuchung_api.repository;

import de.terminbuchung.terminbuchung_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
}