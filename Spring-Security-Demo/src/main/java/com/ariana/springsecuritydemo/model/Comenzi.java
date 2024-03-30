package com.ariana.springsecuritydemo.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comenzi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Comanda")
    private int ID_Comanda;
    @Column(name = "ID_Preparat")
    private int ID_Preparat;
    @Column(name = "id")
    private Long id;

    public int getID_Preparat() {
        return ID_Preparat;
    }

    public void setID_Preparat(int ID_Preparat) {
        this.ID_Preparat = ID_Preparat;
    }

    private Double Cost_Total;
    private String Status_Comanda;
    private LocalDateTime Data_Ora_Comanda;

    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "id",  insertable = false, updatable = false)
    private User user;

    public Comenzi() {
    }

    public int getID_Comanda() {
        return ID_Comanda;
    }

    public void setID_Comanda(int ID_Comanda) {
        this.ID_Comanda = ID_Comanda;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCost_Total() {
        return Cost_Total;
    }

    public void setCost_Total(Double cost_Total) {
        Cost_Total = cost_Total;
    }

    public String getStatus_Comanda() {
        return Status_Comanda;
    }

    public void setStatus_Comanda(String status_Comanda) {
        Status_Comanda = status_Comanda;
    }

    public LocalDateTime getData_Ora_Comanda() {
        return Data_Ora_Comanda;
    }

    public void setData_Ora_Comanda(LocalDateTime data_Ora_Comanda) {
        this.Data_Ora_Comanda = data_Ora_Comanda;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
