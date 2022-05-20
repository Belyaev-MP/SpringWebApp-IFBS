package my.self.springapp.web.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import my.self.springapp.data.cars.CarModelDataRepository;
import my.self.springapp.domain.CarService;
import my.self.springapp.domain.model.CarModel;
import my.self.springapp.web.form.car.CarModelForm;
import my.self.springapp.web.util.CarImageService;


@Controller
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private CarImageService carImageService;

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

    @GetMapping(value = {"car/page", "car/page/{pageId}"})
    public String list(Model model, @PathVariable Optional<Integer> pageId) {
        int page = pageId.isPresent() ? pageId.get() : 0;

        Page<CarModel> cars = carService.findAll(PageRequest.of(page, 4, Sort.by("name").ascending()));

        model.addAttribute("cars", cars);

        return "car/page";
    }

    @GetMapping(value="car/img-big/{carId}", produces=MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public FileSystemResource bigAvatar(ModelAndView modelAndView, @PathVariable Long carId) {
        return carImageService.getImage(carId, CarImageService.BIG_AVATAR_POSTFIX);
    }


    @GetMapping(value="car/img-small/{carId}", produces=MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public FileSystemResource smallAvatar(ModelAndView modelAndView, @PathVariable Long carId) {
        return carImageService.getImage(carId, CarImageService.SMALL_AVATAR_POSTFIX);
    }

    @GetMapping("/car/img-upload")
    public ModelAndView avatarUpload(ModelAndView modelAndView, @RequestParam("carId") Optional<Long> carId) {
        if (carId.isEmpty()) {
            modelAndView.setViewName("redirect:/car/page");
        } else {
            CarModel car = carService.findById(carId.get());
            CarModelForm form = new CarModelForm();
            BeanUtils.copyProperties(car, form);
            form.setId(Long.valueOf(car.getId()));

            modelAndView.addObject(form);

            modelAndView.setViewName("/car/img-upload");
        }
        return modelAndView;
    }

    @PostMapping(path = {"/car/img-upload"}, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ModelAndView avatarUploadProcessing(@ModelAttribute CarModelForm carModelForm, BindingResult bindingResult,
            ModelAndView modelAndView) {

        Long id = carModelForm.getId();

        if (id != null && id > 0) {
            CarModel car = carService.findById(id);

            if (car != null) {
                for (MultipartFile multipartFile : carModelForm.getFiles()) {
                    if (!carImageService.saveCarImage(multipartFile, id)) {
                        modelAndView.setViewName("redirect:/car/upload-error");
                        break;
                    }
                }
            }

            car.setModel(carModelForm.getModel());
            carService.update(car);
        }

        modelAndView.setViewName("redirect:/car/page");

        return modelAndView;
    }

    @GetMapping("/car/upload-error")
    public String uploadError() {
        return "/car/upload-error";
    }
}
