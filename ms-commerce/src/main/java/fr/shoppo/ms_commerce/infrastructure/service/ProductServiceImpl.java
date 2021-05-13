package fr.shoppo.ms_commerce.infrastructure.service;

import fr.shoppo.ms_commerce.domain.constant.MessageConstantEnum;
import fr.shoppo.ms_commerce.domain.service.ProductService;
import fr.shoppo.ms_commerce.infrastructure.dao.CommercantDao;
import fr.shoppo.ms_commerce.infrastructure.dao.CommerceDao;
import fr.shoppo.ms_commerce.infrastructure.dao.ProductDao;
import fr.shoppo.ms_commerce.infrastructure.dao.ProductVfpDao;
import fr.shoppo.ms_commerce.infrastructure.entity.Commerce;
import fr.shoppo.ms_commerce.infrastructure.entity.Product;
import fr.shoppo.ms_commerce.infrastructure.exception.CommercantNotFoundException;
import fr.shoppo.ms_commerce.infrastructure.exception.CommerceException;
import fr.shoppo.ms_commerce.infrastructure.exception.ProductException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductDao productDao;
    private final CommerceDao commerceDao;
    private final CommercantDao commercantDao;
    private final ProductVfpDao productVfpDao;

    public ProductServiceImpl(ProductDao productDao, CommerceDao commerceDao, CommercantDao commercantDao, ProductVfpDao productVfpDao) {
        this.productDao = productDao;
        this.commerceDao = commerceDao;
        this.commercantDao = commercantDao;
        this.productVfpDao = productVfpDao;
    }

    @Override
    public Optional<Product> createProduit(Product product) {
        var c = product.getCommerce();
        var productAttr = product.getAttribute();
        try {
            var commerce = commerceDao
                    .findBySiretCode(c.getSiretCode())
                    .orElseThrow(CommercantNotFoundException::new);

            product.setCommerce(commerce);
            product.image(productAttr.getImage());

            return Optional.of(productDao.save(product));
        } catch (CommerceException ex) {
            logger.error(ex.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Product> editProduct(Product product) {
        var productAttr = product.getAttribute();

        try {
            var commerce = commerceDao
                    .findBySiretCode(product.getCommerce().getSiretCode())
                    .orElseThrow(CommercantNotFoundException::new);

            var productToEdit = productDao
                    .findById(product.getId())
                    .orElseThrow(() -> new ProductException(MessageConstantEnum.ERREUR_PRODUCT_NOT_FOUND));

            product.setId(productToEdit.getId());

            if(productAttr.getName() == null)
                throw new ProductException(MessageConstantEnum.ERREUR_PRODUCT_INFO_MISSING);

            if(productToEdit.getCommerce().getSiretCode().equals(commerce.getSiretCode())) {
                product.image(productAttr.getImage());
                return Optional.of(productDao.save(product));
            }

            throw new ProductException(MessageConstantEnum.ERREUR_WRONG_COMMERCANT);

        }catch (Exception ex){
            logger.error(ex.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Product> deleteProduct(Product product) {
        try {

            var commerce = commerceDao
                    .findBySiretCode(product.getCommerce().getSiretCode())
                    .orElseThrow(CommercantNotFoundException::new);

            var productToDelete = productDao
                    .findById(product.getId())
                    .orElseThrow(() -> new ProductException(MessageConstantEnum.ERREUR_PRODUCT_NOT_FOUND));

            if(productToDelete.getCommerce().getSiretCode().equals(commerce.getSiretCode())) {
                productDao.delete(productToDelete);
                return Optional.of(productToDelete);
            }
            throw new ProductException(MessageConstantEnum.ERREUR_WRONG_COMMERCANT);
        }catch (Exception ex){
            logger.error(ex.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Product> getProduct(Product product) {
        try {
            var commerce = commerceDao
                    .findBySiretCode(product.getCommerce().getSiretCode())
                    .orElseThrow(CommercantNotFoundException::new);

            var productGet = productDao
                    .findById(product.getId())
                    .orElseThrow(() -> new ProductException(MessageConstantEnum.ERREUR_PRODUCT_NOT_FOUND));

            if (productGet.getCommerce().getSiretCode().equals(commerce.getSiretCode()))
                return Optional.of(productGet);
            throw new ProductException(MessageConstantEnum.ERREUR_WRONG_COMMERCANT);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Product> updateQuantityProduit(Product product) {
        try {
            var productDB = productDao
                    .findById(product.getId())
                    .orElseThrow(() -> new ProductException(MessageConstantEnum.ERREUR_PRODUCT_NOT_FOUND));
            var pdbAttr = productDB.getAttribute();
            pdbAttr.setStock(Math.max(pdbAttr.getStock(), 0));

            var commerceDB = productDB.getCommerce();
            var commerce = product.getCommerce();
            productDB.getAttribute().setStock(product.getAttribute().getStock());

            if(!commerceDB.equalsSirets(commerce))
                throw new ProductException(MessageConstantEnum.ERREUR_WRONG_COMMERCANT);
            return Optional.of(productDao.save(productDB));
        } catch (ProductException  e) {
            logger.error(e.getMessage());
            return Optional.empty();
        }

    }

    @Override
    public List<Product> getProductsCommerce(String email) {
        var commercant = commercantDao.findByEmail(email);
        if(commercant == null)
            return new ArrayList<>();
        return productDao.findByCommerce(commercant.getCommerce());
    }

    @Override
    public Optional<Product> updateProduct(Product p){
        return Optional.of(productDao.save(p));
    }

    @Override
    public boolean productInCommerce(int id, Commerce c) {
        AtomicBoolean result = new AtomicBoolean(false);
        productDao.findById(id).ifPresent(product ->
                result.set(product.getCommerce().equals(c))
        );
        return result.get();
    }

    @Override
    public List<Product> getProductsNotVfpCommerce(String email) {
        var commercant = commercantDao.findByEmail(email);
        if(commercant == null)
            return new ArrayList<>();
        return productDao.findByCommerce(commercant.getCommerce()).stream()
                .filter( p-> productVfpDao.findProductVfpByProduct(p).isEmpty())
                .collect(Collectors.toList());
    }
}
