package SANTA.backend.service;

import SANTA.backend.dto.CustomUserDetails;
import SANTA.backend.entity.UserEntity;
import SANTA.backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    //DB에 접근할 repository 객체
    private  final UserRepository userRepository;

    public  CustomUserDetailsService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //DB에서 조회
        UserEntity userData =userRepository.findByUsername(username);
        if(userData!=null){
            //UserDetails에 담아서 return하면 AutneticationManager가 검증 함
            return  new CustomUserDetails(userData);
        }
        return null;
    }
}
