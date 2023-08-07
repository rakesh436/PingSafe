package com.pingSafe.Messages;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomerResponse {

    post_success(200,"customer created"),
    post_sameDataError(500,"UNIQUE constraint failed: customers.id"),
    post_mobileNumCountError(500,"CHECK constraint failed: length(phone_number) = 10");


    private final int responseCode;
    private final String responseMessage;
}
