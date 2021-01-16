package com.avelircraft.services;

import com.avelircraft.models.Image;
import com.avelircraft.repositories.image.ImagesCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ImagesDataService {

    @Autowired
    private ImagesCrudRepository imagesCrudRepository;

    public Optional<Image> findByUserId(Integer id){
        return imagesCrudRepository.findById(id);
    }

    public Image save(Image var){
        return imagesCrudRepository.save(var);
    }

    public void deleteByUserId(Integer id){
        imagesCrudRepository.deleteById(id);
    }

    public void deleteAll(){
        imagesCrudRepository.deleteAll();
    }
}
