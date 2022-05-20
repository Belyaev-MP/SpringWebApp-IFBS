package my.self.springapp.data.cars;

import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import my.self.springapp.domain.model.CarModel;


@Repository
public class CarStorageMysqlDb implements CarStorage {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<CarModel> getAllCarModels(String pattern) {
        List<CarModel> carList = null;

        StringBuilder sqlQuery = new StringBuilder("SELECT car_model.id, car_brand_id, "
                + "car_model.model, car_model.production, car_brand.name as brandName "
                + " from car_model "
                + " left join car_brand"
                + " on car_model.car_brand_id = car_brand.id ");

        String sqlPattern = null;

        if (pattern != null) {
            sqlQuery.append(" WHERE car_model.model LIKE ?");
            sqlPattern = "%" + pattern + "%";
            carList = jdbcTemplate.query(sqlQuery.toString(), new CarRowMapper(), sqlPattern);
        } else {
            carList = jdbcTemplate.query(sqlQuery.toString(), new CarRowMapper());
        }

        return carList;
    }

    @Override
    public CarModel loadById(Long carId) {
        StringBuilder sqlQuery =
                new StringBuilder("SELECT car_model.id, car_brand_id, car_model.model, "
                        + "car_model.production, car_brand.name as brandName "
                        + "from car_model "
                        + "left join car_brand on car_model.car_brand_id = car_brand.id WHERE car_model.id = ?");

        return jdbcTemplate.queryForObject(sqlQuery.toString(), new CarRowMapper(), carId);
    }

    @Override
    public void saveById(CarModel carModel) {
        StringBuilder sqlQuery =
                new StringBuilder("update car_model set car_model.model = ?, "
                        + "car_model.production = ? "
                        + "where car_model.id = ?");

        jdbcTemplate.update(sqlQuery.toString(), carModel.getModel(), carModel.getProduction(), carModel.getId() );
    }

    @Override
    public Page<CarModel> findAll(Pageable pageable) {
        String rowCountSql = "SELECT count(*) FROM car_model";
        int total = jdbcTemplate.queryForObject(rowCountSql, Integer.class);

        StringBuilder sqlQuery = new StringBuilder("SELECT car_model.id, car_brand_id, car_model.model, "
                + "car_model.production, car_brand.name as brandName from car_model "
                + "left join car_brand on car_model.car_brand_id = car_brand.id ");

        if (!pageable.getSort().isEmpty()) {
            Order order = pageable.getSort().getOrderFor("model");
            if(order != null) {
                sqlQuery.append(" ORDER BY model " + order.getDirection() );
            }
        } else {
            sqlQuery.append(" ORDER BY id ");
        }

        sqlQuery.append(" LIMIT ? OFFSET ? ");

        List<CarModel> cars = jdbcTemplate.query(sqlQuery.toString(),
                new Object[] { pageable.getPageSize(), pageable.getOffset() },
                new int[] { Types.INTEGER, Types.BIGINT },
                new CarRowMapper());

        return new PageImpl<>(cars, pageable, total);
    }


}
