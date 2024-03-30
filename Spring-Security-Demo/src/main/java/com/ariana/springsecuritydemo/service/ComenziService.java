package com.ariana.springsecuritydemo.service;

import com.ariana.springsecuritydemo.model.Comenzi;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Service
public interface ComenziService {
    public Comenzi saveOrder(Comenzi comenzi);
    public void updateOrder(Integer ID_Comanda, Comenzi updatedOrder);
    public List<Comenzi> getAllOrders();
    public Comenzi getOrder(Integer ID_Comanda);
    public void deleteOrder(Integer ID_Comanda);
    //public Double calculeazaCostTotal(Long userID);
    public Double calculeazaCostTotal();
    List<Comenzi> getOrdersByUserId(Long userId);
    List<Comenzi> getOrdersForAuthenticatedUser();
    List<Comenzi> getOrdersBetweenDates(LocalDateTime startDate, LocalDateTime endDate);
}
