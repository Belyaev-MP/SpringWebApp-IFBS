package my.self.springapp.domain;

import java.sql.ResultSet;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TestService {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void makeTest() {

        String sql = "SELECT * from car_model";

        List<String> res = jdbcTemplate.query(sql, (ResultSet rs, int rowNum) -> {
            return rs.getInt("id") + " " + rs.getString("model");
        });

        System.out.println("Result:");
        for(String r: res) {
            System.out.println(r);
        }
    }

}
