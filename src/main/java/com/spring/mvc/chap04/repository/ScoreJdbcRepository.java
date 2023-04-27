package com.spring.mvc.chap04.repository;

import com.spring.mvc.chap04.entity.Score;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository("jdbc")
//이름을 변환 해줘서 할 것
public class ScoreJdbcRepository implements ScoreRepository {
    private String url = "jdbc:mariadb://localhost:3306/spring";
    private String username = "root";
    private String password = "1234";

    public ScoreJdbcRepository() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Score> findAll() {
        return null;
    }

    public List<Score> findAll(String sort) {
        List<Score> scoreList = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT * FROM score";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                scoreList.add(new Score(rs));
            }

        }catch (Exception e){

        }
        return scoreList;
    }

    @Override
    public boolean save(Score score) {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            conn.setAutoCommit(false);
            String sql = "INSERT INTO score (name , kor, eng, math, total, average, grade )" +
                    "VALUES(?,?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,score.getName());
            pstmt.setInt(2, score.getKor());
            pstmt.setInt(3,score.getEng());
            pstmt.setInt(4, score.getMath());
            pstmt.setInt(5,score.getTotal());
            pstmt.setDouble(6,score.getAverage());
            pstmt.setString(7,String.valueOf(score.getGrade()));

            int result = pstmt.executeUpdate();
            if(result == 1) {
                conn.commit();
                return true;
            }
            conn.rollback();
            return false;


        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean deleteByStuNum(int stuNum) {
        List<Score> scoreList = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "DELETE FROM score WHERE STU_NUM=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, stuNum);
            int result = pstmt.executeUpdate();

            if(result == 1 ) {

                scoreList.remove(stuNum);  return true;
            }

        }catch(Exception e) {

        }

        return false;

    }

    @Override
    public Score findByStuNum(int stuNum) {
       List<Score> scoreList = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT * FROM score WHERE STU_NUM=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, stuNum);

            ResultSet rs = pstmt.executeQuery();

           if(rs.next()) {
               return new Score(rs);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
        return null; //조회결과가 없기 때문에 null.

    }
}
