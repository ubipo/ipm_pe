package net.pfiers.ipm_pe.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void contextLoads() {
        assertNotNull(mvc);
    }

    @Test
    void getLogin() throws Exception {
        mvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<h1>Login</h1>")));
    }

    @Test
    void getSignup() throws Exception {
        mvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<h1>Signup</h1>")));
    }

    @Test
    void postGetSignup() throws Exception {
        mvc.perform(post("/signup")
                .param("username", "test")
                .param("passwordRaw", "test")
                .param("passwordRawRepeated", "test"))
                .andExpect(status().isFound());

        AtomicReference<MockHttpSession> session = new AtomicReference<>();
        mvc.perform(post("/login")
                .param("username", "test")
                .param("password", "test"))
                .andExpect(status().isFound())
                .andDo(result -> session.set((MockHttpSession) result.getRequest().getSession()));

        mvc.perform(get("/").session(session.get()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<button>logout</button>")))
                .andExpect(content().string(not(containsString("Login</a>"))));
    }

    @Test
    void postSignupBadUsername() throws Exception {
        mvc.perform(post("/signup")
                .param("username", "")
                .param("passwordRaw", "test")
                .param("passwordRawRepeated", "test"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void postSignupNonMatchingPasswords() throws Exception {
        mvc.perform(post("/signup")
                .param("username", "test")
                .param("passwordRaw", "test")
                .param("passwordRawRepeated", "absolutely not the word test"))
                .andExpect(status().isBadRequest());
    }
}
