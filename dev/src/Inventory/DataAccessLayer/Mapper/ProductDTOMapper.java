package Inventory.DataAccessLayer.Mapper;

public class ProductDTOMapper {
    private ProductItemDTOMapper productItemDTOMapper;
    private CategoryDTOMapper categoryDTOMapper;

    public ProductDTOMapper() {
        productItemDTOMapper = new ProductItemDTOMapper();
        categoryDTOMapper = new CategoryDTOMapper();
    }

}
