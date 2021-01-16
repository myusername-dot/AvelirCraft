package com.avelircraft.repositories.news;

import com.avelircraft.models.News;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class CustomizedNewsImpl implements CustomizedNews {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public List findAll() {
        return em.createQuery("from News order by date", News.class).getResultList();
    }

}
