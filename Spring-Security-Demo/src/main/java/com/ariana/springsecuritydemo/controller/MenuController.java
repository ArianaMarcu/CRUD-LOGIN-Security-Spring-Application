package com.ariana.springsecuritydemo.controller;

import com.ariana.springsecuritydemo.service.MenuService;
import com.ariana.springsecuritydemo.model.Meniu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/admin")
@EnableWebSecurity
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/getAllMenus")
    public List<Meniu> list(){
        return menuService.getAllMenus();
    }

    @PostMapping("/addMenu")
    public String add(@RequestBody Meniu meniu){
        menuService.addMenu(meniu);
        return "New menu is added";
    }

    @GetMapping("/menu{id}")
    public ResponseEntity<Meniu> get(@PathVariable Integer id) {
        try {
            Meniu meniu = menuService.getMenu(id);
            return new ResponseEntity<Meniu>(meniu, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            System.out.println("Menu not found");
            return new ResponseEntity<Meniu>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/menu{id}")
    public ResponseEntity<Meniu> update(@RequestBody Meniu meniu,@PathVariable Integer id){
        try{
            Meniu existingMenu = menuService.getMenu(id);
            menuService.addMenu(meniu);
            return new ResponseEntity<>(HttpStatus.OK);

        }catch (NoSuchElementException e){
            return new ResponseEntity<Meniu>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/menu{id}")
    public String delete(@PathVariable Integer id){
        menuService.deleteMenu(id);
        return "Deleted Menu with id "+id;
    }
}
