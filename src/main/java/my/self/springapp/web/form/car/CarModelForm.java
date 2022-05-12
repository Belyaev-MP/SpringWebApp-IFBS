package my.self.springapp.web.form.car;

import org.springframework.web.multipart.MultipartFile;

public class CarModelForm {

    private Long id;
    private String model;
    private MultipartFile[] files;

    public CarModelForm() {}

    public CarModelForm(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String name) {
        this.model = name;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }


}
