package dev.cordeiro.dscatalog.services;

import dev.cordeiro.dscatalog.dtos.ProductDTO;
import dev.cordeiro.dscatalog.entities.Product;
import dev.cordeiro.dscatalog.repositories.ProductRepository;
import dev.cordeiro.dscatalog.services.exceptions.DatabaseException;
import dev.cordeiro.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(PageRequest pageRequest){
        Page<Product> list = repository.findAll(pageRequest);
        return list.map(ProductDTO::new);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(long id) {
        Optional<Product> product = repository.findById(id);
        Product entity = product.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new ProductDTO(entity, entity.getCategories());

    }

//    @Transactional
//    public ProductDTO insert(ProductDTO ProductDTO) {
//        Product Product = new Product(ProductDTO);
//        Product = repository.save(Product);
//        return new ProductDTO(Product);
//    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO ProductDTO) {
        try{
            Product Product = repository.getReferenceById(id);
            Product.setName(ProductDTO.getName());
            Product = repository.save(Product);
            return new ProductDTO(Product);
        }catch (EntityNotFoundException e){
            throw  new ResourceNotFoundException("Id not found " + id);
        }

    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Id not found");
        }catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violantion");
        }

    }
}
