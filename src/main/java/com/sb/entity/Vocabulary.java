package com.sb.entity;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Vocabulary {
    private Integer id;
    private String word;
    private String definition;
    private String example_sentence;
    private String japanese_translation;
    private Timestamp createdAt;

}
