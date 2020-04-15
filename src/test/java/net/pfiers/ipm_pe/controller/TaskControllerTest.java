package net.pfiers.ipm_pe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.pfiers.ipm_pe.dto.TaskDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {
    static final String pathPrefix = "/tasks";

    @Autowired
    private MockMvc mvc;

    @Test
    void contextLoads() {
        assertNotNull(mvc);
    }

    @Test
    void getTasks() throws Exception {
        mvc.perform(get(pathPrefix))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<h1>Tasks</h1>")));
    }

    @Test
    void postGetTask() throws Exception {
        var postReq = post(pathPrefix + "/create")
                .param("title", "Test task")
                .param("description", "Test desc");
        var slug = mvc.perform(postReq)
                .andExpect(status().isFound())
                .andReturn().getResponse().getHeader("Location");

        mvc.perform(get(pathPrefix + "/" + slug))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<td>Test task</td>")))
                .andExpect(content().string(containsString("<span>Test desc</span>")));
    }

    @Test
    void getTaskNonExisting() throws Exception {
        var validButNonExistingSlug = "pvz28T3FTDGdE29vO--0vQ";
        mvc.perform(get(pathPrefix + "/" + validButNonExistingSlug))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("<h1>No such task")));
    }

    @Test
    void postTaskBadTitle() throws Exception {
        var postReq = post(pathPrefix + "/create")
                .param("title", "")
                .param("description", "Test desc");
        mvc.perform(postReq)
                .andExpect(status().isBadRequest());
    }

    @Test
    void getCreateTasks() throws Exception {
        mvc.perform(get(pathPrefix + "/create"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<h1>Create task</h1>")));
    }

    @Test
    void postGetEditTask() throws Exception {
        var createPostReq = post(pathPrefix + "/create")
                .param("title", "Test task")
                .param("description", "Test desc");
        var slug = mvc.perform(createPostReq)
                .andExpect(status().isFound())
                .andReturn().getResponse().getHeader("Location");

        var updatePostReq = post(pathPrefix + "/edit/" + slug)
                .param("title", "Test task edited")
                .param("description", "Test desc");
        mvc.perform(updatePostReq)
                .andExpect(status().isFound());
    }

    @Test
    void postGetEditTaskBadTitle() throws Exception {
        var createPostReq = post(pathPrefix + "/create")
                .param("title", "Test task")
                .param("description", "Test desc");
        var slug = mvc.perform(createPostReq)
                .andExpect(status().isFound())
                .andReturn().getResponse().getHeader("Location");

        var updatePostReq = post(pathPrefix + "/edit/" + slug)
                .param("title", "")
                .param("description", "Test desc");
        mvc.perform(updatePostReq)
                .andExpect(status().isBadRequest());
    }

    /* Too much work

    @Test
    void getEditTask() {
    }

    @Test
    void postEditTask() {
    }

    @Test
    void getCreateSubtask() {
    }

    @Test
    void postSubtask() {
    }

    @Test
    void noSuchTask() {
    }

    */
}
