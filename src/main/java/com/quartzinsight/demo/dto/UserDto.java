package com.quartzinsight.demo.dto;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {

    private Long id;

    private String username;

    private String email;

    private Set<Integer> gameIds;

    private Set<Long> friendsId = new HashSet<>();
}