package com.avelircraft;

import com.avelircraft.models.News;
import com.avelircraft.models.User;
import com.avelircraft.services.ImagesDataService;
import com.avelircraft.services.NewsDataService;
import com.avelircraft.services.UsersDataService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = { SpringBootApplicationClass.class })
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SpringBootApplicationClassTests {

    @Autowired
    private NewsDataService newsDataService;

    @Autowired
    private UsersDataService usersDataService;

    @Autowired
    private ImagesDataService imagesDataService;


    @Ignore
    @Test
    @Transactional
    @Rollback
    public void testUsersDataService() {
        System.out.println("-------------------------------------------");
        System.out.println("Start 1");
        Iterable<User> usersI = usersDataService.findAll();
        if(!usersI.iterator().hasNext()) {
            System.out.println("No news found");
        }
        else {
            usersI.forEach(System.out::println);
        }
        System.out.println("-------------------------------------------");
        System.out.println("Start 2");
        Optional<User> user = usersDataService.findByName("Kolbaska");
        if(user.isPresent())
            System.out.println("User is found " + user.get());
        else
            System.out.println("User Kolbaska not found");
        System.out.println("-------------------------------------------");
        System.out.println("Start 3");
        Optional<User> user2 = usersDataService.findByNameAndPassword("Dante", "passwort");
        if(user2.isPresent())
            System.out.println("User is found " + user2.get());
        else
            System.out.println("User Dante with password not found");
        System.out.println("-------------------------------------------");
    }

    @Ignore
    @Test
    @Transactional
    @Rollback
    @Commit
    public void testNewsDataService() {
        System.out.println("-------------------------------------------");
        System.out.println("Start 1");
        News news = new News();
        newsDataService.save(news);
        News news2 = new News("header", "description", "message");
        newsDataService.save(news2);

        System.out.println("-------------------------------------------");
        System.out.println("Start 2");
        Iterable<News> newsI = newsDataService.findAll();
        if(!newsI.iterator().hasNext()) {
            System.out.println("No news found");
        }
        else {
            newsI.forEach(System.out::println);
        }

        System.out.println("-------------------------------------------");
        System.out.println("Start 3");
        newsDataService.deleteAll();

        System.out.println("-------------------------------------------");
        System.out.println("Start 4");
        newsI = newsDataService.findAll();
        if(!newsI.iterator().hasNext()) {
            System.out.println("No news found");
        }
        else {
            newsI.forEach(System.out::println);
        }
        System.out.println("-------------------------------------------");
    }

    @Ignore
    @Test
    @Transactional
    @Rollback
    public void testImagesDataService() {
        imagesDataService.deleteAll();
    }

}
