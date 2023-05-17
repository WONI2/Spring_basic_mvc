package com.spring.mvc.chap05.controller;

import com.spring.mvc.chap05.dto.request.LoginRequestDTO;
import com.spring.mvc.chap05.dto.request.SignUpRequestDTO;
import com.spring.mvc.chap05.service.LoginResult;
import com.spring.mvc.chap05.service.MemberService;
import com.spring.mvc.util.LoginUtil;
import com.spring.mvc.util.upload.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    @Value("${file.upload.rootpath}")
    private String rootpath;
    private final MemberService memberService;


    //회원가입 요청

    //    회원가입 양식 요청
    @GetMapping("/sign-up")
    public String signUp() {
        log.info("/members/sign-up GET -forwarding to jsp");
        return "members/sign-up";
    }

    //    회원가입 처리 요청
//    @PostMapping("/sign-up")
//    public String signUp(SignUpRequestDTO dto) {
//        log.info("/members/sign-up POST - {}", dto);
//        log.info("프로필 사진 이름 : {}", dto.getProfileImage().getOriginalFilename());
//
////        실제 로컬 스토리지에 파일을 업로드 하는 로직.
//        String savePath = FileUtil.uploadFile(dto.getProfileImage(), rootpath);
//
//        boolean flag = memberService.join(dto, savePath);
////         return "redirect:/board/list";
//        return "redirect:/members/sign-in";
//
//    }
    // 회원가입 처리 요청
    @PostMapping("/sign-up")
    public String signUp(SignUpRequestDTO dto) {
        log.info("/members/sign-up POST ! - {}", dto);

        MultipartFile profileImage = dto.getProfileImage();
        log.info("프로필사진 이름: {}", profileImage.getOriginalFilename());

        String savePath = null;
        if (!profileImage.isEmpty()) { //첨부파일이 있을때만 사진을 저장. db에도 null로 저장됨.
            // 실제 로컬 스토리지에 파일을 업로드하는 로직
            savePath = FileUtil.uploadFile(profileImage, rootpath);
        }

        boolean flag = memberService.join(dto, savePath);

        return "redirect:/members/sign-in";
    }



    //아이디, 이메일 중복 검사
// 비동기 요청 처리
    @GetMapping("/check")
    @ResponseBody //check만 비동기 처리할 때
    public ResponseEntity<?> check(String type, String keyword) {
        log.info("/members/check?type={}&keyword={} ASYNC GET", type, keyword);

        boolean flag = memberService.checkSignUpValue(type, keyword);

        return ResponseEntity.ok().body(flag);

    }

    //    로그인 화면 요청
    @GetMapping("/sign-in")
    public String signIn(HttpServletRequest request) {
        log.info("/members/sign-in GET -forwarding to jsp");

//        요청정보 헤더 안에는 referer라는 키가 있는데, 이 값은 이페이지로
//        들어올 때 어디에서 왔는지에 대한 URI 정보가 기록되어 있음.
        String referer = request.getHeader("Referer");
        log.info("referer : {}", referer);

        return "members/sign-in";
    }

    //    로그인 검증 요청
    @PostMapping("/sign-in")
    public String signIn(LoginRequestDTO dto
            , RedirectAttributes ra
            , HttpServletResponse response
            , HttpServletRequest request) {
        log.info("/members/sign-in POST - {}", dto);
        LoginResult result = memberService.authenticate(dto, request.getSession(), response);

        //로그인 성공시
        if (result == LoginResult.SUCCESS) {
//            세션방식
//            서버에서 세션에 로그인 정보를 저장
            memberService.maintainLoginState(request.getSession(), dto.getAccount());


            //session에는 많은 정보를 담을 수 있고, 가지고 있음
//            클라이언트가 아니라 서버에 저장되는 것(쿠키는 클라이언트에 저장되지만! )


////        쿠키 만들기
//            Cookie loginCookie = new Cookie("login",""); //value는 string만 가능
////            쿠키 세팅
//            loginCookie.setPath("/"); //쿠키 유효범위 설정
//            loginCookie.setMaxAge(60*60*24); //유효시간을 반드시 설정. 초단위.
////            쿠키를 응답시에 실어서 클라이언트에게 전송
//            response.addCookie(loginCookie);

            return "redirect:/";
        }
        ra.addFlashAttribute("msg", result);// flashattribute : 1회성으로 사용
//       return을 redirect로 작성하면 원래대로 돌아가기 때문에, 가지고 있던 메세지가 전달되지 않음
//    model의 데이터가 jsp로 전달되지 않음. 그래서 MODEL이 아닌 RedirectAttributes를사용하여
//        메세지를 살려서 보낼 수 있음
//        로그인 실패시 다시 로그인 화면
        return "redirect:/members/sign-in";

    }

    //    로그아웃 요청 처리
    @GetMapping("/sign-out")
    public String signOut(HttpServletRequest request,
                          HttpServletResponse response) {
//    스프링에서는 HttpSession 을 받아올 수 있음
        HttpSession session = request.getSession();
//로그인 중인지 확인
        if (LoginUtil.isLogin(session)) {
//            자동로그인 상태라면 해제한다
            if (LoginUtil.isAutoLogin(request)) {
                memberService.autoLoginClear(request, response);
            }

//    세션에서 로그인 정보 제거
        session.removeAttribute("login");

//        세션을 아예 초기화(세션 만료 시간)
        session.invalidate(); //다음 로그인을 위해 새로운 세션을 만들어 냄
            return "redirect:/";

    }
        return "redirect:/members/sign-in";
}

}
