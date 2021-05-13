package fr.shoppo.ms_commerce.domain.service;

import fr.shoppo.ms_commerce.infrastructure.entity.Commerce;
import fr.shoppo.ms_commerce.infrastructure.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> createProduit(Product product);
    Optional<Product> editProduct(Product product);
    Optional<Product> deleteProduct(Product product);
    Optional<Product> getProduct(Product product);
    Optional<Product> updateQuantityProduit(Product product);
    List<Product> getProductsCommerce(String email);
    Optional<Product> updateProduct(Product p);
    boolean productInCommerce(int id, Commerce c);
    List<Product> getProductsNotVfpCommerce(String email);
}
