package my.self.springapp.domain.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("car_model")
public class CarModelData {

    @Id
    private int id;

    @Column("car_brand_id")
    private int brand;
    private String model;

    @Column("production")
    private Date production;

    public CarModelData(int id, int brand, String model, Date production) {
        super();
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.production = production;
    }

    public CarModelData() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBrand() {
        return brand;
    }

    public void setBrand(int brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String name) {
        this.model = name;
    }

    public Date getProductionDate() {
        return production;
    }

    public void setProductionDate(Date producctionDate) {
        this.production = producctionDate;
    }

    public String getProductionDateFormat() {
        String productionYear = "";
        if (this.production != null) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy");
            productionYear = dateFormat.format(this.getProductionDate());
        }

        return productionYear;
    }

    @Override
    public String toString() {
        return "CarModel [id:" + this.id + ", model:" + this.model + ", production date:" + this.getProductionDateFormat() + ", brand:" + this.brand + "]";
    }

}
