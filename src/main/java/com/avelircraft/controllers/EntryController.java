package com.avelircraft.controllers;

import com.avelircraft.models.Role;
import com.avelircraft.models.User;
import com.avelircraft.services.RolesDataService;
import com.avelircraft.services.UsersDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = {"/login", "/login.html"})
public class EntryController {

    @Autowired
    private UsersDataService usersDataService;

    @Autowired
    private RolesDataService rolesDataService;

    @RequestMapping(path = "", method = RequestMethod.GET)
    public String loginPage(Model model) { return "login"; }

    @RequestMapping(path = "", method = RequestMethod.POST)
    public String logMe(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(required = false) boolean remember_me,
                        HttpSession session) {
        Optional<User> user = usersDataService.findByNameAndPassword(username, password);
        if (user.isEmpty())
            return "nolog";
        //List<Role> roles = rolesDataService.findByUsername(user.get().getUsername());
        //List<Role> roles = user.get().getRoles();
        session.setAttribute("user", user.get());
        session.setAttribute("log_date", System.currentTimeMillis()); // По истечении суток обновлять объект user
        //session.setAttribute("role", roles);

        return "redirect:/lk";
    }

    @RequestMapping(path = "/new", method = RequestMethod.POST)
    public String regMe(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session) {
//        if (usersDataService.findByUsername(username.toLowerCase()).isPresent())
//            return "error";
//        User user = new User();
//        user.setRealname(username);
//        user.setUsername(username.toLowerCase());
//        user.setPassword(password);
//        user.setRole(new Role(user));
//        user = usersDataService.save(user).get();
//        session.setAttribute("user", user);
//        session.setAttribute("log_date", System.currentTimeMillis());
        return "redirect:/lk";
    }
}