package com.sb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sb.service.VocabularyService;

@Controller
public class VocabularyController {

    @Autowired
    VocabularyService vocabularyService;

    @GetMapping("/voca")
    public String getVocabularies(Model model) {
        model.addAttribute("voca", vocabularyService.selectAll());
        return "vocabulary";
    }
}
