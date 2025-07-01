package SANTA.backend.controller;

import SANTA.backend.dto.JoinDTO;
import SANTA.backend.service.JoinService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@ResponseBody
public class JoinController {

    private final JoinService joinService;
    public JoinController(JoinService joinService){
        this.joinService=joinService;
    }
    @PostMapping("/api/auth/sign-up")
    public Map<String, String> joinProcess(@RequestBody JoinDTO joinDTO) {
        joinService.joinProcess(joinDTO);
        return Map.of("userId", joinDTO.getUserId(), "username", joinDTO.getUsername(),"nickname", joinDTO.getNickname());
    }
    //id를 어떻게 뺴와야 할지 몰겠음;;
    //login도 login 하나를 만들어서 제어할 수 있게 해서 하자
}
