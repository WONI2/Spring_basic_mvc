package com.spring.mvc.chap05.repository;

import com.spring.mvc.chap05.dto.page.Page;
import com.spring.mvc.chap05.dto.page.Search;
import com.spring.mvc.chap05.entity.Board;
import com.spring.mvc.chap05.entity.Reply;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ReplyMapperTest {

    @Autowired
    BoardMapper boardMapper;
    @Autowired
    ReplyMapper replyMapper;

//    @Test
//    @DisplayName("게시물 300개 등록, 각 게시물에 랜덤으로 1000개의 댓글을 나눠서 등록")
//    void bulkInsertTest(){
//        for (int i = 1; i <= 300; i++) {
//            Board b = Board.builder()
//                    .title("게시물 입력하기" + i)
//                    .content("내용은 없음" + i)
//                    .build();
//            boardMapper.save(b);
//        }
//        assertEquals(300, boardMapper.count(new Search()));
//
//        for (int i = 1; i <= 1000; i++) {
//            Reply r = Reply.builder()
//                    .replyWriter("콩콩이" + i)
//                    .replyText("새요 세요" + i)
//                    .boardNo((long) (Math.random() * 300 + 1))
//                    .build();
//            replyMapper.save(r);
//        }
//
//    }


    @Test
    @DisplayName("댓글을 3번 게시물에 등록하면 3번 게시물의 총 댓글 수는 5개 여야 한다")
    @Transactional
    @Rollback //테스트끝난후 롤백해라 테스트를 돌릴때마다 save때문에 값이 변경
    void saveTest() {
        //given
        long boardNo = 3L;
        Reply newReply = Reply.builder()
                .replyText("3번게시물에 등록하는 3번째 댓글 33333")
                .replyWriter("3번게시물에 3번째 댓글 등록하는 사람")
                .boardNo(boardNo)
                .build();
        //when
        boolean flag = replyMapper.save(newReply);
        //then
        assertTrue(flag);
        assertEquals(5, replyMapper.count(boardNo));

    }

    @Test
    @DisplayName("댓글번호가 1001번인 것을 삭제하면 3번 게시물의 총 댓글 수가 3이어야 한다")
    @Transactional @Rollback
    void deleteTest() {
        long replyNo = 1001L;
        long boardNo= 3L;

        boolean flag = replyMapper.deleteOne(replyNo);

        assertTrue(flag);
        assertEquals(3,replyMapper.count(boardNo));
    }

    @Test
    @DisplayName("댓글번호가 1002번인 댓글을 수정하면 댓글내용이 3번 게시물의 4번째 댓글이 된다")
    @Transactional @Rollback
    void modifyTest() {
        long replyNo = 1002L;
        long boardNo= 3L;

        Reply b = Reply.builder()
                .replyNo(replyNo)
                .replyText("3번 게시물의 4번째 댓글")
                .build();
        boolean flag = replyMapper.modify(b);

        assertTrue(flag);


    }

    @Test
    @DisplayName("게시물 3번의 댓글의 수는 4개 이다")
    void countTest() {
        long boardNo = 3L;

        int c = replyMapper.count(boardNo);

        assertEquals(4, c);

    }
    @Test
    @DisplayName("댓글 번호 154번의 작성자는 콩콩이154이고 게시글 3번일 것이다")
    void findOneTest() {
        long replyNo = 154L;


        Reply num = replyMapper.findOne(replyNo);

        assertEquals("콩콩이154",num.getReplyWriter());
        assertEquals(3,num.getBoardNo());
    }

    @Test
    @DisplayName("게시물 3번의 댓글목록을 조회하면 댓글 번호가 154, 298, 1001,1002 일 것이다")
    void findAllTest() {
        long boardNo = 3L;

        List<Reply> list = replyMapper.findAll(boardNo, new Page());

        assertEquals(154, list.get(0).getReplyNo());
        assertEquals(298, list.get(1).getReplyNo());
        assertEquals(1001, list.get(2).getReplyNo());



    }

}