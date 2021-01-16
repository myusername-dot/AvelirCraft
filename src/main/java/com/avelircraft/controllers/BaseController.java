package com.avelircraft.controllers;

import com.avelircraft.models.MyUserPrincipal;
import com.avelircraft.models.User;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseController {
    protected User getCurrentUser() {
        MyUserPrincipal user = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUser();
    }
}