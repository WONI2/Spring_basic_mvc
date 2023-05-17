package com.spring.mvc.chap05.controller;

import com.spring.mvc.chap05.dto.response.BoardListResponseDTO;
import com.spring.mvc.chap05.dto.request.BoardWriteRequestDTO;
import com.spring.mvc.chap05.dto.page.PageMaker;
import com.spring.mvc.chap05.dto.page.Search;
import com.spring.mvc.chap05.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
@Slf4j
public class BoardController {

    private final BoardService boardService;

    // 목록 조회 요청
    @GetMapping("/list")
    public String list(Search page, Model model
    , HttpServletRequest request) {
        boolean flag = false;

//        세션을 확인
//        Object login = request.getSession().getAttribute("login");
//        if(login != null) {
//            flag = true;
//
//        }


        //쿠키를 확인
//        Cookie[] cookies = request.getCookies();
//        for (Cookie cookie : cookies) {
//            if(cookie.getName().equals("login")){
//                flag =true;
//                break;
//            }
//        }
//        if(!flag) return "redirect:/members/sign-in";

        log.info("/board/list : GET");
       log.info("page: {}", page);
        List<BoardListResponseDTO> responseDTO
                = boardService.getList(page);
//        페이지 알고리즘 작동

        PageMaker maker = new PageMaker(page, boardService.getCount(page));
        model.addAttribute("bList", responseDTO);
        model.addAttribute("maker", maker);
        model.addAttribute("s",page);

        return "chap05/list";
    }

    // 글쓰기 화면 조회 요청
    @GetMapping("/write") //목록은 볼 수 있지만 글은 회원만 작성하도록 만들기
    public String write(HttpSession session) {
//        if(!LoginUtil.isLogin(session)) {
//            return "redirect:/members/sign-in";
//        }
//        인터셉터로 만들어 보기

        System.out.println("/board/write : GET");

        return "chap05/write";
    }

    // 글 등록 요청 처리
    @PostMapping("/write")
    public String write(BoardWriteRequestDTO dto, HttpSession session) {
        System.out.println("/board/write : POST");
        boardService.register(dto, session);
        return "redirect:/board/list";
    }

    // 글 삭제 요청 처리
    @GetMapping("/delete")
    public String delete(int bno) {
        System.out.println("/board/delete : GET");
        boardService.delete(bno);
        return "redirect:/board/list";
    }

    // 글 상세 조회 요청
    @GetMapping("/detail")
    public String detail(@ModelAttribute("s") Search search, int bno, Model model) {
        System.out.println("/board/detail : GET");
        model.addAttribute("b", boardService.getDetail(bno));
//        model.addAttribute("s",search); 바로 전달하니까 @ModelAttribute 사용해서 보내기 가능.
        return "chap05/detail";
    }

}
