package dev.cordeiro.dscatalog.controller;

import dev.cordeiro.dscatalog.dtos.CategoryDTO;
import dev.cordeiro.dscatalog.entities.Category;
import dev.cordeiro.dscatalog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping
    ResponseEntity<List<CategoryDTO>> findaALl(){
        List<CategoryDTO> list = service.findAll();
        if(!list.isEmpty()){
            return ResponseEntity.ok().body(list);
        }else{
            return ResponseEntity.noContent().build();
        }


    }
}
