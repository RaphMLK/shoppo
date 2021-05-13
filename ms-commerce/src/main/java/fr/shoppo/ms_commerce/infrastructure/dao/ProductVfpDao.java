package fr.shoppo.ms_commerce.infrastructure.dao;

import fr.shoppo.ms_commerce.infrastructure.entity.Commerce;
import fr.shoppo.ms_commerce.infrastructure.entity.Product;
import fr.shoppo.ms_commerce.infrastructure.entity.ProductVfp;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductVfpDao extends CrudRepository<ProductVfp, Integer> {

    @Query("DELETE FROM ProductVfp p where p.id=:id")
    @Modifying
    void deleteById(int id);

    Optional<ProductVfp> findProductVfpByProduct(Product p);

    List<ProductVfp> findProductVfpByCommerce(Commerce c);
}
