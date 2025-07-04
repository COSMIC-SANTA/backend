package SANTA.backend.core.service;

import SANTA.backend.core.domain.User;
import SANTA.backend.core.dto.JoinDTO;
import SANTA.backend.core.dto.JoinResponseDTO;
import SANTA.backend.core.entity.UserEntity;
import SANTA.backend.core.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder; //config에 있는 encode방식

    public JoinService(UserRepository userRepository,BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository=userRepository; //초기화 과정
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User findByNickName(String nickname){
        return userRepository.findByUsername(nickname);
    }

    public JoinResponseDTO join(String username, String password, String nickname, int age)throws IllegalAccessException{
        if(findByUsername(username)!=null)
            throw new IllegalAccessException("이미 사용중인 아이디입니다.");

        User user=User.registerUser(username,password,nickname,age);

        User joinUser=userRepository.join(user);
        return new JoinResponseDTO(joinUser);//repository에서 db로의 데이터 저장??
    }
}
