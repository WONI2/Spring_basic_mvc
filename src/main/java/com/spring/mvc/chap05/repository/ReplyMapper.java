package com.spring.mvc.chap05.repository;

import com.spring.mvc.chap05.dto.page.Page;
import com.spring.mvc.chap05.entity.Reply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReplyMapper {

//  댓글 등록
    boolean save(Reply reply);

//    댓글 수정
    boolean modify(Reply reply);

//    댓글 삭제
    boolean deleteOne(long replyNo);

//    댓글 개별 조회 -- 수정할 때 쓰기
    Reply findOne(long replyNo);

//    댓글 목록 조회
    List<Reply> findAll(@Param("bn") long boardNo,
                        @Param("p") Page page); //2가지를 받을 경우 겹치는 필드가 없더라도
                                        // Param으로 별칭을 지어줄 것

//    댓글 수 조회
    int count(long boardNo);

}
