package com.workintech.s18d1.dao;

import com.workintech.s18d1.entity.BreadType;
import com.workintech.s18d1.entity.Burger;
import com.workintech.s18d1.exceptions.BurgerException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BurgerDaoImpl implements BurgerDao{

    private final EntityManager entityManager;

    @Autowired
    public BurgerDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public Burger save(Burger burger) {
        entityManager.persist(burger);
        return burger;
    }

    @Override
    public List<Burger> findAll() {
        TypedQuery<Burger> findAll = entityManager.createQuery("SELECT b FROM Burger b",Burger.class);
        return findAll.getResultList();
    }

    @Override
    public Burger findById(long id) {
       Burger burger= entityManager.find(Burger.class,id);
       if(burger == null){
           throw new BurgerException("Burger is not exist"+id, HttpStatus.BAD_REQUEST);
       }
       return burger;
    }

    @Transactional
    @Override
    public Burger update(Burger burger) {
      return entityManager.merge(burger);
    }

    @Transactional
    @Override
    public Burger remove(long id) {
        Burger burger = findById(id);
        entityManager.remove(burger);
        return burger;
    }

    @Override
    public List<Burger> findByPrice(double price) {
    TypedQuery<Burger> findPriceBurger=entityManager.createQuery("SELECT b FROM Burger b WHERE b.price> :price ORDER BY b.price desc", Burger.class);
    findPriceBurger.setParameter("price", price);
    return findPriceBurger.getResultList();
    }

    @Override
    public List<Burger> findByBreadType(BreadType breadType) {
        TypedQuery<Burger> findBreadType = entityManager.createQuery("SELECT b FROM Burger b WHERE b.breadType = :breadType ORDER  BY b.name desc", Burger.class);
        findBreadType.setParameter("breadType", breadType);
        return findBreadType.getResultList();
    }

    @Override
    public List<Burger> findByContent(String contents) {
        TypedQuery<Burger> findByContent = entityManager.createQuery("SELECT b FROM Burger b WHERE b.contents LIKE CONCAT  = ('%', : contents, '%' Order BY b.name)", Burger.class);
        findByContent.setParameter("contents",contents);

        return findByContent.getResultList();
    }
}
