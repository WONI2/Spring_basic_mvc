package com.spring.mvc.spring_jdbc;

import com.spring.mvc.jdbc.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PersonSpringRepository {
//    스프링jdbc 활용 - 데이터베이스 접속 설정 정보를 설정파일(application.properties)을 통해 불러와서 사용

    private final JdbcTemplate jdbcTemplate;

//    저장기능
    public void savePerson(Person p) {
        String sql = "INSERT INTO person (person_name, person_age) VALUES(?,?)";
        jdbcTemplate.update(sql, p.getPersonName(), p.getPersonAge());
    }
    public void removePerson(long id) {
        String sql = "DELETE FROM person WHERE id = ?";
        jdbcTemplate.update(sql,id);
    }

    public boolean modifyPerson(Person p) {
        String sql = "UPDATE SET person_name=?, person_age=? WHERE id =? ";
        int result = jdbcTemplate.update(sql, p.getPersonName(), p.getPersonAge(), p.getId());

        return result == 1;

    }

//    전체조회 기능
    public List<Person> findAll() {
        String sql = "SELECT * FROM person";
        List<Person> personList = jdbcTemplate.query(sql, new RowMapper() {
            @Override
            public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Person(rs);

//                p.setId(rs.getLong("id"));
//                p.setPersonName(rs.getString("person_name"));
//                p.setPersonAge(rs.getInt("person_age"));
//
            }

        });
        return personList;
    }

    public Person findOne(long id) {
        String sql = "SELECT * FROM person WHERE id =?";
        return jdbcTemplate.queryForObject(sql, (rs, n) -> new Person(rs) ,id);
                                        //(SQL, ROW, ?에 채울 것) 순서로 작성
    }

}
