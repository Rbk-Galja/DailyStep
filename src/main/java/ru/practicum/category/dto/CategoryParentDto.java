package ru.practicum.category.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.children.dto.CategoryChildrenDto;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryParentDto {

    String name;
    List<CategoryChildrenDto> children = new ArrayList<>();
}
