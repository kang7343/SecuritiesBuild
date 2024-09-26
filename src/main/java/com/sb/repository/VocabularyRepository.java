package com.sb.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sb.entity.Vocabulary;

@Mapper
public interface VocabularyRepository {

    List<Vocabulary> selectAll();
}
