package my.self.springapp.data.cars;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import my.self.springapp.domain.model.CarModel;


public interface CarStorage {
    List<CarModel> getAllCarModels(String pattern);
    CarModel loadById(Long carId);
    void saveById(CarModel carModel);
    Page<CarModel> findAll(Pageable pageable);
}
