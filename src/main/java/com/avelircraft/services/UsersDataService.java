package com.avelircraft.services;

import com.avelircraft.models.User;
import com.avelircraft.repositories.user.CustomizedUsersCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UsersDataService {

    @Autowired
    private CustomizedUsersCrudRepository usersCrudRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Optional<User> findById(Integer id){
        return usersCrudRepository.findById(id);
    }

    public Optional<User> findByUsername(String name){
        return usersCrudRepository.findByUsername(name);
    }

    public Optional<User> findByName(String name){
        return usersCrudRepository.findByRealname(name);
    }

    public Optional<User> findByNameAndPassword(String name, String password){
        Optional<User> user = usersCrudRepository.findByRealname(name);
        if (user.isEmpty())
            return user;
        if (!bCryptPasswordEncoder.matches(password, user.get().getPassword())){
            Optional<User> notFound = Optional.ofNullable(null);
            return notFound;
        }
        return user;
    }

    public Iterable<User> findAll(){
        return usersCrudRepository.findAll();
    }

    public void addIcon(User user){
        usersCrudRepository.addIcon(user.getId());
        user.setProfileicon(true);
    }

    public User update(User user){
        if (user.getId() != 0)
            return usersCrudRepository.save(user);
        else throw new IllegalArgumentException("id field must not be null");
    }

    public Optional<User> save(User user){
        if (user.getId() == 0) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            return Optional.of(usersCrudRepository.save(user));
        }
        return Optional.empty();
    }

    public void delete(User user){
        usersCrudRepository.delete(user);
    }
}
