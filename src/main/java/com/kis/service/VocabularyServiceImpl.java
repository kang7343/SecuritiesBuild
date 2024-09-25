package com.kis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kis.entity.Vocabulary;
import com.kis.repository.VocabularyRepository;

@Service
public class VocabularyServiceImpl implements VocabularyService {

    @Autowired
    private VocabularyRepository vocabularyRepository;

    @Override
    public List<Vocabulary> selectAll() {
        return vocabularyRepository.selectAll();
    }
}
