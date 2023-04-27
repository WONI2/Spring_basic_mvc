package com.spring.mvc.chap04.entity;

import com.spring.mvc.chap04.DTO.ScoreRequestDTO;
import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;

@Setter @Getter @ToString @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
@Builder
public class Score {

    private String name;
    private int kor, eng, math;
    private int stuNum; //학번
    private  int total;
    private double average;
    private Grade grade; //학점

    public Score(ScoreRequestDTO dto) {
        this.name =dto.getName();
        this.kor = dto.getKor();
        this.eng = dto.getEng();
        this.math = dto.getMath();
        calcTotalAndAvg();
        calcGrade();
    }

    public Score(ResultSet rs) throws SQLException {
        this.stuNum =rs.getInt("stu_num");
        this.name = rs.getString("name");
        this.kor = rs.getInt("kor");
        this.eng = rs.getInt("eng");
        this.math = rs.getInt("math");
        this.total = rs.getInt("total");
        this.average = rs.getDouble("average");
        this.grade = Grade.valueOf(rs.getString("grade"));
    }

    private void calcGrade() {
        if(average >= 90) {
            this.grade =Grade.A;
        } else if (average >= 80) {
            this.grade = Grade.B;
        } else if (average >= 70) {
            this.grade =Grade.C;
        } else {
            this.grade =Grade.D;
        }

    }

    private void calcTotalAndAvg() {
        this.total = kor+eng+math;
        this.average =Math.ceil(total/3.0* 100) / 100.0;

    }
    public void setDto(ScoreRequestDTO dto) {
        this.name =dto.getName();
        this.kor = dto.getKor();
        this.eng = dto.getEng();
        this.math = dto.getMath();
        calcTotalAndAvg();
        calcGrade();
    }


}
