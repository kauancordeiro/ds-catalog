package dev.cordeiro.dscatalog.services;

import dev.cordeiro.dscatalog.dtos.CategoryDTO;
import dev.cordeiro.dscatalog.entities.Category;
import dev.cordeiro.dscatalog.repositories.CategoryRepository;
import dev.cordeiro.dscatalog.services.exceptions.DatabaseException;
import dev.cordeiro.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAllPaged(PageRequest pageRequest){
        Page<Category> list = repository.findAll(pageRequest);
        return list.map(CategoryDTO::new);
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(long id) {
        Optional<Category> category = repository.findById(id);
        return category.map(CategoryDTO::new).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO categoryDTO) {
        Category category = new Category(categoryDTO);
        category = repository.save(category);
        return new CategoryDTO(category);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
        try{
            Category category = repository.getReferenceById(id);
            category.setName(categoryDTO.getName());
            category = repository.save(category);
            return new CategoryDTO(category);
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
