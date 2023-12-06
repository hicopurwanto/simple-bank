package nobicode.restful.controller;

import nobicode.restful.dto.AccountResponse;
import nobicode.restful.dto.CreateAccountRequest;
import nobicode.restful.dto.WebResponse;
import nobicode.restful.entity.User;
import nobicode.restful.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping(
            path = "/api/accounts",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<AccountResponse> create(User user, @RequestBody CreateAccountRequest request) {
        AccountResponse accountResponse = accountService.create(user, request);
        return WebResponse.<AccountResponse>builder().data(accountResponse).build();
    }

    @GetMapping(
            path = "/api/accounts/{accountId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<AccountResponse> get(User user, @PathVariable("accountId") String accountId) {
        AccountResponse accountResponse = accountService.get(user, accountId);
        return WebResponse.<AccountResponse>builder().data(accountResponse).build();
    }
}
