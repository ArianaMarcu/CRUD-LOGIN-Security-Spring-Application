package com.ariana.springsecuritydemo.service;

import com.ariana.springsecuritydemo.model.Meniu;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface MenuService {
    public Meniu addMenu(Meniu meniu);
    public List<Meniu> getAllMenus();

    public Meniu getMenu(Integer id);

    public void deleteMenu(Integer id);
    public void updateStoc(Integer id);
}