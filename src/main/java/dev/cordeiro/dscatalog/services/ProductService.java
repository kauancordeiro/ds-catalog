package dev.cordeiro.dscatalog.services;

import dev.cordeiro.dscatalog.dtos.CategoryDTO;
import dev.cordeiro.dscatalog.dtos.ProductDTO;
import dev.cordeiro.dscatalog.entities.Category;
import dev.cordeiro.dscatalog.entities.Product;
import dev.cordeiro.dscatalog.repositories.CategoryRepository;
import dev.cordeiro.dscatalog.repositories.ProductRepository;
import dev.cordeiro.dscatalog.services.exceptions.DatabaseException;
import dev.cordeiro.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(Pageable pageable) {
        Page<Product> list = repository.findAll(pageable);
        return list.map(ProductDTO::new);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(long id) {
        Optional<Product> product = repository.findById(id);
        Product entity = product.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new ProductDTO(entity, entity.getCategories());

    }

    @Transactional
    public ProductDTO insert(ProductDTO productDTO) {
        Product productEntity = new Product();
        copyDtoToEntity(productDTO, productEntity);
        productEntity = repository.save(productEntity);
        return new ProductDTO(productEntity);
    }


    @Transactional
    public ProductDTO update(Long id, ProductDTO productDTO) {
        try {
            Product productEntity = repository.getReferenceById(id);
            copyDtoToEntity(productDTO, productEntity);
            productEntity = repository.save(productEntity);
            return new ProductDTO(productEntity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }

    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violantion");
        }

    }

    private void copyDtoToEntity(ProductDTO productDTO, Product productEntity) {

        productEntity.setName(productDTO.getName());
        productEntity.setDate(productDTO.getDate());
        productEntity.setDescription(productDTO.getDescription());
        productEntity.setImgUrl(productDTO.getImgUrl());
        productEntity.setPrice(productDTO.getPrice());

        productEntity.getCategories().clear();
        for(CategoryDTO catDto: productDTO.getCategories()){
            Category categoryEntity = categoryRepository.getReferenceById(catDto.getId());
            productEntity.getCategories().add(categoryEntity);
        }
    }

}
