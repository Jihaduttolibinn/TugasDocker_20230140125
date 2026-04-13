package com.example.Pertemuan6.controller;

import com.example.Pertemuan6.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    // Penyimpanan data temporary di memori
    private static final List<User> userList = new ArrayList<>();

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username, 
                               @RequestParam String password, 
                               HttpSession session, 
                               Model model) {
        // Logika sesuai request: username = admin, password = nim (20230140125)
        if ("admin".equals(username) && "20230140125".equals(password)) {
            session.setAttribute("userSession", "Jihaduttolibinn");
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Username atau Password salah bro!");
            return "login";
        }
    }

    @GetMapping("/home")
    public String homePage(HttpSession session, Model model) {
        if (session.getAttribute("userSession") == null) {
            return "redirect:/login";
        }
        model.addAttribute("namaUser", session.getAttribute("userSession"));
        return "home";
    }

    @GetMapping("/form")
    public String formPage(HttpSession session) {
        if (session.getAttribute("userSession") == null) {
            return "redirect:/login";
        }
        return "form";
    }

    @PostMapping("/result")
    public String resultPage(@RequestParam String nama, 
                             @RequestParam String nim, 
                             @RequestParam String jenisKelamin, 
                             Model model) {
        User user = new User(nama, nim, jenisKelamin);
        userList.add(user); // Simpan ke list
        model.addAttribute("user", user);
        return "result";
    }

    @GetMapping("/list")
    public String listData(HttpSession session, Model model) {
        if (session.getAttribute("userSession") == null) {
            return "redirect:/login";
        }
        model.addAttribute("users", userList);
        return "list";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
