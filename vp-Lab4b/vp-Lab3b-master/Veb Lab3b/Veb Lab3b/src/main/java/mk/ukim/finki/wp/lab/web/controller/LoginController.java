package mk.ukim.finki.wp.lab.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.wp.lab.model.User;
import mk.ukim.finki.wp.lab.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String getLoginPage(Model model) {
        model.addAttribute("bodyContent", "login");
        return "master-template";
    }

    @PostMapping
    public String login(HttpServletRequest request, Model model) {
        User user = null;
        try {
            user = this.authService.login(request.getParameter("username"), request.getParameter("password"));
            request.getSession().setAttribute("user", user);
            return "redirect:/home";
        } catch (RuntimeException ex) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("bodyContent", "login");
            return "master-template";
        }
    }

}
