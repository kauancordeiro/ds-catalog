package dev.cordeiro.dscatalog.dtos;

import dev.cordeiro.dscatalog.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private Long id;
    private String name;

    public CategoryDTO (Category category){
        this.id = category.getId();
        this.name = category.getName();
    }
}
