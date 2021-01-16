package com.avelircraft.services;

import com.avelircraft.models.SupportRequest;
import com.avelircraft.models.User;
import com.avelircraft.repositories.support.CustomizedSupportRequestsCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class SupportRequestsDataService {

    @Autowired
    private CustomizedSupportRequestsCrudRepository supportCrudRepository;

    public Optional<SupportRequest> findById(Long id){
        return supportCrudRepository.findById(id);
    }

    public List<SupportRequest> findAll(){
        List<SupportRequest> requests = (List<SupportRequest>) supportCrudRepository.findAll();
        requests.sort(Collections.reverseOrder());
        return requests;
    }

    public List<SupportRequest> findAllByUserId(Integer userId){
        List<SupportRequest> requests = (List<SupportRequest>) supportCrudRepository.findAll();
        Collections.sort(requests, Collections.reverseOrder());
        return requests;
    }

    public SupportRequest save(SupportRequest var){
        return supportCrudRepository.save(var);
    }
}
