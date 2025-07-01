package SANTA.backend.service;

import SANTA.backend.dto.JoinDTO;
import SANTA.backend.entity.UserEntity;
import SANTA.backend.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserRepository userRepository; //->여기 질문
    private final BCryptPasswordEncoder bCryptPasswordEncoder; //config에 있는 encode방식

    public JoinService(UserRepository userRepository,BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository=userRepository; //초기화 과정
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }

    public void joinProcess(JoinDTO joinDTO){
        String userId= joinDTO.getUserId();
        String username=joinDTO.getUsername();
        String password=joinDTO.getPassword();
        String nickname=joinDTO.getNickname();
        int age=joinDTO.getAge();

        Boolean isExist=userRepository.existsByNickname(nickname);
        Boolean idisExist=userRepository.existsByuserId(userId);

        if(isExist){
            return;
        }
        //nickname, age설정
        UserEntity data=new UserEntity();
        data.setUsername(username);
        data.setNickname(nickname);
        data.setAge(age);
        data.setUserId(userId);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setRole("ROLE_ADMIN"); //이부분 모든 사람 어드민 설정

        userRepository.save(data);//repository에서 db로의 데이터 저장??
    }
}
