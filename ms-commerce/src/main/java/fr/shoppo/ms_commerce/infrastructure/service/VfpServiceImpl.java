package fr.shoppo.ms_commerce.infrastructure.service;

import fr.shoppo.ms_commerce.domain.bo.AddVfpByProductGroup;
import fr.shoppo.ms_commerce.domain.bo.ProductVfpDTO;
import fr.shoppo.ms_commerce.domain.constant.MessageConstantEnum;
import fr.shoppo.ms_commerce.domain.service.CommercantService;
import fr.shoppo.ms_commerce.domain.service.ProductService;
import fr.shoppo.ms_commerce.domain.service.VfpService;
import fr.shoppo.ms_commerce.infrastructure.dao.ProductDao;
import fr.shoppo.ms_commerce.infrastructure.dao.ProductVfpDao;
import fr.shoppo.ms_commerce.infrastructure.entity.ProductVfp;
import fr.shoppo.ms_commerce.infrastructure.mapper.ProductVfpToProductVfpDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class VfpServiceImpl implements VfpService {

    private CommercantService commercantService;
    private ProductService productService;
    private ProductVfpDao productVfpDao;
    private ProductDao productDao;

    @Override
    @Transactional
    public String addVfpByProduct(AddVfpByProductGroup addVfpByProductGroup) {
        AtomicReference<String> response = new AtomicReference<>(MessageConstantEnum.ERREUR.toString());
        var addVfpByProducts = addVfpByProductGroup.getAddVfpByProducts();
        commercantService.getCommercant(addVfpByProductGroup.getEmailCommerce())
                .ifPresent(commercant -> {
                    if (!addVfpByProducts.stream()
                            .allMatch(addVfpByProduct ->
                                    productService.productInCommerce(
                                    addVfpByProduct.getId(),
                                    commercant.getCommerce())
                            )
                    ) {
                        response.set(MessageConstantEnum.ERREUR_PRODUCT_NOT_FOUND.toString());
                    } else {
                        addVfpByProducts.forEach(newProductVfp ->
                                productDao.findById(newProductVfp.getId()).ifPresent(product ->
                                        productVfpDao.save(new ProductVfp(commercant.getCommerce(), product, newProductVfp.getLibelle()))
                                )
                        );
                        response.set(MessageConstantEnum.OK.toString());
                    }
                });
        return response.get();
    }

    @Override
    public String deleteVfp(int idVfp) {
        var response = new AtomicReference<>(MessageConstantEnum.ERREUR_PRODUCT_NOT_FOUND.toString());
        productVfpDao.findById(idVfp).ifPresent(p -> {
                productVfpDao.deleteById(idVfp);
                response.set(MessageConstantEnum.OK.toString());
        });
        return response.get();
    }

    @Override
    public List<ProductVfpDTO> getVfp(String email) {
        var response = new AtomicReference<>(new ArrayList<ProductVfpDTO>());
        commercantService.getCommercant(email).ifPresent( commercant ->
                productVfpDao.findProductVfpByCommerce(commercant.getCommerce())
                .forEach( p -> response.get().add(ProductVfpToProductVfpDTO.productVfpToProductVfpDTO(p))
            )
        );
        return response.get();
    }

    @Override
    public Optional<ProductVfp> getVfpById(int id) {
        AtomicReference<Optional<ProductVfp>> productVfpOptionnal = new AtomicReference<>(Optional.empty());
        productVfpDao.findById(id).ifPresent(productVfp ->
                productVfpOptionnal.set(Optional.of(productVfp))
        );
        return productVfpOptionnal.get();
    }

    @Autowired
    public void setCommercantService(CommercantService commercantService) {
        this.commercantService = commercantService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setProductVfpDao(ProductVfpDao productVfpDao) {
        this.productVfpDao = productVfpDao;
    }

    @Autowired
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
