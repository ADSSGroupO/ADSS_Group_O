package dev.src.Inventory.BuisnessLayer;

import java.time.LocalDate;

public class Category {
    //dictionary contains the product by category
   // Dictionary<Integer,Product> productByCategory;
    String name;
    int id;
    LocalDate startDiscount;
    LocalDate endDiscount;
    float discount;
    public Category(String name, int id) {
        this.name = name;
        this.id = id;
    }


    public void setDiscount(LocalDate start, LocalDate end, float discount){
        this.discount=discount;
        startDiscount=start;
        endDiscount=end;
    }
    public float getDiscount() {
        LocalDate now = LocalDate.now();
        if(now.isBefore(startDiscount) && now.isAfter(endDiscount))
            return discount;
        else
            return 0;

    }

}
