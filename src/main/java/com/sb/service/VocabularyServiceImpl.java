package com.sb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sb.entity.Vocabulary;
import com.sb.repository.VocabularyRepository;

@Service
public class VocabularyServiceImpl implements VocabularyService {

    @Autowired
    private VocabularyRepository vocabularyRepository;

    @Override
    public List<Vocabulary> selectAll() {
        return vocabularyRepository.selectAll();
    }
}
