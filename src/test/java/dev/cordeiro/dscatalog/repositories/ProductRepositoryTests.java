package dev.cordeiro.dscatalog.repositories;

import dev.cordeiro.dscatalog.entities.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void deleteShouldDeleteObjectWhenIdExists(){
        long id = 1L;

        productRepository.deleteById(id);

        Optional<Product> productResult = productRepository.findById(id);

        Assertions.assertFalse(productResult.isPresent());
    }

    @Test
    public void deleteShouldThrowExceptionWhenIdNotExists(){
        Assertions.assertThrows(EmptyResultDataAccessException.class, () ->{
            long id = 200L;
                productRepository.deleteById(id);

        });
    }

}
