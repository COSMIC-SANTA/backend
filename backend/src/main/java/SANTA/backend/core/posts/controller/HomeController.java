package SANTA.backend.core.posts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/community/board")
public class HomeController {
    @GetMapping("/home")
    public String Index(){
        return "index";
    }
}
