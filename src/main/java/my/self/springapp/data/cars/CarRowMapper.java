package my.self.springapp.data.cars;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import my.self.springapp.domain.model.CarBrand;
import my.self.springapp.domain.model.CarModel;


public class CarRowMapper implements RowMapper<CarModel> {

    @Override
    public CarModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        CarModel carModel = new CarModel();
        CarBrand carBrand = new CarBrand(rs.getInt("car_brand_id"), rs.getString("brandName"));

        carModel.setId(rs.getInt("id"));
        carModel.setBrand(carBrand);
        carModel.setModel(rs.getString("model"));
        carModel.setProduction(rs.getDate("production"));

        return carModel;
    }

}
