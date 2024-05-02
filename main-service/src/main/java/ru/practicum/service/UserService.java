package ru.practicum.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.user.NewUserRequest;
import ru.practicum.entity.User;
import ru.practicum.dto.user.UserDto;
import ru.practicum.exception.NotFoundException;
import ru.practicum.storage.UserStorage;
import ru.practicum.utility.Mapper;
import ru.practicum.utility.Paginator;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    private final UserStorage storage;

    @Transactional(readOnly = true)
    public List<UserDto> getUsersByParam(List<Long> ids, int from, int size) {
        List<UserDto> foundUsers;
        PageRequest page = Paginator.simplePage(from, size);
        if (ids == null) {
            foundUsers = storage.findAll(page)
                    .stream().map(Mapper::toUserDto).collect(Collectors.toList());
        } else {
            foundUsers = storage.findAllByIdIn(ids, page)
                    .stream().map(Mapper::toUserDto).collect(Collectors.toList());
        }
        log.info("Найден список из {} пользователей", foundUsers.size());
        return foundUsers;
    }

    @Transactional
    public UserDto createUser(NewUserRequest creatingDto) {
        User savedUser = storage.save(Mapper.toUser(creatingDto));
        log.info("Новый пользователь сохранен с id {}", savedUser.getId());
        return Mapper.toUserDto(savedUser);
    }

    @Transactional
    public void deleteUser(long userId) {
        if (!storage.existsById(userId)) {
            throw new NotFoundException(String.format("Пользователь с id %d не существует", userId));
        }
        storage.deleteById(userId);
        log.info("Пользователь с id {} удален", userId);
    }
}
