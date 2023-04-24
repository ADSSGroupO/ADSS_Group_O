package Inventory.DataAccessLayer.DTO;
import java.io.File;
import java.time.LocalDate;

public class CategoryDTO {
    private int id;
    private String name;
    private LocalDate startDiscount;
    private LocalDate endDiscount;
    private float discount;

    public CategoryDTO(int id, String name, LocalDate startDiscount, LocalDate endDiscount, float discount) {
        this.id = id;
        this.name = name;
        this.startDiscount = startDiscount;
        this.endDiscount = endDiscount;
        this.discount = discount;
    }

    public CategoryDTO LoadData(){
        String path =System.getProperty("user.dir");
        return null;
    }

}
