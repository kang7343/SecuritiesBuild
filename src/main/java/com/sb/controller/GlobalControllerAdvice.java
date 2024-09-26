package com.sb.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.sb.security.UserAccountDetails;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void addUserToModel(Model model, @AuthenticationPrincipal UserAccountDetails userDetails) {
        if (userDetails != null) {
            model.addAttribute("userId", userDetails.getId());
            model.addAttribute("user", userDetails);
            System.out.println("UserID: " + userDetails.getId());
        }
    }
}
