package dev.cordeiro.dscatalog.dtos;
import dev.cordeiro.dscatalog.entities.Category;
import dev.cordeiro.dscatalog.entities.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
public class ProductDTO{

    @Setter
    private Long id;
    @Setter
    private String name;
    @Setter
    private String description;
    @Setter
    private Double price;
    @Setter
    private String imgUrl;
    @Setter
    private Instant date;
    private List<CategoryDTO> categories = new ArrayList<>();

    public ProductDTO(Long id, String name, String description, Double price, String imgUrl, Instant date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.date = date;
    }

    public ProductDTO(Product product) {
        this.id = product.getId();;
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.imgUrl = product.getImgUrl();
        this.date = product.getDate();
    }

    public ProductDTO(Product product, Set<Category> categorySet){
        this(product);
        categorySet.forEach(cat -> this.categories.add(new CategoryDTO(cat)));
    }
}
