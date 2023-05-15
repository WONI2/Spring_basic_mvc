package com.spring.mvc.chap05.api;

import com.spring.mvc.chap05.dto.response.ReplyListResponseDTO;
import com.spring.mvc.chap05.dto.request.ReplyPostRequestDTO;
import com.spring.mvc.chap05.dto.request.ReplyPutRequestDTO;
import com.spring.mvc.chap05.dto.page.Page;
import com.spring.mvc.chap05.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/replies")
@Slf4j
//클라이언트의 접근을 어떤 앱에서만 허용할 것인가
@CrossOrigin(origins = {"http://127.0.0.1:5500"})
public class ReplyController {

    private final ReplyService replyService;

    //    댓글 목록 조회 요청
    //URL : /api/v1/replies/3/page/1
    @GetMapping("/{boardNo}/page/{pageNo}")
    public ResponseEntity<?> list(@PathVariable long boardNo,
                                  @PathVariable int pageNo) {
        log.info("/api/v1/replies/{}/page/{} : GET ", boardNo, pageNo);

        Page page = new Page();
        page.setPageNo(pageNo);
        page.setAmount(10);
      ReplyListResponseDTO replyList = (ReplyListResponseDTO) replyService.getList(boardNo, page);

        return ResponseEntity.ok().body(replyList);
    }

//    댓글 등록 요청
    @PostMapping
    public ResponseEntity<?> create(
            @Validated //requestDTO에서 작성한 내용 검증 ,  BindingResult 는 검증결과를 가진 객체
          @RequestBody ReplyPostRequestDTO dto,//@RequestBody : 요청 메시지 바디에 JSON으로 보내주세요
            BindingResult result) {
//        입력값 검증에 걸리면 4xx 상태코드 리턴
        if(result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.toString());
        }

        log.info("/api/v1/replies : POST");
        log.info("param : {} ", dto); //잘 넘어오는지 서버에서 확인해보기 원래는 debug나 tracer에서 받기

//        서비스에 비즈니스로직 처리 위임
        try {
            ReplyListResponseDTO registerDTO = replyService.register(dto);
            return ResponseEntity.ok().body(registerDTO);
        } catch (Exception e) {
//            문제발생 상황을 클라이언트에게 전달throw new RuntimeException(e);
//            e.getMessage() : service에서 작성한 "댓글 저장에 실패했지롱" 부분
            log.warn("500 Status code response", e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

//    성공시 클라이언트에 응답하기 , return이니까 try 아래에 넣어도 됨.
//        return ResponseEntity.ok().body(registerDTO);
    }


//   댓글 삭제 요청
    @DeleteMapping("/{replyNo}")
public ResponseEntity<?> remove(@PathVariable(required = false) Long replyNo) {
        if(replyNo == null) {
            return ResponseEntity.badRequest().body("댓글번호를 확인해주세요");

        }
        log.info("/api/v1/replies/{} DELETE", replyNo);
        try {
            ReplyListResponseDTO responseDTO = replyService.delete(replyNo);
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }
//    @PutMapping("/{replyNo}")
    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH})
//    public ResponseEntity<?> modify(ReplyPutRequestDTO dto) {
//
//
//        try {
//            ReplyListResponseDTO responseDTO = replyService.modify(dto);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        return null;
//    }
    public ResponseEntity<?> modify(
            @Validated @RequestBody ReplyPutRequestDTO dto
            , BindingResult result
    ) {

        if (result.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(result.toString());
        }

        log.info("/api/v1/replies PUT!");
        try {
            ReplyListResponseDTO responseDTO = replyService.modify(dto);
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            log.warn("500 status code! caused by: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


}
