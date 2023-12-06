package nobicode.restful.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nobicode.restful.dto.AccountResponse;
import nobicode.restful.dto.CreateAccountRequest;
import nobicode.restful.dto.WebResponse;
import nobicode.restful.entity.Account;
import nobicode.restful.entity.User;
import nobicode.restful.repository.AccountRepository;
import nobicode.restful.repository.UserRepository;
import nobicode.restful.security.BCrypt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        accountRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setUsername("hiko");
        user.setPasswords(BCrypt.hashpw("test", BCrypt.gensalt()));
        user.setName("Hiko");
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis() + 1000000000L);
        userRepository.save(user);
    }

    @Test
    void createAccountBadRequest() throws Exception {
        CreateAccountRequest request = new CreateAccountRequest();
        request.setAccountNumber("");

        mockMvc.perform(
                post("/api/accounts")
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
    void createAccountSuccess() throws Exception {
        CreateAccountRequest request = new CreateAccountRequest();
        request.setAccountNumber("0000000001");

        mockMvc.perform(
                post("/api/accounts")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<AccountResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());
            assertEquals("0000000001", response.getData().getAccountNumber());

            assertTrue(accountRepository.existsById(response.getData().getId()));
        });
    }

    @Test
    void getAccountNotFound() throws Exception {
        mockMvc.perform(
                get("/api/accounts/123321")
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
    void getAccountSuccess() throws Exception {
        User user = userRepository.findById("hiko").orElseThrow();

        Account account = new Account();
        account.setId(UUID.randomUUID().toString());
        account.setUser(user);
        account.setAccountNumber("0000000002");
        accountRepository.save(account);

        mockMvc.perform(
                get("/api/accounts/" + account.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<AccountResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());
            assertEquals(account.getId(), response.getData().getId());
            assertEquals(account.getAccountNumber(), response.getData().getAccountNumber());
        });
    }

}