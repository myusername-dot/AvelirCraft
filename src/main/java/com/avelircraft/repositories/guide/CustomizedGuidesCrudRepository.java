package com.avelircraft.repositories.guide;

import com.avelircraft.models.Guide;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomizedGuidesCrudRepository extends CrudRepository<Guide, Integer>, CustomizedGuide<Guide> {

    @Modifying
    @Query("update Guide g set g.views = g.views + 1 where g.id = ?1")
    void incrementGuideViewsById(Integer id);

    List<Guide> findAllByOrderByDateDesc();
}
