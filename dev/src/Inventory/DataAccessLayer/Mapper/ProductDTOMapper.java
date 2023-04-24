package Inventory.DataAccessLayer.Mapper;

public class ProductDTOMapper {
    private ProductItemDTOMapper productItemDTOMapper;
    private CategoryDAO categoryDTOMapper;

    public ProductDTOMapper() {
        productItemDTOMapper = new ProductItemDTOMapper();
        categoryDTOMapper = new CategoryDAO();
    }

}
