package com.spring.mvc.chap05.dto;


import com.spring.mvc.chap05.entity.Reply;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter @Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyPutRequestDTO {
    @NotNull
    @Min(0) @Max(Long.MAX_VALUE) //반드시 정수입력
    private Long bno;
    @NotNull
    @Min(0) @Max(Long.MAX_VALUE) //반드시 정수입력
    private Long replyNo;
    @NotBlank
    private String text;


    public Reply toEntity() {
        return Reply.builder()
                .boardNo(this.bno)
                .replyNo(this.replyNo)
                .replyText(this.text)
                .build();
    }



}
