package my.self.springapp.data.cars;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import my.self.springapp.domain.model.CarModelData;


public interface CarModelDataRepository
extends CrudRepository<CarModelData, Integer> {

    List<CarModelData> findAllByModel(String name);
    List<CarModelData> findAllByModelLike(String name);

    @Query("select car_model.* from car_model left join car_brand "
            + "on car_model.car_brand_id = car_brand.id where car_brand.name LIKE :brand")
    List<CarModelData> findAllByNameBrand(@Param("brand") String brandName);


    @Query("update car_model set model = :model where id = :id")
    @Modifying
    int updateModelNameByCarId(Integer id, String model);

}
