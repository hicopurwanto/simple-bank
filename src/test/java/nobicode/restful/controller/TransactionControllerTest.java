package nobicode.restful.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nobicode.restful.dto.CreateTransactionRequest;
import nobicode.restful.dto.TransactionResponse;
import nobicode.restful.dto.WebResponse;
import nobicode.restful.entity.Account;
import nobicode.restful.entity.Transaction;
import nobicode.restful.entity.User;
import nobicode.restful.repository.AccountRepository;
import nobicode.restful.repository.TransactionRepository;
import nobicode.restful.repository.UserRepository;
import nobicode.restful.security.BCrypt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        transactionRepository.deleteAll();
        accountRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setUsername("hiko");
        user.setPasswords(BCrypt.hashpw("test", BCrypt.gensalt()));
        user.setName("Hiko");
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis() + 1000000000L);
        userRepository.save(user);

        Account account = new Account();
        account.setId("hiko");
        account.setUser(user);
        account.setAccountNumber("0000000002");
        accountRepository.save(account);
    }

    @Test
    void createTransactionBadRequest() throws Exception{
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setToAccount("");
        request.setAmount("");

        mockMvc.perform(
                post("/api/accounts/hiko/transactions")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void createTransactionSuccess() throws Exception{
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setToAccount("0000000001");
        request.setAmount("1000000");

        mockMvc.perform(
                post("/api/accounts/hiko/transactions")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<TransactionResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());
            assertEquals(request.getToAccount(), response.getData().getToAccount());
            assertEquals(request.getAmount(), response.getData().getAmount());

            assertTrue(transactionRepository.existsById(response.getData().getId()));
        });
    }

    @Test
    void getTransactionNotFound() throws Exception{
        mockMvc.perform(
                get("/api/accounts/hiko/transactions/hiko")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void getTransactionSuccess() throws Exception{
        Account account = accountRepository.findById("hiko").orElseThrow();

        Transaction transaction = new Transaction();
        transaction.setId("hiko");
        transaction.setAccount(account);
        transaction.setToAccount("0000000003");
        transaction.setAmount("3000000");
        transactionRepository.save(transaction);

        mockMvc.perform(
                get("/api/accounts/hiko/transactions/hiko")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<TransactionResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());
            assertEquals(transaction.getId(), response.getData().getId());
            assertEquals(transaction.getToAccount(), response.getData().getToAccount());
            assertEquals(transaction.getAmount(), response.getData().getAmount());
        });
    }

    @Test
    void listTransactionNotFound() throws Exception{
        mockMvc.perform(
                get("/api/accounts/idsalah/transactions")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void listTransactionSuccess() throws Exception{
        Account account = accountRepository.findById("hiko").orElseThrow();

        for (int i = 0; i < 5; i++) {
            Transaction transaction = new Transaction();
            transaction.setId("hiko-" + i);
            transaction.setAccount(account);
            transaction.setToAccount("0000000003");
            transaction.setAmount("3000000");
            transactionRepository.save(transaction);
        }

        mockMvc.perform(
                get("/api/accounts/hiko/transactions")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<TransactionResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals(5, response.getData().size());
        });
    }

}