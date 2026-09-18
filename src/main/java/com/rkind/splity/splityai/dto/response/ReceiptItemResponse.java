package com.rkind.splity.splityai.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptItemResponse {

    private String name;

    private Integer quantity;

    private Double amount;

}