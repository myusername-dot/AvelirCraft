package com.avelircraft.repositories.role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CustomizedRolesImpl implements CustomizedRoles{

    @PersistenceContext
    private EntityManager em;

//    @Override
//    @Transactional
//    public List findByUsername(String username) {
//        return em.createQuery("from Role where username:=username", Role.class).getResultList();
//    }
}
