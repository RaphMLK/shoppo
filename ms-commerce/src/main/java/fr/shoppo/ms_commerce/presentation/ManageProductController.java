package fr.shoppo.ms_commerce.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.shoppo.ms_commerce.domain.bo.ProductWithCommercant;
import fr.shoppo.ms_commerce.domain.constant.MessageConstantEnum;
import fr.shoppo.ms_commerce.domain.service.CommercantService;
import fr.shoppo.ms_commerce.domain.service.ProductService;
import fr.shoppo.ms_commerce.infrastructure.entity.Commercant;
import fr.shoppo.ms_commerce.infrastructure.entity.Commerce;
import fr.shoppo.ms_commerce.infrastructure.entity.Product;
import fr.shoppo.ms_commerce.infrastructure.exception.CommercantNotFoundException;
import fr.shoppo.ms_commerce.infrastructure.exception.ProductException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Optional;

import static fr.shoppo.ms_commerce.domain.constant.MessageConstantEnum.OK;

@Component
public class ManageProductController {
    private static final Logger logger = LoggerFactory.getLogger(ManageProductController.class);

    private final ProductService productService;
    private final CommercantService commercantService;

    public ManageProductController(ProductService productService, CommercantService commercantService) {
        this.productService = productService;
        this.commercantService = commercantService;
    }

    @RabbitListener(queues = "${mq.queue.add-product}")
    public String createProduct(ProductWithCommercant productWithCommercant){
        try{
            Optional<Commercant> commercant = commercantService.getCommercant(productWithCommercant.getCommercant());
            if(commercant.isEmpty()){
                throw new CommercantNotFoundException();
            }
            Product product = productWithCommercantToProduct(productWithCommercant, commercant.get().getCommerce());
            logger.debug("{}",productService);
            Optional<Product> newProduct = productService.createProduit(product);
            logger.debug("{}",newProduct);
            if(newProduct.isEmpty()){
                throw new ProductException(MessageConstantEnum.ERREUR_PRODUCT_NOT_CREATED);
            }
            return OK.toString();
        } catch (ProductException | CommercantNotFoundException productException){
            logEnd(productException,"ADD NEW PRODUCT");
            return productException.getMessage();
        }
    }

    @RabbitListener(queues = "${mq.queue.edit-product}")
    public String editProduct(ProductWithCommercant productWithCommercant) {
        try {

            Optional<Commercant> commercant = commercantService.getCommercant(productWithCommercant.getCommercant());
            if(commercant.isEmpty()) {
                throw new CommercantNotFoundException();
            }

            Product product = productWithCommercantToProduct(productWithCommercant, commercant.get().getCommerce());
            Optional<Product> editedProduct = productService.editProduct(product);
            if(editedProduct.isEmpty()){
                throw new ProductException(MessageConstantEnum.ERREUR_EDIT_PRODUCT);
            }
            return OK.toString();
        } catch (Exception exception) {
            logEnd(exception,"EDIT PRODUCT");
            return exception.getMessage();
        }
    }

    @RabbitListener(queues = "${mq.queue.delete-product}")
    public String deleteProduct(ProductWithCommercant productWithCommercant) {
        try {

            Optional<Commercant> commercant = commercantService.getCommercant(productWithCommercant.getCommercant());
            if(commercant.isEmpty()) {
                throw new CommercantNotFoundException();
            }

            Product product = productWithCommercantToProduct(productWithCommercant, commercant.get().getCommerce());
            Optional<Product> productDeleted = productService.deleteProduct(product);
            if (productDeleted.isEmpty()) {
                throw new ProductException(MessageConstantEnum.ERREUR_DELETE_PRODUCT);
            }
            return OK.toString();
        } catch (ProductException | CommercantNotFoundException productException) {
            logEnd(productException,"DELETE PRODUCT");
            return productException.getMessage();
        }
    }

    @RabbitListener(queues = "${mq.queue.get-product}")
    public String getProduct(ProductWithCommercant productWithCommercant) {
        var objectMapper = new ObjectMapper();


        try {
            var commercant = commercantService
                    .getCommercant(productWithCommercant.getCommercant())
                    .orElseThrow(CommercantNotFoundException::new);
            var product = productWithCommercantToProduct(productWithCommercant, commercant.getCommerce());
            var productGet = productService.getProduct(product)
                    .orElseThrow(() -> new ProductException(MessageConstantEnum.ERREUR_PRODUCT_NOT_FOUND));
            var attribute = productGet.getAttribute();
            productGet.setCommerce(null);

            ProductWithCommercant productWithCommercant1 = new ProductWithCommercant();

            productWithCommercant1.setId(productGet.getId());
            productWithCommercant1.setName(attribute.getName());
            productWithCommercant1.setDescription(attribute.getDescription());
            productWithCommercant1.setImage(Base64.getEncoder().encodeToString(attribute.getImage()));
            productWithCommercant1.setStock(attribute.getStock());
            productWithCommercant1.setPrix(attribute.getPrix());

            return objectMapper.writeValueAsString(productWithCommercant1);
        } catch (CommercantNotFoundException | JsonProcessingException | ProductException commercantNotFoundException) {
            logEnd(commercantNotFoundException, "GET PRODUCT");
            return commercantNotFoundException.getMessage();
        }
    }

    @RabbitListener(queues = "${mq.queue.update-quantity}")
    public String updateQuantity(Product product){
        try {
            Optional<Product> productOptional = productService.updateQuantityProduit(product);
            if (productOptional.isEmpty())
                throw new ProductException(MessageConstantEnum.ERREUR_PRODUCT_NOT_UPDATE);
            return OK.toString();
        } catch (ProductException e){
            logEnd(e, "UPDATE QUANTITY PRODUIT");
            return e.getMessage();
        }
    }

    @RabbitListener(queues = "${mq.queue.get-products-commerce}")
    public String getProductsCommerce(String email) throws JsonProcessingException {
        var produitList = productService.getProductsCommerce(email);
        produitList.forEach(e -> e.setCommerce(null));
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(produitList);
    }

    @RabbitListener(queues = "${mq.queue.get-product-commerce-not-vfp}")
    public String getProductsNotVfpCommerce(String email) throws JsonProcessingException {
        var produitList = productService.getProductsNotVfpCommerce(email);
        produitList.forEach(e -> e.setCommerce(null));
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(produitList);
    }

    private Product productWithCommercantToProduct(ProductWithCommercant productWithCommercant, Commerce commerce){
        var image = productWithCommercant.getImage();
        return Product.of(productWithCommercant.getId(),
                productWithCommercant.getName(),
                productWithCommercant.getDescription(),
                productWithCommercant.getPrix(),
                productWithCommercant.getStock(),
                commerce,
                image != null ? image.getBytes() : null
        );
    }

    void logEnd(Exception ex, String state){
        logger.info("END {} : {}", ex.getMessage(), state);
    }
}
