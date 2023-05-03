package com.spring.mvc.chap05.entity;

import lombok.*;

import java.time.LocalDate;

@Setter @Getter @ToString @EqualsAndHashCode @NoArgsConstructor @AllArgsConstructor
@Builder
public class Reply {
    private long replyNo;
    private String replyText;
    private String replyWriter;
    private LocalDate replyDate;
    private long boardNo;





}
