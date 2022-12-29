package ru.jsft.voteforlunch.service;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jsft.voteforlunch.model.User;
import ru.jsft.voteforlunch.repository.UserRepository;

import java.util.List;

import static ru.jsft.voteforlunch.validation.ValidationUtils.checkEntityNotNull;
import static ru.jsft.voteforlunch.validation.ValidationUtils.checkNew;

@Service
@Slf4j
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User findById(long id) {
        log.info("Find user with id = {}", id);
        return checkEntityNotNull(repository.findById(id), id, User.class);
    }

    public List<User> findAllSorted() {
        log.info("Find all users");
        return repository.findAll(Sort.by("email"));
    }

    public User create(@NotNull User user) {
        log.info("Create user: {}", user);
        checkNew(user);
        return repository.save(user);
    }

    public void delete(long id) {
        log.info("Delete user with id = {}", id);
        repository.deleteById(id);
    }

    @Transactional
    public User update(long id, User user) {
        log.info("Update user with id = {}", user.getId());
        User storedUser = checkEntityNotNull(repository.findById(id), id, User.class);
        user.setId(id);
        user.setPassword(storedUser.getPassword()); // do not update the password, it must be updated in a separate way
        user.setRoles(storedUser.getRoles()); // do not update roles, it must be updated in a separate way
        return repository.save(user);
    }
}
