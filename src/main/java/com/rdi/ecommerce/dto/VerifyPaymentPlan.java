package com.rdi.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class VerifyPaymentPlan {

    private String order_id;
    private String paidAt;
    private String createdAt;
    private String requested_amount;
    private String pos_transaction_data;
    private String source;
    private String fees_breakdown;
    private String transaction_date;
}
