package fr.shoppo.ms_commerce.infrastructure.mapper;

import fr.shoppo.ms_commerce.infrastructure.entity.Product;
import fr.shoppo.ms_commerce.infrastructure.entity.ProductWithoutCommerce;

public class ProductMapper {
    public static ProductWithoutCommerce toProductWithoutCommerce(Product p){
        var pwc = new ProductWithoutCommerce();
        pwc.setId(p.getId());
        pwc.setAttribute(p.getAttribute());
        return pwc;
    }
}
