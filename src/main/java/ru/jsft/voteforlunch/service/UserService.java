package ru.jsft.voteforlunch.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jsft.voteforlunch.error.NotFoundException;
import ru.jsft.voteforlunch.model.User;
import ru.jsft.voteforlunch.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User findById(long id) {
        log.info("Find user with id = {}", id);
        return repository.findById(id)
                .orElseThrow(() -> (new NotFoundException(String.format("User with id = %d not found", id))));
    }

    public List<User> findAllSorted() {
        log.info("Find all users");
        return repository.findAll(Sort.by("email"));
    }

    public User create(User user) {
        if (!user.isNew()) {
            throw new IllegalArgumentException("User must be new");
        }

        log.info("Create user: {}", user);
        return repository.save(user);
    }

    public void delete(long id) {
        log.info("Delete user with id = {}", id);
        repository.deleteById(id);
    }

    @Transactional
    public User update(long id, User user) {
        Optional<User> userOptional = repository.findById(id);

        if (userOptional.isEmpty()) {
            throw new NotFoundException(String.format("User with id = %d not found", id));
        }

        log.info("Update user with id = {}", user.getId());
        user.setId(id);
        return repository.save(user);
    }
}
