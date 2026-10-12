package de.terminbuchung.terminbuchung_api.service;

import de.terminbuchung.terminbuchung_api.exception.DuplicateResourceException;
import de.terminbuchung.terminbuchung_api.exception.ResourceNotFoundException;
import de.terminbuchung.terminbuchung_api.model.User;
import de.terminbuchung.terminbuchung_api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
    }

    @Transactional
    public User create(User user) {
        if (repository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("Email already registered: " + user.getEmail());
        }
        return repository.save(user);
    }

    @Transactional
    public User update(Long id, User updated) {
        User existing = findById(id);

        boolean emailChanged = !existing.getEmail().equals(updated.getEmail());
        if (emailChanged && repository.existsByEmail(updated.getEmail())) {
            throw new DuplicateResourceException("Email already registered: " + updated.getEmail());
        }

        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setPhone(updated.getPhone());
        return repository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        User existing = findById(id);
        repository.delete(existing);
    }
}