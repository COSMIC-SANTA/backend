package SANTA.backend.core.auth.controller;

import SANTA.backend.core.auth.dto.JoinDTO;
import SANTA.backend.core.auth.dto.JoinResponseDTO;
import SANTA.backend.core.auth.service.CustomUserDetails;
import SANTA.backend.core.auth.service.JoinService;
import SANTA.backend.global.jwt.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/api/auth/sign-up")
    public ResponseEntity<JoinResponseDTO> joinProcess(@RequestBody JoinDTO joinDTO) throws IllegalAccessException{
        JoinResponseDTO joinUser = joinService.join(joinDTO.getUsername(),joinDTO.getPassword(),joinDTO.getNickname(),joinDTO.getAge());
        return ResponseEntity.ok().body(joinUser);
    }

    @PostMapping("/api/auth/sign-out")
    public ResponseEntity<Void> logOut(@AuthenticationPrincipal CustomUserDetails customUserDetails, HttpServletRequest request){
        String token = extractAccessToken(request);
        joinService.logOut(customUserDetails.getUserId(),token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/form")
    public String loginForm(){
        return "form";
    }
    //id를 어떻게 뺴와야 할지 몰겠음;;
    //login도 login 하나를 만들어서 제어할 수 있게 해서 하자

    private String extractAccessToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return (authHeader != null && authHeader.startsWith("Bearer "))
                ? authHeader.split(" ")[1]
                : null;
    }
}
