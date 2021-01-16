package com.avelircraft.services;

import com.avelircraft.models.News;
import com.avelircraft.repositories.news.CustomizedNewsCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NewsDataService {

    @Autowired
    private CustomizedNewsCrudRepository newsCrudRepository;

    public Optional<News> findById(Integer id){
        return newsCrudRepository.findById(id);
    }

    public List<News> findAll(){
        ArrayList<News> news = new ArrayList<>();
        newsCrudRepository.findAll()
                .iterator()
                .forEachRemaining(news::add);
        return news;
    }

    public News save(News var){
        return newsCrudRepository.save(var);
    }

    public Iterable<News> saveAll(List<News> vars){
        return newsCrudRepository.saveAll(vars);
    }

    public void deleteById(Integer id){
        newsCrudRepository.deleteById(id);
    }

    public void delete(News var){
        newsCrudRepository.delete(var);
    }

    public void deleteAll(List<News> vars){
        newsCrudRepository.deleteAll(vars);
    }

    public void deleteAll(){ newsCrudRepository.deleteAll(); }

    public News incrementViews(News var){
        newsCrudRepository.incrementNewsViewsById(var.getId());
        return var.incrementViews();
    }
}
