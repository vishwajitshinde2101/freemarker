package com.freemarker.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class MailRequest {

    private String to;
    private String from;
    private String subject;
    private Map<String, Object> model = new HashMap<>();
}
