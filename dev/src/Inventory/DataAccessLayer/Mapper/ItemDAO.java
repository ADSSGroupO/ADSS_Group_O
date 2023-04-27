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
                if((int) row.get("is_defective") == 1)
                    item.setDefective(row.get("defective_description").toString());
                if((int) row.get("is_expired") == 1)
                    item.setExpired(true);
                if (row.get("selling_price") != null)
                    item.setSellingPrice((float) row.get("selling_price"));
                if (row.get("the_price_been_sold_at") != null)
                    item.setThePriceBeenSoldAt((float) row.get("the_price_been_sold_at"));
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
    public void addItem(String manufacturer, Integer barcode, String name, String expirationDate, double costPrice, int category, int productID, String size, double sellingPrice){
        try {
            connectDB.createTables();
            Item.Location locate = Item.Location.INVENTORY;
            String query = "INSERT INTO Items (producer, barcode, name, location, expiration_date, cost_price, category, makat, size, is_defective, is_expired, selling_price) VALUES ('" + manufacturer + "', " + barcode + ", '" + name + "', '" + locate + "', '" + expirationDate + "', " + costPrice + ", " + category + ", '" + productID + "', '" + size + "', 0, 0, " + sellingPrice + ")";
            connectDB.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            connectDB.close_connect();
        }
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
      public void moveItemToStore(Integer ItemID) {
          try {
              connectDB.createTables();
              String query = "UPDATE Items SET location = 'STORE' WHERE barcode = " + ItemID;
              connectDB.executeUpdate(query);
          } catch (Exception e) {
              System.out.println(e.getMessage());
          } finally {
              connectDB.close_connect();
          }
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

    public void startConnection() throws SQLException {
        connectDB.createTables();
        loadData();
    }

    public HashMap<Integer, ArrayList<Item>> getSoldItems() {
        try {
            connectDB.createTables();
            String query = "SELECT id FROM Category ";
            ArrayList<HashMap<String, Object>> CategorySet = connectDB.executeQuery(query);
            HashMap<Integer, ArrayList<Item>> soldItems = new HashMap<>();
            for(HashMap<String, Object> row : CategorySet) {
                int categoryID = (int) row.get("id");
                query = "SELECT * FROM Items WHERE category = " + categoryID + " AND location = 'SOLD'";
                ArrayList<HashMap<String, Object>> resultSet = connectDB.executeQuery(query);
                ArrayList<Item> items = new ArrayList<>();
                for (HashMap<String, Object> itemRow : resultSet) {
                    Item item;
                    Item.Location location = Item.Location.valueOf((String) itemRow.get("location"));
                    if(itemRow.get("size")!=null) {
                        item = new Item((String) itemRow.get("producer"), (int) itemRow.get("barcode"), (String) itemRow.get("name"), location, (String) itemRow.get("expiration_date"), (float) itemRow.get("cost_price"), (int) itemRow.get("makat"));
                    }
                    else {
                        item = new Item((String) itemRow.get("producer"), (int) itemRow.get("barcode"), (String) itemRow.get("name"), location, (String) itemRow.get("expiration_date"), (float) itemRow.get("cost_price"), (int) itemRow.get("makat"), (String) itemRow.get("size"));
                    }
                    if((int) itemRow.get("is_defective") == 1)
                        item.setDefective(itemRow.get("defective_description").toString());
                    if((int) itemRow.get("is_expired") == 1)
                        item.setExpired(true);
                    if (itemRow.get("selling_price") != null)
                        item.setSellingPrice((float) itemRow.get("selling_price"));
                    if (itemRow.get("the_price_been_sold_at") != null)
                        item.setThePriceBeenSoldAt((float) itemRow.get("the_price_been_sold_at"));
                    items.add(item);
                }
                soldItems.put(categoryID, items);
            }
            return soldItems;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public HashMap<Integer, Item> getItemById() {
        try{
            connectDB.createTables();
            String query = "SELECT * FROM Items";
            ArrayList<HashMap<String, Object>> resultSet = connectDB.executeQuery(query);
            HashMap<Integer, Item> items = new HashMap<>();
            for (HashMap<String, Object> itemRow : resultSet) {
                Item item;
                Item.Location location = Item.Location.valueOf((String) itemRow.get("location"));
                if(itemRow.get("size")!=null) {
                    item = new Item((String) itemRow.get("producer"), (int) itemRow.get("barcode"), (String) itemRow.get("name"), location, (String) itemRow.get("expiration_date"), (double) itemRow.get("cost_price"), (int) itemRow.get("makat"));
                }
                else {
                    item = new Item((String) itemRow.get("producer"), (int) itemRow.get("barcode"), (String) itemRow.get("name"), location, (String) itemRow.get("expiration_date"), (double) itemRow.get("cost_price"), (int) itemRow.get("makat"), (String) itemRow.get("size"));
                }
                if((int) itemRow.get("is_defective") == 1)
                    item.setDefective(itemRow.get("defective_description").toString());
                if((int) itemRow.get("is_expired") == 1)
                    item.setExpired(true);
                if (itemRow.get("selling_price") != null)
                    item.setSellingPrice((float) itemRow.get("selling_price"));
                if (itemRow.get("the_price_been_sold_at") != null)
                    item.setThePriceBeenSoldAt((float) itemRow.get("the_price_been_sold_at"));
                items.put((int) itemRow.get("barcode"), item);
            }
            return items;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
