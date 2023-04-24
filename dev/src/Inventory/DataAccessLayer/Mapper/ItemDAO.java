package Inventory.DataAccessLayer.Mapper;

import Inventory.BusinessLayer.Item;
import java.util.ArrayList;
import java.util.HashMap;

public class ItemDAO {
    private final ConnectDB connectDB = ConnectDB.getInstance();

    public ItemDAO() {

    }

    public ArrayList<HashMap<Item, String>> loadData() {
        ArrayList<HashMap<Item, String>> items = new ArrayList<>();
        try {
            connectDB.createTables();
            String query = "SELECT * FROM Items";
            ArrayList<HashMap<String,Object>> resultSet = connectDB.executeQuery(query);
            for (HashMap<String, Object> row : resultSet) {
                Item item;
                Item.Location location = (Item.Location) row.get("location");
                if(row.get("size")!=null) {
                    item = new Item((String) row.get("producer"), (int) row.get("barcode"), (String) row.get("name"), location, (String) row.get("expiration_date"), (float) row.get("cost_price"), (int) row.get("makat"));
                }
                else {
                    item = new Item((String) row.get("producer"), (int) row.get("barcode"), (String) row.get("name"), location, (String) row.get("expiration_date"), (float) row.get("cost_price"), (int) row.get("makat"), (String) row.get("size"));
                }
                HashMap<Item, String> itemStringHashMap = new HashMap<>();
                itemStringHashMap.put(item, (String) row.get("category"));
                items.add(itemStringHashMap);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            connectDB.close_connect();
        }
        return items;
    }
}
