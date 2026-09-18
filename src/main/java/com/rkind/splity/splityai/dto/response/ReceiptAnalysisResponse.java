package com.rkind.splity.splityai.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptAnalysisResponse {

    private String merchant;

    private String currency;

    private LocalDate billDate;

    private Double totalAmount;

    private Double taxAmount;

    private List<ReceiptItemResponse> items;

}