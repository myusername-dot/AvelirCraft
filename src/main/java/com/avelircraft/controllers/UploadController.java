package com.avelircraft.controllers;

import com.avelircraft.models.*;
import com.avelircraft.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping(path = "/upload")
public class UploadController {

    private File dir;

    @Autowired
    private UsersDataService usersDataService;

    @Autowired
    private ImagesDataService imagesDataService;

    @Autowired
    private NewsDataService newsDataService;

    @Autowired
    private GuidesDataService guidesDataService;

    public UploadController() {
        dir = new File("/Server_files");
        if (!dir.exists()) dir.mkdir();
    }

    @RequestMapping(path = "/image", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<byte[]> getImage(@RequestParam(required = false) boolean profile,
                                           @RequestParam("id") String imgInfo) {
        File foundFile = Arrays.stream(Objects.requireNonNull(dir
                .listFiles()))
                .filter(f -> f.getName().equals(imgInfo))
                .findFirst()
                .orElse(null);
        Image img = new Image();
        try {
            img.setImg(new FileInputStream(foundFile).readAllBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.noContent().build();
        }
        String[] info = imgInfo.split("\\.");
        img.setType("image/" + info[1]);

        return ResponseEntity.ok()
                .contentLength(img.getImg().length)
                .contentType(MediaType.parseMediaType(img.getType()))
                .body(img.getImg());
    }

    @RequestMapping(path = "/user/icon", method = RequestMethod.POST)
    public String saveProfilePic(@RequestParam("image") MultipartFile image, HttpSession session) {
        if (!image.isEmpty()) {
            if (!Objects.requireNonNull(image.getContentType()).startsWith("image"))
                return "error";
            User user = (User) session.getAttribute("user");
            Image img;
            try {
                img = new Image(user.getId(), image.getContentType(), image.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                return "error";
            }
            imagesDataService.save(img);
            usersDataService.addIcon(user);
        }
        return "redirect:/lk";
    }

    @RequestMapping(path = "/user/icon", method = RequestMethod.GET)
    //@ResponseBody
    public ResponseEntity<byte[]> getProfilePic(@RequestParam("id") String id) {
        Image img;
        try {
            img = imagesDataService.findByUserId(Integer.parseInt(id)).get();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok()
                .contentLength(img.getImg().length)
                .contentType(MediaType.parseMediaType(img.getType()))
                .body(img.getImg());
    }

    @RequestMapping(path = "/news", method = RequestMethod.POST)
    public String createNews(@RequestParam(defaultValue = "") String header,
                             @RequestParam("description") String description,
                             @RequestParam("message") String message,
                             @RequestParam("image") MultipartFile image,
                             HttpSession session) {
        User user = (User) session.getAttribute("user");
        boolean access = user != null && user.getRoles().stream()
                .anyMatch(role -> role.getRole()
                        .matches("owner|fakeowner|admin|moder"));
        if (!access)
            return "error";
        if (!image.isEmpty()) {
            try {
                // ---------------- Сохраняем изображение ----------------
                Long imgId = System.currentTimeMillis();
                byte[] bytes = image.getBytes();
                String[] type = Objects.requireNonNull(image.getContentType()).split("/");
                if (!type[0].equals("image"))
                    throw new Exception("Файл не является изображением");
                String imgName = imgId.toString() + "." + type[1];
                File file = new File(dir, imgName);
                file.createNewFile();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(file));
                stream.write(bytes);
                stream.close();
                // ----------------- Сохраняем новость -----------------
                News news = new News(header, description, message, imgName);
                newsDataService.save(news);
                return "redirect:/";
            } catch (Exception e) {
                System.out.println("createNews " + e);
                return "error";
            }
        } else {
            News news = new News(header, description, message);
            newsDataService.save(news);
            return "redirect:/";
        }
    }

    @RequestMapping(path = "/guide", method = RequestMethod.POST)
    public String createGuide(@RequestParam(defaultValue = "") String header,
                              @RequestParam("description") String description,
                              @RequestParam("tags") Optional<String> tags,
                              @RequestParam("link") String link,
                              HttpSession session) {
        User user = (User) session.getAttribute("user");
        boolean access = user != null && user.getRoles().stream()
                .anyMatch(role -> role.getRole()
                        .matches("owner|fakeowner|admin|moder"));
        if (!access)
            return "error";
        Guide guide = new Guide(link, header, description, " " + tags.orElse("") + " ");
        guide = guidesDataService.save(guide);

        return "redirect:/guidmenu.html";
    }
}