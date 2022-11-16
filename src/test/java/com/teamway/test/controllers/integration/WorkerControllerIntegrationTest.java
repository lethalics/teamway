package com.teamway.test.controllers.integration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.teamway.test.controllers.WorkerController;
import com.teamway.test.controllers.requests.RequestWorkerDto;
import com.teamway.test.controllers.responses.WorkerDto;
import com.teamway.test.services.WorkerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(WorkerController.class)
public class WorkerControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private WorkerService workerService;

    @Test
    public void testCreateWorkerSuccessfully() throws Exception {
        var requestWorkerDto = WorkerDto.builder()
            .id(1L)
            .firstName("Abc")
            .lastName("Def")
            .build();

        when(workerService.createWorker(any(RequestWorkerDto.class))).thenReturn(requestWorkerDto);

        mvc.perform(post("/api/v1/worker")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\" : \"Abc\", \"lastName\":  \"Def\"}"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.firstName").value("Abc"))
            .andExpect(jsonPath("$.lastName").value("Def"));
    }
}