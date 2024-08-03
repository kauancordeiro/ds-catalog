package dev.cordeiro.dscatalog.repositories;

import dev.cordeiro.dscatalog.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
