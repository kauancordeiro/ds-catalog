package dev.cordeiro.dscatalog.entities;

import dev.cordeiro.dscatalog.dtos.CategoryDTO;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "tb_category")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Category (CategoryDTO categoryDTO){
        this.name = categoryDTO.getName();
    }

}
