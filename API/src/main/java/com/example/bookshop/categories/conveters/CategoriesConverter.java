package com.example.bookshop.categories.conveters;

import com.example.bookshop.categories.models.CategoryEntity;
import com.example.bookshop.categories.repositories.CategoryRepository;
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

}
