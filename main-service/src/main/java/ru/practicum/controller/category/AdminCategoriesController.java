package ru.practicum.controller.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.category.CategoryDto;
import ru.practicum.dto.category.NewCategoryDto;
import ru.practicum.service.CategoryService;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/admin/categories")
public class AdminCategoriesController {
    private final CategoryService service;

    /**
     * Добавление новой категории
     * Имя категории должно быть уникальным
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@RequestBody @Valid NewCategoryDto creatingDto) {
        log.info("Создание новой категории: {}", creatingDto);
        return service.createCategory(creatingDto);
    }

    /**
     * Удаление категории
     *  С категорией не должно быть связано ни одного события
     */
    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable long catId) {
        log.info("Удаление категории с id {}", catId);
        service.deleteCategory(catId);
    }

    /**
     * Изменение категории
     * Имя категории должно быть уникальным
     */
    @PatchMapping("/{catId}")
    public CategoryDto updateCategory(@PathVariable long catId,
                               @RequestBody @Valid NewCategoryDto updatingDto) {
        log.info("Обновление категории с id {}", catId);
        return service.updateCategory(catId, updatingDto);
    }
}
