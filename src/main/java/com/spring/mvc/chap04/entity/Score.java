package com.spring.mvc.chap04.entity;

import com.spring.mvc.chap04.DTO.ScoreRequestDTO;
import lombok.*;

@Setter @Getter @ToString @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
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
