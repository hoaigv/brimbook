package com.example.bookshop.converter;

import com.example.bookshop.entity.CategoryEntity;
import com.example.bookshop.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Named("CategoriesConverter")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Component
public class CategoriesConverter {
    CategoryRepository categoryRepository;

    @Named("CategoriesCodeToCategories")
    public Set<CategoryEntity> CategoriesCodeToCategories(Set<String> categories) {
        return categories.stream().map(
                categoryRepository::findByCategoryCode
        ).collect(Collectors.toSet());
    }

    @Named("CategoriesToCategoriesCode")
    public Set<String> CategoriesToCategoriesCode(Set<CategoryEntity> categories) {
        return categories.stream().map(CategoryEntity::getCategoryCode).collect(Collectors.toSet());
    }
}
