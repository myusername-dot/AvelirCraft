package com.avelircraft.repositories.news;

import com.avelircraft.models.News;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomizedNewsCrudRepository extends CrudRepository<News, Integer>, CustomizedNews<News>  {

    @Modifying
    @Query("update News n set n.views = n.views + 1 where n.id = ?1")
    void incrementNewsViewsById(Integer id);

}