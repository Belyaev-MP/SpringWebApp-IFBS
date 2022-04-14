package my.self.springapp.domain.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CarModel {

    private int id;
    private CarBrand brand;
    private String model;
    private Date production;

    public CarModel(int id, CarBrand brand, String name, Date productionDate) {
        super();
        this.id = id;
        this.brand = brand;
        this.model = name;
        this.production = productionDate;
    }

    public CarModel() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CarBrand getBrand() {
        return brand;
    }

    public void setBrand(CarBrand brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String name) {
        this.model = name;
    }

    public Date getProduction() {
        return production;
    }

    public void setProduction(Date producctionDate) {
        this.production = producctionDate;
    }

    public String getProductionDateFormat() {
        String productionYear = "";
        if (this.production != null) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy");
            productionYear = dateFormat.format(this.getProduction());
        }

        return productionYear;
    }

    @Override
    public String toString() {
        return "CarModel [id:" + this.id + ", name:" + this.model + ", production date:" + this.getProductionDateFormat() + ", brand:" + this.brand.getName() + "]";
    }

}
