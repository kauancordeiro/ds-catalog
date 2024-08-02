package dev.cordeiro.dscatalog.controller;

import dev.cordeiro.dscatalog.dtos.CategoryDTO;
import dev.cordeiro.dscatalog.entities.Category;
import dev.cordeiro.dscatalog.services.CategoryService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping
    ResponseEntity<Page<CategoryDTO>> findaALl(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10")Integer pageSize,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy
    ) {

        PageRequest pageRequest = PageRequest.of(page,pageSize, Sort.Direction.valueOf(direction), orderBy);

        Page<CategoryDTO> list = service.findAllPaged(pageRequest);

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    ResponseEntity<CategoryDTO> findById(@PathVariable long id) {
        CategoryDTO categoryDTO = service.findById(id);
        return ResponseEntity.ok().body(categoryDTO);
    }

    @PostMapping
    ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO categoryDTO) {

        categoryDTO = service.insert(categoryDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(categoryDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(categoryDTO);
    }

    @PutMapping("/{id}")
    ResponseEntity<CategoryDTO> update(@RequestBody CategoryDTO categoryDTO, @PathVariable Long id) {
        categoryDTO = service.update(id, categoryDTO);

        return ResponseEntity.ok().body(categoryDTO);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete (@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
