package com.ariana.springsecuritydemo.serviceImplementation;


import com.ariana.springsecuritydemo.model.Meniu;
import com.ariana.springsecuritydemo.repository.MenuRepository;
import com.ariana.springsecuritydemo.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImplementation implements MenuService {

    private final MenuRepository menuRepository;

    @Override
    public Meniu addMenu(Meniu meniu) {
        return menuRepository.save(meniu);
    }

    @Autowired
    public MenuServiceImplementation(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }
    @Override
    public List<Meniu> getAllMenus() {
        return (List<Meniu>) menuRepository.findAll();
    }

    public Meniu getMenu(Integer id){
        return menuRepository.findById(id).get();
    }

    public void deleteMenu(Integer id){
        menuRepository.deleteById(id);
    }

    public void updateStoc(Integer id) {
        Meniu preparat = menuRepository.findById(id).orElse(null);
        if (preparat != null) {
            int stocActualizat = preparat.getStoc() - 1;
            preparat.setStoc(stocActualizat);
            menuRepository.save(preparat);
        }
    }

}
