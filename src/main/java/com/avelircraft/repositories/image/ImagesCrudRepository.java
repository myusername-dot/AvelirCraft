package com.avelircraft.repositories.image;

import com.avelircraft.models.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImagesCrudRepository extends CrudRepository<Image, Integer> {

}
