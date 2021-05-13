package fr.shoppo.ms_commerce.infrastructure.mapper;

import fr.shoppo.ms_commerce.domain.bo.ProductVfpDTO;
import fr.shoppo.ms_commerce.domain.bo.ProductVfpProductDTO;
import fr.shoppo.ms_commerce.infrastructure.entity.ProductVfp;

public class ProductVfpToProductVfpDTO {

    public static ProductVfpDTO productVfpToProductVfpDTO(ProductVfp productVfp){
        if (productVfp.getProduct() == null)
            return new ProductVfpDTO(productVfp.getId(), productVfp.getLibelle(), null, productVfp.getStock(), productVfp.getCommerce().getSiretCode());
        var product = productVfp.getProduct();
        return new ProductVfpDTO(productVfp.getId(), productVfp.getLibelle(),
                new ProductVfpProductDTO(product.getId(), product.getAttribute().getName()),
                product.getAttribute().getStock(), productVfp.getCommerce().getSiretCode());

    }
}
