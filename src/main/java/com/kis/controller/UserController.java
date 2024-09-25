package com.kis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kis.entity.User;
import com.kis.form.UserForm;
import com.kis.helper.UserHelper;
import com.kis.service.UserService;

@SessionAttributes("userForm")
@Controller
@RequestMapping("user/account")
public class UserController {

    @ModelAttribute("userForm")
    public UserForm setupForm() {
        return new UserForm();
    }

    @Autowired
    private UserService userService;

    @GetMapping("register")
    public String register() {
        return "user/register";
    }

    @PostMapping("confirm")
    public String confirm(@Validated @ModelAttribute("userForm") UserForm userForm,
            BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        if (bindingResult.hasErrors()) {
            return "user/register";
        }

        String originalPassword = userForm.getPassword();
        String maskedPassword = originalPassword.replaceAll(".", "*");
        model.addAttribute("maskedPassword", maskedPassword);

        boolean existingUser = false;
        User user = UserHelper.convert(userForm);
        existingUser = userService.isDuplicateuserMail((user).getMailAddress());
        model.addAttribute("userForm", userForm);

        if (existingUser == true) {
            redirectAttributes.addFlashAttribute("errorMessage", "アカウントが既に存在しています。");
            return "redirect:/user/account/register";
        }

        return "user/confirm";
    }

    @PostMapping("execute")
    public String execute(@ModelAttribute("userForm") UserForm userForm, RedirectAttributes redirectAttributes,
            Model model) {
        User user = UserHelper.convert(userForm);
        UserHelper.setTimestamp(user);
        userService.insertUser(user);
        return "redirect:/user/account/complete";
    }

    @GetMapping("complete")
    public String complete(@ModelAttribute("userForm") UserForm userForm, SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "user/complete";
    }
}
