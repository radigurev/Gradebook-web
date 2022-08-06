package com.example.onlinegradebook.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class IndexControllerClass {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void returnIndexPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("dashboardInfo"));
    }

    @Test
    void returnAdminGradePage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/tables/grades"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("grades"));
    }

    @Test
    void returnAdminResponsePage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/tables/responses"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("responses"));
    }

    @Test
    void returnAdminAbsencePage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("absences"));
    }
    @Test
    void returnAdminTeachersPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/admin/teachers"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("teachers"));
    }

    @Test
    void returnAdminSubjectsPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/admin/subjects"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("subjects"));
    }

    @Test
    void returnAdminTestPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/tables/test"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("tests"));
    }

    @Test
    void returnProgramPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/admin/program"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("programs"));
    }
    @Test
    void returnMaterialPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/admin/material"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("material"));
    }

    @Test
    void returnClassPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/admin/classes"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("classes"));
    }

    @Test
    void returnStudentGradesPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/view/grades"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("grades"));
    }

    @Test
    void returnStudentAbsencePage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/view/absences"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("absence"));
    }

    @Test
    void returnStudentResponsePage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/view/responses"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("responses"));
    }
    @Test
    void returnStudentTestsPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/view/test"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("tests"));
    }
}
