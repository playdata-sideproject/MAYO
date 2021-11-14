package kr.pe.mayo.controller;

import kr.pe.mayo.config.oauth.PrincipalDetails;
import kr.pe.mayo.domain.User;
import kr.pe.mayo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private HttpSession session;


    @GetMapping("/login-success")
    public String checkFirstJoin(@AuthenticationPrincipal PrincipalDetails userDetails){

        // 뷰 분기
        User user = userService.checkFirstJoin(userDetails.getUser().getUsername());
        String phone = user.getPhone();
        if (phone == null){  // 처음 회원가입하는 사용자라면 ... 근데 이걸 phone 컬럼으로 검증하는게 논리에 맞나...? 무튼 추가정보 입력 폼으로 이동
            return "register";
        }

        // 다시 방문해서 로그인하는 사용자라면 이미 추가정보도 다 있음. HttpSession에 저장
        session.setAttribute("user", user);
        return "redirect:/";
    }


    @PostMapping("/register")
    public String register(HttpSession session, String phone, String birth, String school){
        User user = userService.register(phone, birth, school);
        session.setAttribute("user", user);
        return "redirect:/";
    }

}
