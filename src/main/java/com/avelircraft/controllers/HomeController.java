package com.avelircraft.controllers;

import com.avelircraft.models.*;
import com.avelircraft.models.stats.MMOCore;
import com.avelircraft.models.stats.PlayTime;
import com.avelircraft.models.stats.UUID;
import com.avelircraft.services.GuidesDataService;
import com.avelircraft.services.NewsDataService;
import com.avelircraft.services.SupportRequestsDataService;
import com.avelircraft.services.UsersDataService;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping(path = "/")
public class HomeController extends BaseController {

    @Autowired
    private UsersDataService usersDataService;

    @Autowired
    private NewsDataService newsDataService;

    @Autowired
    private GuidesDataService guidesDataService;

    @Autowired
    private SupportRequestsDataService supportDataService;

//    @Autowired
//    private PlatformTransactionManager transactionManager;

    @RequestMapping(path = {"/", "/index.html", "/index"})
    public String indexPage(Model model) {
        List<News> news = Stream.concat(newsDataService.findAll()
                .stream(), Stream.generate(News::new))
                .limit(10)
                .collect(Collectors.toList());
        model.addAttribute("news", news);
        return "index";
    }

    @RequestMapping(path = {"/news.html", "/news"})
    public String newsPage(@RequestParam Integer id, Model model) {
        boolean deleteAccess = false;
        User user = null;
        Optional<News> news = newsDataService.findById(id);
        if (news.isEmpty())
            news = Optional.of(new News());
        else {
            newsDataService.incrementViews(news.get());
            user = getCurrentUser().orElse(null);
            deleteAccess = user != null && user.getRoles().stream()
                    .anyMatch(role -> role.getRole()
                            .matches("owner|fakeowner|admin|moder"));
        }
        model.addAttribute("user", user);
        model.addAttribute("news", news.get());
        model.addAttribute("comments", news.get().getComments());
        model.addAttribute("delete_access", deleteAccess);
        return "news";
    }

    @RequestMapping(path = {"/lk.html", "/lk"})
    public String lkPage(Model model) {
        User user = getCurrentUser().get();
        List<Role> roles = user.getRoles();
        boolean panelAccess = roles.stream()
                .anyMatch(role -> role.getRole()
                        .matches("owner|fakeowner|admin|moder"));
        user = usersDataService.update(user);
        PlayTime playTime = null;
        for (UUID uuid : user.getUuid()) {
            playTime = uuid.getPlayTime();
            if (playTime != null) break;
        }
        MMOCore mmoCore = null;
        for (Role role : roles) {
            mmoCore = role.getMmoCore();
            if (mmoCore != null) break;
        }
        model.addAttribute("panel_access", panelAccess);
        model.addAttribute("play_time", playTime);
        model.addAttribute("lvl", mmoCore == null ? null : mmoCore.getLvl());
        model.addAttribute("class", mmoCore == null ? null : mmoCore.getClas());
        model.addAttribute("user", user);
        return "lk";
    }

    @RequestMapping(path = {"/adminpanel.html", "/adminpanel"})
    public String adminPage(Model model) {
        User user = getCurrentUser().get();
        boolean pageAccess = user.getRoles().stream()
                .anyMatch(role -> role.getRole()
                        .matches("owner|fakeowner|admin|moder"));
        if (pageAccess)
            return "adminpanel";
        else
            return "error";
    }

    @RequestMapping(path = {"/donate.html", "/donate"})
    public String donatePage(Model model) {
        return "donate";
    }

    @RequestMapping(path = {"/guid.html", "/guid"})
    public String guidPage(@RequestParam Integer id, Model model) {
        boolean deleteAccess = false;
        Optional<User> user;
        Optional<Guide> guide = guidesDataService.findById(id);
        if (guide.isEmpty())
            guide = Optional.of(new Guide());
        else {
            guidesDataService.incrementViews(guide.get());
            user = getCurrentUser();
            deleteAccess = user.isPresent() && user.get().getRoles().stream()
                    .anyMatch(role -> role.getRole()
                            .matches("owner|fakeowner|admin|moder"));
        }
        model.addAttribute("guid", guide.get());
        model.addAttribute("delete_access", deleteAccess);
        return "guid";
    }

    @RequestMapping(path = {"/guidmenu.html", "/guidmenu"})
    public String guidmenuPage(@RequestParam("tags") Optional<String> tags, Model model) {
        List<Guide> guides;
        if (tags.isEmpty())
            guides = guidesDataService.findAll();
        else
            guides = guidesDataService.findByTags(tags.get().split(" "));
        model.addAttribute("guides", guides);
        return "guidmenu";
    }

    @RequestMapping(path = {"/nolog.html", "/nolog"})
    public String nologPage(Model model) {
        return "nolog";
    }

    @RequestMapping(path = {"/support.html", "/support"})
    public String supportPage(Model model) {
        User user = getCurrentUser().get();
        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getRole()
                        .matches("owner|fakeowner|admin|moder"));
        List<SupportRequest> supportRequests;
        if (isAdmin)
            supportRequests = supportDataService.findAll();
        else
            supportRequests = supportDataService.findAllByUserId(user.getId());
        model.addAttribute("admin", isAdmin);
        model.addAttribute("requests", supportRequests);
        return "support";
    }

    @RequestMapping(path = {"/openticket.html", "/openticket"})
    public String openticketPage(@RequestParam Long id, Model model) {
        Optional<SupportRequest> supportRequest = supportDataService.findById(id);
        if (supportRequest.isEmpty())
            return "error";
        User user = getCurrentUser().get();
        boolean isAccess = supportRequest.get().getUserId().equals(user.getId()) ||
                user.getRoles().stream()
                        .anyMatch(role -> role.getRole()
                                .matches("owner|fakeowner|admin|moder"));
        if (!isAccess)
            return "error";
        boolean isAdmin = !supportRequest.get().getUserId().equals(user.getId());
        if (isAdmin && supportRequest.get().getStatus().equals("filed")) {
            supportRequest.get().setStatus("processed");
            supportDataService.save(supportRequest.get());
        }
        model.addAttribute("admin", isAdmin);
        model.addAttribute("request", supportRequest.get());
        model.addAttribute("open", !supportRequest.get().getStatus().equals("close"));
        model.addAttribute("comments", supportRequest.get().getSupportComments());
        return "openticket";
    }

    @RequestMapping(path = {"/polssogl.html", "/polssogl"})
    public String polssoglPage(Model model) {
        return "polssogl";
    }

    @RequestMapping(path = {"/register.html", "/register"})
    public String registerPage(Model model) {
        return "register";
    }
}
