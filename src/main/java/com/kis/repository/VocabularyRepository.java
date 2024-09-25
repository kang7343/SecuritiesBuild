package com.kis.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kis.entity.Vocabulary;

@Mapper
public interface VocabularyRepository {

    List<Vocabulary> selectAll();
}
