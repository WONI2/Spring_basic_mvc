package com.spring.mvc.chap05.dto;


import com.spring.mvc.chap05.dto.page.PageMaker;
import lombok.*;
import java.util.List;

@Setter @Getter @ToString @NoArgsConstructor @AllArgsConstructor @Builder
public class ReplyListResponseDTO {


    private int count; //총 댓글 수
    private PageMaker pageInfo;
    private List<ReplyDetailResponseDTO> replies;
}
