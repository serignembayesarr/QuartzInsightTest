package com.quartzinsight.demo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameDto {

    private Long id;

    private String title;

    private String url;
}