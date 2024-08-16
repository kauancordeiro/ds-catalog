package dev.cordeiro.dscatalog.controller;

import dev.cordeiro.dscatalog.dtos.ProductDTO;
import dev.cordeiro.dscatalog.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Controller
@RequestMapping("/producties")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    ResponseEntity<Page<ProductDTO>> findaALl(Pageable pageable/* page, size, sort */) {


        Page<ProductDTO> list = service.findAllPaged(pageable);

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    ResponseEntity<ProductDTO> findById(@PathVariable long id) {
        ProductDTO ProductDTO = service.findById(id);
        return ResponseEntity.ok().body(ProductDTO);
    }

    @PostMapping
    ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO ProductDTO) {

        ProductDTO = service.insert(ProductDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(ProductDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(ProductDTO);
    }

    @PutMapping("/{id}")
    ResponseEntity<ProductDTO> update(@RequestBody ProductDTO ProductDTO, @PathVariable Long id) {
        ProductDTO = service.update(id, ProductDTO);

        return ResponseEntity.ok().body(ProductDTO);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete (@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
