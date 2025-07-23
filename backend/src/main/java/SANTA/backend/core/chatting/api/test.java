package SANTA.backend.core.chatting.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class test {

    @GetMapping("/chattingTest")
    public String chattingTest(){
        return "chattingTest";
    }
}
