package com.avelircraft.repositories.support;

import com.avelircraft.models.SupportRequest;
import com.avelircraft.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomizedSupportRequestsCrudRepository extends CrudRepository<SupportRequest, Long> {

    List<SupportRequest> findByUserId(Integer userId);
}
