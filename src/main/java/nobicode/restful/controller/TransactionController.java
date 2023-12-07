package nobicode.restful.controller;

import nobicode.restful.dto.CreateTransactionRequest;
import nobicode.restful.dto.TransactionResponse;
import nobicode.restful.dto.WebResponse;
import nobicode.restful.entity.User;
import nobicode.restful.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping(
            path = "/api/accounts/{accountId}/transactions",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<TransactionResponse> create(User user,
                                                   @RequestBody CreateTransactionRequest request,
                                                   @PathVariable("accountId") String accountId) {
        request.setAccountId(accountId);
        TransactionResponse transactionResponse = transactionService.create(user, request);
        return WebResponse.<TransactionResponse>builder().data(transactionResponse).build();
    }

    @GetMapping(
            path = "/api/accounts/{accountId}/transactions/{transactionId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<TransactionResponse> get(User user,
                                                @PathVariable("accountId") String accountId,
                                                @PathVariable("transactionId") String transactionId) {

        TransactionResponse transactionResponse = transactionService.get(user, accountId, transactionId);
        return WebResponse.<TransactionResponse>builder().data(transactionResponse).build();
    }

    @GetMapping(
            path = "/api/accounts/{accountId}/transactions",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<TransactionResponse>> list(User user,
                                                       @PathVariable("accountId") String accountId) {
        List<TransactionResponse> transactionResponses = transactionService.list(user, accountId);
        return WebResponse.<List<TransactionResponse>>builder().data(transactionResponses).build();
    }
}
