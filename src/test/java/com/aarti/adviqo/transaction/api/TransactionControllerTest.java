package com.aarti.adviqo.transaction.api;

import com.aarti.adviqo.transaction.repository.exception.TransactionNotFoundException;
import com.aarti.adviqo.transaction.domain.Transaction;
import com.aarti.adviqo.transaction.usecases.add.SaveTransactionUseCase;
import com.aarti.adviqo.transaction.usecases.get.byId.GetTransactionById;
import com.aarti.adviqo.transaction.usecases.get.byType.GetTransactionByType;
import com.aarti.adviqo.transaction.usecases.get.sum.GetTotalTransactionAmount;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TransactionController.class)
@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@AutoConfigureMockMvc
class TransactionControllerTest {

    @MockBean
    private SaveTransactionUseCase saveTransactionUseCase;

    @MockBean
    private GetTransactionById getTransactionById;

    @MockBean
    private GetTransactionByType getTransactionByType;

    @MockBean
    private GetTotalTransactionAmount getTotalTransactionAmount;
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

        verify(saveTransactionUseCase).saveTransaction(1L, "cars", 10.0, 0L);

    }

    @Test
    void when_TransactionId_then_ShouldReturnTransactionDetails() throws Exception {
        Transaction transaction = new Transaction(1, 10.0,"cars" );
        given(getTransactionById.searchTransactionById(1)).willReturn(transaction);

        mockMvc.perform(get("/transactionservice/transaction/{transactionId}", 1)
                .accept(APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk());

        verify(getTransactionById).searchTransactionById(1L);
    }

    @Test
    void when_TransactionId_then_ShouldThrowNotFoundTransactionException() throws Exception {

        given(getTransactionById.searchTransactionById(1L)).willThrow(new TransactionNotFoundException());

        mockMvc.perform(get("/transactionservice/transaction/{transactionId}", 1L)
                .accept(APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isNotFound());


    }

    @Test
    void when_Type_then_ShouldReturnTransactionDetails() throws Exception {
        given(getTransactionByType.searchTransactionOfType("cars")).willReturn(any());

        mockMvc.perform(get("/transactionservice/types/{type}", "cars")
                .accept(APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk());

        verify(getTransactionByType).searchTransactionOfType("cars");
    }

    @Test
    void when_Type_then_ShouldThrowNotFoundTransactionException() throws Exception {

        given(getTransactionByType.searchTransactionOfType("cars")).willThrow(new TransactionNotFoundException());

        mockMvc.perform(get("/transactionservice/types/{type}", "cars")
                .accept(APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isNotFound());
    }

    @Test
    void when_TransactionId_then_ShouldReturnSumOfAllTransactions() throws Exception {
        given(getTotalTransactionAmount.getTransactionAmount(1L)).willReturn(50D);

        mockMvc.perform(get("/transactionservice/sum/{transactionId}", 1L)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk());

        verify(getTotalTransactionAmount).getTransactionAmount(1L);
    }
}