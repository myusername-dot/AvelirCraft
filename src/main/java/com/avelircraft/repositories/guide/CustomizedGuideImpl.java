package com.avelircraft.repositories.guide;

import com.avelircraft.models.Guide;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CustomizedGuideImpl implements CustomizedGuide<Guide> {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public List<Guide> findGuidesLikeTags(String[] tags){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Guide> cq = cb.createQuery(Guide.class);

        Root<Guide> guide = cq.from(Guide.class);
        List<Predicate> predicates = new ArrayList<>();

        for (String tag : tags){
            predicates.add(cb.like(guide.get("tags"), "% " + tag + " %"));
        }
        Predicate finalPredicate = cb.and(predicates.toArray(new Predicate[0]));
        cq.where(finalPredicate).orderBy(cb.desc(guide.get("date"))); // ????????? cb.asc
        return em.createQuery(cq).getResultList();
    }
}
