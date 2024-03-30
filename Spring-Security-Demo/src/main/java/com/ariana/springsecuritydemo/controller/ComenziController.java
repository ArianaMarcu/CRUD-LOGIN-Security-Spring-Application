package com.ariana.springsecuritydemo.controller;

import com.ariana.springsecuritydemo.model.User;
import com.ariana.springsecuritydemo.repository.UserRepository;
import com.ariana.springsecuritydemo.service.ComenziService;
import com.ariana.springsecuritydemo.model.Comenzi;
import com.ariana.springsecuritydemo.userDetails.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/home")
public class ComenziController {

    private final ComenziService comenziService;

    @Autowired
    public ComenziController(ComenziService comenziService) {
        this.comenziService = comenziService;
    }

    /*@GetMapping("/getAllOrders")
    public List<Comenzi> list(){
        return comenziService.getAllOrders();
    }*/

    @PostMapping("/addOrder")
    public String add(@RequestBody Comenzi comenzi){
        comenziService.saveOrder(comenzi);
        return "New order is added";
    }

    @PutMapping("/order/{ID_Comanda}")
    public ResponseEntity<String> updateOrder(@PathVariable Integer ID_Comanda, @RequestBody Comenzi updatedOrder) {
        try {
            comenziService.updateOrder(ID_Comanda, updatedOrder);
            return new ResponseEntity<>("Comanda actualizată cu succes.", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/order{ID_Comanda}")
    public ResponseEntity<Comenzi> get(@PathVariable Integer ID_Comanda) {
        try {
            Comenzi comenzi = comenziService.getOrder(ID_Comanda);
            return new ResponseEntity<Comenzi>(comenzi, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            System.out.println("Order not found");
            return new ResponseEntity<Comenzi>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{userId}/orders")
    public List<Comenzi> getOrdersByUserId(@PathVariable Long userId) {
        return comenziService.getOrdersByUserId(userId);
    }

    /*@PutMapping("/order{ID_Comanda}")
    public ResponseEntity<Comenzi> update(@RequestBody Comenzi comenzi,@PathVariable Integer ID_Comanda){
        try{
            Comenzi existingOrder = comenziService.getOrder(ID_Comanda);
            comenziService.saveOrder(comenzi);
            return new ResponseEntity<>(HttpStatus.OK);

        }catch (NoSuchElementException e){
            return new ResponseEntity<Comenzi>(HttpStatus.NOT_FOUND);
        }
    }*/

    @DeleteMapping("/order{ID_Comanda}")
    public String delete(@PathVariable Integer ID_Comanda) {
        comenziService.deleteOrder(ID_Comanda);
        return "Deleted Order with id " + ID_Comanda;
    }

    /*@GetMapping("/{userId}/costTotal")
    public Double calculeazaCostTotalUtilizator(@PathVariable Long userId) {
        return comenziService.calculeazaCostTotal(userId);
    }*/
    @GetMapping("/costTotal")
    public Double calculeazaCostTotalUtilizator() {
        return comenziService.calculeazaCostTotal();
    }

    @GetMapping("/getAllOrderss")
    public List<Comenzi> list2(Principal principal) {
        return comenziService.getOrdersForAuthenticatedUser();
    }

}