package com.coral.test.flink.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventInfo {
    private String user;
    private String url;
    private Long timeLength;
}
