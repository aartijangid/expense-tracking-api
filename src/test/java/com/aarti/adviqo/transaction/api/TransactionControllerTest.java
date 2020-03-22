package com.aarti.adviqo.transaction.api;

import com.aarti.adviqo.transaction.usecases.add.SaveTransactionUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TransactionController.class)
@ExtendWith(SpringExtension.class)
class TransactionControllerTest {

    @MockBean
    private SaveTransactionUseCase saveTransactionUseCase;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void when_Transaction_then_ShouldReturnOk() throws Exception {

        mockMvc.perform(put("/transaction/{transactionId}", 1)
                .accept(APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\"amount\":10.0,\"type\":\"cars\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\":\"OK\"}"));

    }


}