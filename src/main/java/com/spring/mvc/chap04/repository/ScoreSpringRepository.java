package com.spring.mvc.chap04.repository;

import com.spring.mvc.chap04.entity.Score;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@RequiredArgsConstructor
@Repository("spring")
public class ScoreSpringRepository implements ScoreRepository{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Score> findAll() {

        return null;
    }

    @Override
    public List<Score> findAll(String sort) {
        String sql = "SELECT * FROM score";
        switch(sort) {
            case "stuNum" :
                sql += " ORDER BY STU_NUM";
                break;
            case "name" :
                sql += " ORDER BY name";
                break;
            case "avg" :
                sql += " ORDER BY average";
                break;
        }
        List<Score> scoreList = jdbcTemplate.query(sql, (rs, n) -> new Score(rs));

        return scoreList;
    }


    @Override
    public boolean save(Score score) {
        String sql = "INSERT INTO score " +
                "(name, kor, eng, math, total, average, grade) " +
                "VALUES(?,?,?,?,?,?,?)";
        int update = jdbcTemplate.update(sql, score.getName(), score.getKor(),
                score.getEng(), score.getMath(), score.getTotal(),
                score.getAverage(), String.valueOf(score.getGrade()));
        return update == 1;
    }

    @Override
    public boolean deleteByStuNum(int stuNum) {
        String sql = "DELETE FROM score WHERE STU_NUM=? ";
        int result = jdbcTemplate.update(sql, stuNum);

        return result == 1;
    }

    @Override
    public Score findByStuNum(int stuNum) {
        String sql = "SELECT * FROM score WHERE STU_NUM=?";
       return  jdbcTemplate.queryForObject(sql, (rs, n) -> new Score(rs), stuNum);
    }
}
