package com.avelircraft.repositories.role;

import com.avelircraft.models.Role;
import com.avelircraft.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomizedRolesCrudRepository extends CrudRepository<Role, String>, CustomizedRoles<Role> {

//     List<Role> findByUsername(String username);

    List<Role> findByUser(User user);

}
