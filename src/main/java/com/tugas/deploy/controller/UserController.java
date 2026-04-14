package com.tugas.deploy.controller;

import com.tugas.deploy.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private final String USERNAME = "admin";
    private final String PASSWORD = "20230140198";

    // penyimpanan sementara (tanpa database)
    private List<User> dataMahasiswa = new ArrayList<>();

    // LOGIN PAGE
    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    // PROCESS LOGIN
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        if (username.equals(USERNAME) && password.equals(PASSWORD)) {
            session.setAttribute("username", username);
            session.setAttribute("nim", password);
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Login gagal!");
            return "login";
        }
    }

    // HOME PAGE
    @GetMapping("/home")
    public String home(HttpSession session, Model model) {

        String nim = (String) session.getAttribute("nim");
        if (nim == null) return "redirect:/";

        model.addAttribute("nim", nim);
        model.addAttribute("list", dataMahasiswa);

        return "home";
    }

    // FORM PAGE
    @GetMapping("/form")
    public String form(HttpSession session, Model model) {

        if (session.getAttribute("nim") == null) return "redirect:/";

        model.addAttribute("user", new User());
        return "form";
    }

    // SUBMIT FORM
    @PostMapping("/submit")
    public String submit(@ModelAttribute User user,
                         HttpSession session) {

        if (session.getAttribute("nim") == null) return "redirect:/";

        dataMahasiswa.add(user);
        return "redirect:/home";
    }

    // LOGOUT
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}