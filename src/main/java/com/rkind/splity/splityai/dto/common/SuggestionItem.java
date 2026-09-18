package com.rkind.splity.splityai.dto.common;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuggestionItem {

    private String id;

    private String title;

    private String value;

    private String icon;

}