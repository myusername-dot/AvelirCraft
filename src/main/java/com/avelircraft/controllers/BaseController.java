package com.avelircraft.controllers;

import com.avelircraft.models.MyUserPrincipal;
import com.avelircraft.models.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseController {

    protected User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) return null;
        MyUserPrincipal user = (MyUserPrincipal) authentication.getPrincipal();
        return user.getUser();
    }
}