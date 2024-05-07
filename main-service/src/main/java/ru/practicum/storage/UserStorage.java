package ru.practicum.storage;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage extends JpaRepository<User, Long> {
    List<User> findAllByIdIn(List<Long> ids, PageRequest page);

    Optional<User> findById(long userId);
}
