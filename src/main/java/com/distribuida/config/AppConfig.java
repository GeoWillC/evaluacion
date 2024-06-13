package com.distribuida.config;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class AppConfig{

    @PersistenceContext
    private EntityManagerFactory entityManagerFactory;

    @PostConstruct
    public void inicializado(){
        System.out.println("Inicializacion de la persistencia");
        entityManagerFactory=Persistence.createEntityManagerFactory("distribuida-gc");
    }
    @Produces
    public EntityManager entityManaged() {
        System.out.println("Inicializacion del entity manager");
        return entityManagerFactory.createEntityManager();
    }
}