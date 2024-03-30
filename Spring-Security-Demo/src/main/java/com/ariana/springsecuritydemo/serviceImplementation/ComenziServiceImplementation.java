package com.ariana.springsecuritydemo.serviceImplementation;

import com.ariana.springsecuritydemo.model.Comenzi;
import com.ariana.springsecuritydemo.model.Meniu;
import com.ariana.springsecuritydemo.model.User;
import com.ariana.springsecuritydemo.repository.ComenziRepo;
import com.ariana.springsecuritydemo.repository.UserRepository;
import com.ariana.springsecuritydemo.service.ComenziService;
import com.ariana.springsecuritydemo.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ComenziServiceImplementation implements ComenziService {
    private final ComenziRepo comenziRepo;
    private final UserRepository userRepository;
    private final MenuService menuService;

    @Autowired
    public ComenziServiceImplementation(ComenziRepo comenziRepo, UserRepository userRepository, MenuService menuService) {
        this.comenziRepo = comenziRepo;
        this.userRepository = userRepository;
        this.menuService = menuService;
    }

    /*@Override
    public Comenzi saveOrder(Comenzi comenzi) {
        //Comenzi savedComanda = comenziRepo.save(comenzi);
        //menuService.updateStoc(comenzi.getID_Preparat());
        //return savedComanda;
        Meniu preparat = menuService.getMenu(comenzi.getID_Preparat());
        if (preparat != null && preparat.getStoc() > 0) {
            comenziRepo.save(comenzi);
            menuService.updateStoc(comenzi.getID_Preparat());
            return comenzi;
        } else {
            throw new RuntimeException("Preparatul nu mai este disponibil în stoc.");
        }
    }*/
    @Override
    public Comenzi saveOrder(Comenzi comenzi) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        if (user != null) {
            comenzi.setUser(user); // Setează utilizatorul autentificat pentru comandă
            comenzi.setId(user.getId());
            Meniu preparat = menuService.getMenu(comenzi.getID_Preparat());
            if (preparat != null && preparat.getStoc() > 0) {
                comenziRepo.save(comenzi);
                menuService.updateStoc(comenzi.getID_Preparat());
                return comenzi;
            } else {
                throw new RuntimeException("Preparatul nu mai este disponibil în stoc.");
            }
        } else {
            throw new RuntimeException("Utilizatorul autentificat nu poate fi găsit.");
        }
    }

    public void updateOrder(Integer ID_Comanda, Comenzi updatedOrder) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        if (user != null) {
            Optional<Comenzi> existingOrderOptional = comenziRepo.findById(ID_Comanda);
            if (existingOrderOptional.isPresent()) {
                Comenzi existingOrder = existingOrderOptional.get();
                if (existingOrder.getUser().getId().equals(user.getId())) {
                    // Actualizăm detaliile comenzii cu cele primite în parametrul updatedOrder
                    existingOrder.setID_Preparat(updatedOrder.getID_Preparat());
                    existingOrder.setCost_Total(updatedOrder.getCost_Total());
                    existingOrder.setStatus_Comanda(updatedOrder.getStatus_Comanda());
                    existingOrder.setData_Ora_Comanda(updatedOrder.getData_Ora_Comanda());

                    comenziRepo.save(existingOrder);
                } else {
                    throw new RuntimeException("Nu aveți permisiunea de a actualiza această comandă.");
                }
            } else {
                throw new RuntimeException("Comanda nu a fost găsită.");
            }
        } else {
            throw new RuntimeException("Utilizatorul autentificat nu poate fi găsit.");
        }
    }

    @Override
    public List<Comenzi> getAllOrders() {
        return (List<Comenzi>) comenziRepo.findAll();
    }

    public Comenzi getOrder(Integer ID_Comanda){
        return comenziRepo.findById(ID_Comanda).get();
    }

    /*public void deleteOrder(Integer ID_Comanda){
        comenziRepo.deleteById(ID_Comanda);
    }*/

    public void deleteOrder(Integer ID_Comanda) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        if (user != null) {
            Optional<Comenzi> existingOrder = comenziRepo.findById(ID_Comanda);
            existingOrder.ifPresent(comanda -> {
                if (comanda.getUser().getId().equals(user.getId())) {
                    comenziRepo.deleteById(ID_Comanda);
                } else {
                    throw new RuntimeException("Nu aveți permisiunea de a șterge această comandă.");
                }
            });
        } else {
            throw new RuntimeException("Utilizatorul autentificat nu poate fi găsit.");
        }
    }

    @PersistenceContext
    private EntityManager entityManager;
    /*public Double calculeazaCostTotal(Long userId) {
        Double costTotal = 0.00;

        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            List<Comenzi> comenzi = entityManager.createQuery(
                            "SELECT c FROM Comenzi c WHERE c.user.id = :userId", Comenzi.class)
                    .setParameter("userId", userId)
                    .getResultList();

            for (Comenzi comanda : comenzi) {
                costTotal += comanda.getCost_Total();
            }
        }
        return costTotal;
    }*/
    public Double calculeazaCostTotal() {
        Double costTotal = 0.00;
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username);
        if (user != null) {
            List<Comenzi> comenzi = entityManager.createQuery(
                            "SELECT c FROM Comenzi c WHERE c.user.id = :userId", Comenzi.class)
                    .setParameter("userId", user.getId())
                    .getResultList();

            for (Comenzi comanda : comenzi) {
                costTotal += comanda.getCost_Total();
            }
        }
        return costTotal;
    }

    @Override
    public List<Comenzi> getOrdersByUserId(Long userId) {
        return entityManager.createQuery(
                        "SELECT c FROM Comenzi c WHERE c.user.id = :userId", Comenzi.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<Comenzi> getOrdersForAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return entityManager.createQuery(
                        "SELECT c FROM Comenzi c WHERE c.user.username = :username", Comenzi.class)
                .setParameter("username", username)
                .getResultList();
    }

    @Override
    public List<Comenzi> getOrdersBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return entityManager.createQuery(
                        "SELECT c FROM Comenzi c WHERE c.Data_Ora_Comanda BETWEEN :startDate AND :endDate", Comenzi.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

}