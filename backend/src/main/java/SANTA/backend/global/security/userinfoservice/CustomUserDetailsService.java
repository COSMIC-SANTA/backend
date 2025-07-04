package SANTA.backend.global.security.userinfoservice;

import SANTA.backend.core.domain.User;
import SANTA.backend.core.repository.UserRepository;
import SANTA.backend.global.security.userinfo.CustomUserDetails;
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
        User userData =userRepository.findByUsername(username);
        if(userData!=null){
            //UserDetails에 담아서 return하면 AutneticationManager가 검증 함
            return CustomUserDetails.create(userData);
        }
        return null;
    }
}
