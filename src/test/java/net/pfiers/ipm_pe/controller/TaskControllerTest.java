package net.pfiers.ipm_pe.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    @WithUserDetails("admin")
    void getTasks() throws Exception {
        mvc.perform(get(pathPrefix))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<h1>Tasks</h1>")));
    }

    @Test
    @WithUserDetails("admin")
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
    @WithUserDetails("admin")
    void getTaskNonExisting() throws Exception {
        var validButNonExistingSlug = "pvz28T3FTDGdE29vO--0vQ";
        mvc.perform(get(pathPrefix + "/" + validButNonExistingSlug))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("<h1><span>No such task")));
    }

    @Test
    @WithUserDetails("admin")
    void postTaskBadTitle() throws Exception {
        var postReq = post(pathPrefix + "/create")
                .param("title", "")
                .param("description", "Test desc");
        mvc.perform(postReq)
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails("admin")
    void getCreateTasks() throws Exception {
        mvc.perform(get(pathPrefix + "/create"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<h1>Create task</h1>")));
    }

    @Test
    @WithUserDetails("admin")
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
        mvc.perform(updatePostReq).andExpect(status().isFound());

        mvc.perform(get(pathPrefix + "/" + slug))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<td>Test task edited</td>")))
                .andExpect(content().string(containsString("<span>Test desc</span>")));
    }

    @Test
    @WithUserDetails("admin")
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

    @Test
    @WithUserDetails("admin")
    void getCreateSubtask() throws Exception {
        var postReq = post(pathPrefix + "/create")
                .param("title", "Test task")
                .param("description", "Test desc");
        var slug = mvc.perform(postReq)
                .andExpect(status().isFound())
                .andReturn().getResponse().getHeader("Location");

        mvc.perform(get(pathPrefix + "/" + slug + "/sub/create"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<h1>Create subtask</h1>")));
    }

    @Test
    @WithUserDetails("admin")
    void postSubtask() throws Exception {
        var createPostReq = post(pathPrefix + "/create")
                .param("title", "Test task")
                .param("description", "Test desc");
        var slug = mvc.perform(createPostReq)
                .andExpect(status().isFound())
                .andReturn().getResponse().getHeader("Location");

        var subCreatePostReq = post(pathPrefix + "/" + slug + "/sub/create")
                .param("title", "Test subtask")
                .param("description", "Test subdesc");
        var subUrl = mvc.perform(subCreatePostReq)
                .andExpect(status().isFound())
                .andReturn().getResponse().getHeader("Location");
        var subSlug = subUrl.substring(subUrl.lastIndexOf('/')); // Subcreate returns absolute url

        mvc.perform(get(pathPrefix + "/" + subSlug))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<td>Test subtask</td>")))
                .andExpect(content().string(containsString("<span>Test subdesc</span>")));
    }

    @Test
    @WithUserDetails("admin")
    void postSubtaskBadTitle() throws Exception {
        var createPostReq = post(pathPrefix + "/create")
                .param("title", "Test task")
                .param("description", "Test desc");
        var slug = mvc.perform(createPostReq)
                .andExpect(status().isFound())
                .andReturn().getResponse().getHeader("Location");

        var subCreatePostReq = post(pathPrefix + "/" + slug + "/sub/create")
                .param("title", "") // BAD
                .param("description", "Test subdesc");
        mvc.perform(subCreatePostReq)
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
    void noSuchTask() {
    }

    */
}
