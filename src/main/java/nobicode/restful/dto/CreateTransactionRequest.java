package nobicode.restful.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTransactionRequest {

    @JsonIgnore
    @NotBlank
    private String accountId;
    private String id;

    @NotBlank
    private String toAccount;

    @NotBlank
    private String amount;

    private Date createdAt;
}
