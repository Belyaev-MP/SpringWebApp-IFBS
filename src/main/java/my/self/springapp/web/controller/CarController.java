package my.self.springapp.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import my.self.springapp.data.cars.CarModelDataRepository;
import my.self.springapp.domain.CarService;
import my.self.springapp.domain.model.CarModel;


@Controller
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private CarModelDataRepository carModelDataRepository;

    @GetMapping("car/list")
    public String list(Model model) {
        model.addAttribute("cars", carService.getCarsList());
        model.addAttribute("title", "Список автомобилей (демонстрация Spring JDBC)");

        return "car/list";
    }

    @GetMapping("car/list-data")
    public ModelAndView listData(ModelAndView model) {
        model.addObject("cars", carModelDataRepository.findAllByNameBrand("Tes%"));
        model.addObject("title", "Список автомобилей (демонстрация Spring Data JDBC)");

        model.setViewName("car/list-data");

        return model;
    }
    
    @PostMapping(path = "/cars/ajax-filter", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<CarModel> ajaxFilter(@RequestParam(name = "q", required = false)String pattern) {
    	return carService.getCarsByNameLike(pattern);
    }

}
