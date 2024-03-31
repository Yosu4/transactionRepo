package com.bank.transaction.dao.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ResponseEntity {
    private String status;
    private String statusCode;
    private Object bodyResponse;
}
