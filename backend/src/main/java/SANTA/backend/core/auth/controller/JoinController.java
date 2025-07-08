package SANTA.backend.core.auth.controller;

import SANTA.backend.core.auth.dto.JoinDTO;
import SANTA.backend.core.auth.dto.JoinResponseDTO;
import SANTA.backend.core.auth.service.JoinService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class JoinController {

    private final JoinService joinService;


    public JoinController(JoinService joinService){
        this.joinService=joinService;
    }
    @PostMapping("/api/auth/sign-up")
    public ResponseEntity<JoinResponseDTO> joinProcess(@RequestBody JoinDTO joinDTO) throws IllegalAccessException{
        JoinResponseDTO joinUser = joinService.join(joinDTO.getUsername(),joinDTO.getPassword(),joinDTO.getNickname(),joinDTO.getAge());
        return ResponseEntity.ok().body(joinUser);
    }

    @GetMapping("/form")
    public String loginForm(){
        return "form";
    }
    //id를 어떻게 뺴와야 할지 몰겠음;;
    //login도 login 하나를 만들어서 제어할 수 있게 해서 하자
}
