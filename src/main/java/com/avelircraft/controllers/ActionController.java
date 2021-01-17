package com.avelircraft.controllers;

import com.avelircraft.models.*;
import com.avelircraft.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Optional;

@Controller
@RequestMapping(path = "/act")
public class ActionController extends BaseController {

    private File dir;

    @Autowired
    private UsersDataService usersDataService;

    @Autowired
    private NewsDataService newsDataService;

    @Autowired
    private CommentsDataService commentsDataService;

    @Autowired
    private GuidesDataService guidesDataService;

    @Autowired
    private SupportRequestsDataService requestsDataService;

    public ActionController() {
        dir = new File("/Server_files");
        if (!dir.exists()) dir.mkdir();
    }

    @RequestMapping(path = "/news/comment", method = RequestMethod.POST)
    public RedirectView commentNews(@RequestParam("news_id") Integer nId,
                                    @RequestParam("message") String message,
                                    RedirectAttributes attr) {
        if (nId == 0)
            return new RedirectView("/");
        User user = getCurrentUser().get();
        Optional<News> news = newsDataService.findById(nId);
        if (news.isEmpty())
            return new RedirectView("/error");
        Comment comment = new Comment(user, news.get(), message);
        System.out.println(comment);
        if (commentsDataService.save(comment) == null)
            return new RedirectView("/error");
        //System.out.println(comment);
        attr.addAttribute("id", nId);
        return new RedirectView("/news");
    }

    @RequestMapping(path = "/news/delete", method = RequestMethod.POST)
    public String deleteNews(@RequestParam("news_id") Integer nId) {
        User user = getCurrentUser().get();
        boolean deleteAccess = user.getRoles().stream()
                .anyMatch(role -> role.getRole()
                        .matches("owner|fakeowner|admin|moder"));
        if (!deleteAccess)
            return "error";
        newsDataService.deleteById(nId);
        return "redirect:/";
    }

    @RequestMapping(path = "/guide/delete", method = RequestMethod.POST)
    public String deleteGuide(@RequestParam("guide_id") Integer gId) {
        User user = getCurrentUser().get();
        boolean deleteAccess = user.getRoles().stream()
                .anyMatch(role -> role.getRole()
                        .matches("owner|fakeowner|admin|moder"));
        if (!deleteAccess)
            return "error";
        guidesDataService.deleteById(gId);
        return "redirect:/guidmenu";
    }

    @RequestMapping(path = "/news/comment/delete", method = RequestMethod.POST)
    public RedirectView deleteComment(@RequestParam("news_id") Integer nId,
                                      @RequestParam("comment_id") Long cId,
                                      RedirectAttributes attr) {
        User user = getCurrentUser().get();
        Optional<Comment> com = commentsDataService.findById(cId);
        boolean deleteAccess = com.isPresent() && user.getId().equals(com.get().getUser().getId()) || user.getRoles().stream()
                .anyMatch(role -> role.getRole()
                        .matches("owner|fakeowner|admin|moder"));
        if (!deleteAccess)
            return new RedirectView("/error");
        commentsDataService.delete(com.get());
        attr.addAttribute("id", nId);
        return new RedirectView("/news");
    }

    @RequestMapping(path = "/admin/privilege", method = RequestMethod.POST)
    public String grantPrivilege(@RequestParam("name") String username,
                                 @RequestParam("priveleg") String privilege) {
        User user = getCurrentUser().get();
        boolean access = user.getRoles().stream()
                .anyMatch(role -> role.getRole()
                        .matches("owner|fakeowner|admin|moder"));
        if (!access)
            return "error";
        Optional<User> grantUser = usersDataService.findByName(username.toLowerCase());
        if (grantUser.isEmpty())
            return "error";
        boolean has = grantUser.get().getRoles().stream()
                .anyMatch(role -> role.getRole()
                        .matches(privilege));
        if (has)
            return "error";
        String uuid = username + " " + privilege + " " + System.currentTimeMillis();
        Role role = new Role(uuid, grantUser.get(), privilege);
        grantUser.get().setRole(role);
        usersDataService.update(grantUser.get());
        return "redirect:/adminpanel";
    }

    @RequestMapping(path = "/admin/delete_user", method = RequestMethod.POST)
    public String deleteUser(@RequestParam("name") String username) {
        User user = getCurrentUser().get();
        boolean access = user.getRoles().stream()
                .anyMatch(role -> role.getRole()
                        .matches("admin"));
        if (!access)
            return "error";
        Optional<User> deleteUser = usersDataService.findByName(username.toLowerCase());
        if (deleteUser.isEmpty())
            return "error";
        usersDataService.delete(deleteUser.get());
        return "redirect:/adminpanel";
    }

    @RequestMapping(path = "/support", method = RequestMethod.POST)
    public RedirectView support(@RequestParam("tagsupport") String topic,
                                @RequestParam("descsupport") String message,
                                RedirectAttributes attr) {
        User user = getCurrentUser().get();
        SupportRequest supportRequest = new SupportRequest(user.getId(), topic);
        SupportComment supportComment = new SupportComment(user, supportRequest, message);
        supportRequest.addSupportComment(supportComment);
        supportRequest = requestsDataService.save(supportRequest);
        attr.addAttribute("id", supportRequest.getId());
        return new RedirectView("/openticket");
    }

    @RequestMapping(path = "/support/comment", method = RequestMethod.POST)
    public RedirectView commSupport(@RequestParam("req_id") Long id,
                                    @RequestParam("message") String message,
                                    RedirectAttributes attr) {
        User user = getCurrentUser().get();
        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getRole()
                        .matches("owner|fakeowner|admin|moder"));
        Optional<SupportRequest> supportRequest = requestsDataService.findById(id);
        if (supportRequest.isEmpty())
            return new RedirectView("/error");
        if (!isAdmin && !supportRequest.get().getUserId().equals(user.getId()))
            return new RedirectView("/error");
        SupportComment supportComment = new SupportComment(user, supportRequest.get(), message);
        supportRequest.get().addSupportComment(supportComment);
        requestsDataService.save(supportRequest.get());
        attr.addAttribute("id", id);
        return new RedirectView("/openticket");
    }

    @RequestMapping(path = "/support/close", method = RequestMethod.POST)
    public RedirectView closeSupport(@RequestParam("req_id") Long id,
                                    RedirectAttributes attr) {
        User user = getCurrentUser().get();
        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getRole()
                        .matches("owner|fakeowner|admin|moder"));
        Optional<SupportRequest> supportRequest = requestsDataService.findById(id);
        if (supportRequest.isEmpty())
            return new RedirectView("/error");
        if (!isAdmin && !supportRequest.get().getUserId().equals(user.getId()))
            return new RedirectView("/error");
        supportRequest.get().setStatus("close");
        requestsDataService.save(supportRequest.get());
        attr.addAttribute("id", id);
        return new RedirectView("/openticket");
    }
}
