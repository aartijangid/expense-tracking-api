package com.aarti.adviqo.transaction.api;

import com.aarti.adviqo.transaction.api.dto.GetTransactionByIdResponse;
import com.aarti.adviqo.transaction.domain.Transaction;
import com.aarti.adviqo.transaction.usecases.add.SaveTransactionUseCase;
import com.aarti.adviqo.transaction.usecases.get.byId.GetTransactionById;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(controllers = TransactionController.class)
//@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

    @MockBean
    private SaveTransactionUseCase saveTransactionUseCase;

    @MockBean
    private GetTransactionById getTransactionById;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void when_TransactionWithoutParentId_then_ShouldReturnOk() throws Exception {

        mockMvc.perform(put("/transactionservice/transaction/{transactionId}", 1)
                .accept(APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\"amount\":10.0,\"type\":\"cars\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\":\"OK\"}"));

        verify(saveTransactionUseCase).saveTransaction(1L, "cars", 10.0, 0L);

    }

    @Test
    void when_TransactionWithParentId_then_ShouldReturnOk() throws Exception {

        mockMvc.perform(put("/transactionservice/transaction/{transactionId}", 1)
                .accept(APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\"amount\":10.0,\"type\":\"cars\", \"parent_id\":1 }"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\":\"OK\"}"));

    }

    @Test
    void when_GivenTransactionId_then_ShouldReturnTransactionDetails() throws Exception {
        Transaction transaction = new Transaction(1, 10.0,"cars" );
        given(getTransactionById.searchTransactionById(1)).willReturn(transaction);

        mockMvc.perform(get("/transactionservice/transaction/{transactionId}", 1)
                .accept(APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk());
    }
}