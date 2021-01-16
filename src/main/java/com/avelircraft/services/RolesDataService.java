package com.avelircraft.services;

import com.avelircraft.models.Role;
import com.avelircraft.models.User;
import com.avelircraft.repositories.role.CustomizedRolesCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RolesDataService {

    @Autowired
    private CustomizedRolesCrudRepository rolesCrudRepository;

    public Optional<Role> findById(String id){
        return rolesCrudRepository.findById(id);
    }

//    public List<Role> findByUsername(String username){ return rolesCrudRepository.findByUsername(username); }

    public List<Role> findByUser(User user){ return rolesCrudRepository.findByUser(user); }
}
