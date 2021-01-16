package com.avelircraft.repositories.user;

import com.avelircraft.models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomizedUsersCrudRepository extends CrudRepository<User, Integer> {

    Optional<User> findByRealname(String realname);

    Optional<User> findByUsername(String username);

    @Modifying
    @Query("update User u set u.profileicon = true where u.id = ?1")
    void addIcon(Integer id);
}
