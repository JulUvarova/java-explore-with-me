package ru.practicum.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.category.CategoryDto;
import ru.practicum.dto.category.NewCategoryDto;
import ru.practicum.entity.Category;
import ru.practicum.exception.NotFoundException;
import ru.practicum.storage.CategoryStorage;
import ru.practicum.utility.Mapper;
import ru.practicum.utility.Paginator;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class CategoryService {
    private final CategoryStorage storage;

    @Transactional
    public CategoryDto createCategory(NewCategoryDto creatingDto) {
        Category savedCategory = storage.save(Mapper.toCategory(creatingDto));
        log.info("Новая категория сохранена с id {}", savedCategory.getId());
        return Mapper.toCatDto(savedCategory);
    }

    @Transactional
    public void deleteCategory(long catId) {
        checkCategory(catId);
        storage.deleteById(catId);
        log.info("Категория с id {} удалена", catId);
    }

    @Transactional
    public CategoryDto updateCategory(long catId, NewCategoryDto updatingDto) {
        Category oldCat = checkCategory(catId);
        oldCat.setName(updatingDto.getName());
        Category updatedCat = storage.save(oldCat);
        log.info("Категория с id {} обновлена", catId);
        return Mapper.toCatDto(updatedCat);
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> getCategoriesByParam(int from, int size) {
        List<Category> foundCategories = storage.findAll(Paginator.simplePage(from, size)).toList();
        log.info("Найден список из {} категорий", foundCategories.size());
        return foundCategories.stream().map(Mapper::toCatDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDto getCategoriesById(long catId) {
        CategoryDto foundCategory = Mapper.toCatDto(checkCategory(catId));
        log.info("Найдена категория с id {}", catId);
        return foundCategory;
    }

    private Category checkCategory(long catId) {
        return storage.findById(catId).orElseThrow(() ->
                new NotFoundException(String.format("Категория с id %d не существует", catId)));
    }
}
