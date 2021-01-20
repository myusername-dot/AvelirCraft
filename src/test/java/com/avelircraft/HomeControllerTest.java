package com.avelircraft;

import com.avelircraft.controllers.HomeController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class HomeControllerTest {

    @Autowired
    private HomeController homeController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails("Dante")
    @Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void lkPage() throws Exception {
        this.mockMvc.perform(get("/lk"))
                .andExpect(status().isOk())
                .andExpect(authenticated().withUsername("dante"))
                .andExpect(xpath("//div[@id='user_data']/a[1]")
                        .string("Dante"))
                .andExpect(xpath("//div[@id='user_data']/a[2]")
                        .string(containsString("owner")))
                .andExpect(xpath("//div[@id='user_data']/a[3]")
                        .string(containsString("монеты")))
                .andExpect(xpath("//div[@id='user_data']/a[4]")
                        .string("2h: 17m"))
                .andExpect(xpath("//div[@id='user_data']/a[5]")
                        .string("1"))
                .andExpect(xpath("//div[@id='user_data']/a[6]")
                        .string("HUMAN"));
    }

    @Test
    @Sql(value = {"/create-news-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-news-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void  homePage() throws Exception {
        this.mockMvc.perform(get("/index"))
                .andExpect(status().isOk())
                .andExpect(xpath("//div[@id='block-content']/div").nodeCount(10))
                .andExpect(xpath("//div[@id='block-content']/div[@data-id=3]").exists())
                .andExpect(xpath("//div[@id='block-content']/div[@data-id=5]").exists());
    }

    @Test
    @Sql(value = {"/create-guide-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-guide-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void guideMenuPageFull() throws Exception {
        this.mockMvc.perform(get("/guidmenu"))
                .andExpect(status().isOk())
                .andExpect(xpath("//div[@id='guides']/div").nodeCount(3))
                // На самом деле Header №3 будет снизу, а Гайд 1 сверху, так уж написал фронтендер
                .andExpect(xpath("//div[@id='guides']/div[1]/div[1]").string(containsString("Header №3")))
                .andExpect(xpath("//div[@id='guides']/div[2]/div[1]").string(containsString("Заголовок гайда №2")))
                .andExpect(xpath("//div[@id='guides']/div[3]/div[1]").string(containsString("Гайд 1")));
    }

    @Test
    @Sql(value = {"/create-guide-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-guide-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void guideMenuPageTags() throws Exception {
        this.mockMvc.perform(get("/guidmenu").param("tags", "два 3три"))
                .andExpect(status().isOk())
                .andExpect(xpath("//div[@id='guides']/div").nodeCount(2))
                .andExpect(xpath("//div[@id='guides']/div[1]/div[1]").string(containsString("Заголовок гайда №2")))
                .andExpect(xpath("//div[@id='guides']/div[2]/div[1]").string(containsString("Гайд 1")));

        this.mockMvc.perform(get("/guidmenu").param("tags", "odin1"))
                .andExpect(status().isOk())
                .andExpect(xpath("//div[@id='guides']/div").nodeCount(1))
                .andExpect(xpath("//div[@id='guides']/div[1]/div[1]").string(containsString("Гайд 1")));

        this.mockMvc.perform(get("/guidmenu").param("tags", "3три odin1"))
                .andExpect(status().isOk())
                .andExpect(xpath("//div[@id='guides']/div").nodeCount(1))
                .andExpect(xpath("//div[@id='guides']/div[1]/div[1]").string(containsString("Гайд 1")));

        this.mockMvc.perform(get("/guidmenu").param("tags", "odin1 3три"))
                .andExpect(status().isOk())
                .andExpect(xpath("//div[@id='guides']/div").nodeCount(1))
                .andExpect(xpath("//div[@id='guides']/div[1]/div[1]").string(containsString("Гайд 1")));

        this.mockMvc.perform(get("/guidmenu").param("tags", "4four4"))
                .andExpect(status().isOk())
                .andExpect(xpath("//div[@id='guides']/div").nodeCount(1))
                .andExpect(xpath("//div[@id='guides']/div[1]/div[1]").string(containsString("Header №3")));

        this.mockMvc.perform(get("/guidmenu").param("tags", "odin1 4four4"))
                .andExpect(status().isOk())
                .andExpect(xpath("//div[@id='guides']/div").nodeCount(0));

        this.mockMvc.perform(get("/guidmenu").param("tags", "null"))
                .andExpect(status().isOk())
                .andExpect(xpath("//div[@id='guides']/div").nodeCount(0));
    }
}
