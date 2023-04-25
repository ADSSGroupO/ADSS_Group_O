package Inventory.DataAccessLayer.Mapper;

import Inventory.BusinessLayer.Item;

import java.sql.SQLException;
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
    public  String addItem(String manufacturer, Integer barcode, String name, String expirationDate, double costPrice, int category, int productID,String size){
        try {
            connectDB.createTables();
            Item.Location locate = Item.Location.INVENTORY;
            String query;
            if(size!=null)
                query = "INSERT INTO Items (producer, barcode, name, location, expiration_date, cost_price, makat, category, productID) VALUES ('" + manufacturer + "', " + barcode + ", '" + name + "', '" + locate + "', '" + expirationDate + "', " + costPrice + ", " + category + ", '" + productID + "')";
            else
                query = "INSERT INTO Items (producer, barcode, name, location, expiration_date, cost_price, makat, category, productID, size) VALUES ('" + manufacturer + "', " + barcode + ", '" + name + "', '" + locate + "', '" + expirationDate + "', " + costPrice + ", " + category + ", '" + productID + "', '" + size + "')";
            connectDB.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            connectDB.close_connect();
        }
        return "Item added successfully";
    }
    public void setSold(int barcode) {
        try {
            connectDB.createTables();
            String query = "UPDATE Items SET location = 'SOLD' WHERE barcode = " + barcode;
            connectDB.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            connectDB.close_connect();
        }
    }
     public String getItem(Integer CategoryID, Integer ItemID){
        try {
            connectDB.createTables();
            String query = "SELECT * FROM Items WHERE makat = " + CategoryID + " AND barcode = " + ItemID;
            connectDB.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            connectDB.close_connect();
        }
        return "Item sold successfully";
     }

     public String itemsInStock(Integer CategoryID){
        try {
            connectDB.createTables();
            String query = "SELECT * FROM Items WHERE makat = " + CategoryID+ " AND (location = 'INVENTORY' OR location = 'STORE')";
            connectDB.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            connectDB.close_connect();
        }
        return "Item sold successfully";
     }
      public  String moveItemToStore(Integer ItemID) {
          try {
              connectDB.createTables();
              String query = "UPDATE Items SET location = 'STORE' WHERE barcode = " + ItemID;
              connectDB.executeUpdate(query);
          } catch (Exception e) {
              System.out.println(e.getMessage());
          } finally {
              connectDB.close_connect();
          }
          return "Item moved successfully";
      }

      public  void defective(Integer DefItem, String reason) {
          try {
              connectDB.createTables();
              String query = "UPDATE Items SET (is_defective = 'DEFECTIVE' AND defective_description = "+reason+ ") WHERE barcode = " + DefItem;
              connectDB.executeUpdate(query);
          } catch (Exception e) {
              System.out.println(e.getMessage());
          } finally {
              connectDB.close_connect();
          }
      }

    public void setExpired(int barcode) {
        try {
            connectDB.createTables();
            String query = "UPDATE Items SET is_expired = 1 WHERE barcode = " + barcode;
            connectDB.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            connectDB.close_connect();
        }
    }

    public void setThePriceBeenSoldAt(int barcode, double thePriceBeenSoldAt) {
        try {
            connectDB.createTables();
            String query = "UPDATE Items SET the_price_been_sold_at = " + thePriceBeenSoldAt + " WHERE barcode = " + barcode;
            connectDB.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            connectDB.close_connect();
        }
    }

}
