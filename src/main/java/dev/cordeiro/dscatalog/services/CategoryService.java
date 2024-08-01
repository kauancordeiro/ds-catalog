package dev.cordeiro.dscatalog.services;

import dev.cordeiro.dscatalog.dtos.CategoryDTO;
import dev.cordeiro.dscatalog.entities.Category;
import dev.cordeiro.dscatalog.repositories.CategoryRepository;
import dev.cordeiro.dscatalog.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll(){
        List<Category> list = repository.findAll();
        return list.stream().map(CategoryDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(long id) {
        Optional<Category> category = repository.findById(id);
        return category.map(CategoryDTO::new).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
    }
}
