package com.spring.mvc.chap05.repository;

import com.spring.mvc.chap05.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {
    //회원가입기능
    boolean save(Member member);

    //회원정보조회
    Member findMember(String account);

    //중복체크(account, email 검사) 기능
    int isDuplicate(String type, String keyword);

//  안될 경우
//   ( @Param("type") String type, @Param("keyword") String keyword)


}
