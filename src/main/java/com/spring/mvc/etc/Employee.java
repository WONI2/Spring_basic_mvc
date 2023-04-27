package com.spring.mvc.etc;

import lombok.*;

import java.time.LocalDate;


@Setter @Getter @ToString @NoArgsConstructor @AllArgsConstructor
public class Employee {

    private int empId;

    private String empName;
    private String position;

    private LocalDate hireDate;


//    빌더 패턴 구현
//    1. 내부 클래스를 외부와 같은 스펙으로 만듦

    public static  class  EmployeeBuilder {
        private int empId;

        private String empName;
        private String position;

        private LocalDate hireDate;
// 2. 내부 클래스 생성자를 private으로 제한해서 Employee 클래스외부에서 생성불가능 하게 함
    private  EmployeeBuilder() {

    }
//3. 각 필드별로 빌더 메서드들을 구현
        public EmployeeBuilder empName(int empName) {
        this.empId = empId;
        return this;
        }
        public EmployeeBuilder empName(String empName) {
            this.empName = empName;
            return this;
        }
        public EmployeeBuilder position(String position) {
            this.position = position;
            return this;
        }
        public EmployeeBuilder hireDate(LocalDate hireDate) {
            this.hireDate = hireDate;
            return this;
        }

        public Employee build() {
        return  new Employee(empId, empName, position, hireDate);
        }
    }
// 빌더 호출 메서드
    public  static EmployeeBuilder builder() {
        return new EmployeeBuilder();
    }

}
