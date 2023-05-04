package com.spring.mvc.chap05.dto;

import com.spring.mvc.chap05.entity.Reply;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//request에서 setter와 noarg~는 필수
@Setter @Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder

//RequestDTO는 클라이언트가 제대로 값을 보냈는지 검증해야 함

public class ReplyPostRequestDTO {

//    필드명은 클라이언트 개발자와 상의 할 것, entity의 필드명과는 다름. entity는 DB와 상호작용하기 때문에
//    DB와 이름을 맞춰줘야 하지만 여기 DTO는 클라이언트에게 받는 내용이라서 클라이언트와 상의해야 함
    @NotBlank //필수값
    private String text; //댓글내용
    @NotBlank //필수값
    @Size(min = 2, max = 8)
    private String author; // 댓글 작성자명
    @NotNull //필수값
    private Long bno; // 원본 글 번호
/*
* @NotNull : null을 허용하지 않음
* @NotBlank : null + 빈문자열 을 허용하지 않음
*
* */

//dto를 entity로 바꿔서 리턴하는 메서드

    public Reply toEntity() {
        return Reply.builder()
                .replyText(this.text)
                .replyWriter(this.author)
                .boardNo(this.bno)
                .build();
    }

}
