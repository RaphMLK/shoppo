package fr.shoppo.mainmsinterface.domain.service.commerce;

public interface ManageProductService {
    String editProduct(int id, String name, String description, float prix, int stock, String image, String commercant);
    String deleteProduct(int id, String commercant);
    String createProduit(String name, String description, float prix, int stock, String image, String commercant);
    String getProduct(Integer id, String username);
    String updateQuantityProduit(String email, int id, int quantity);
    String getProductsCommerce(String email);
    String getProductsNotVfp(String email);
}
