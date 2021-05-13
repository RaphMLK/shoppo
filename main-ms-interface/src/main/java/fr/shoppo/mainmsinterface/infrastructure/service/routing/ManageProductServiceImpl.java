package fr.shoppo.mainmsinterface.infrastructure.service.routing;

import fr.shoppo.mainmsinterface.domain.bo.commerce.Commerce;
import fr.shoppo.mainmsinterface.domain.bo.commerce.Product;
import fr.shoppo.mainmsinterface.domain.bo.commerce.ProductWithCommercant;
import fr.shoppo.mainmsinterface.domain.service.commerce.ManageProductService;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManageProductServiceImpl extends RoutingKeyDictionnary implements ManageProductService {

    RabbitTemplate template;
    DirectExchange exchangeCommerce;

    @Override
    public String createProduit(String name, String description, float prix, int stock, String image, String commercant) {
        ProductWithCommercant product = new ProductWithCommercant();
        product.setName(name);
        product.setDescription(description);
        product.setPrix(prix);
        product.setStock(stock);
        product.setImage(image);
        product.setCommercant(commercant);
        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeCommerce.getName(),
                        routingKeyAddProduct,
                        product
                )
        );
    }

    @Override
    public String getProduct(Integer id, String commercant) {
        ProductWithCommercant product = new ProductWithCommercant();
        product.setId(id);
        product.setCommercant(commercant);

        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeCommerce.getName(),
                        routingKeyGetProduct,
                        product
                )
        );

    }

    @Override
    public String editProduct(int id, String name, String description, float prix, int stock, String image, String commercant) {
        ProductWithCommercant product = new ProductWithCommercant();
        product.setId(id);
        product.setName(name);
        product.setDescription(description);
        product.setPrix(prix);
        product.setStock(stock);
        product.setCommercant(commercant);
        product.setImage(image);
        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeCommerce.getName(),
                        routingKeyEditProduct,
                        product
                )
        );
    }

    @Override
    public String updateQuantityProduit(String siret, int id, int quantity) {
        Commerce commerce = new Commerce();
        commerce.setSiretCode(siret);

        Product product = new Product();
        product.setId(id);
        product.setStock(quantity);

        product.setCommerce(commerce);
        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeCommerce.getName(),
                        routingKeyUpdateQuantity,
                        product
                )
        );
    }

    @Override
    public String deleteProduct(int id, String commercant) {
        ProductWithCommercant product = new ProductWithCommercant();
        product.setId(id);
        product.setCommercant(commercant);
        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeCommerce.getName(),
                        routingKeyDeleteProduct,
                        product
                )
        );
    }

    public String getProductsCommerce(String email) {
        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeCommerce.getName(),
                        routingKeyGetProductsCommerce,
                        email
                )
        );
    }

    @Override
    public String getProductsNotVfp(String email) {
        return String.valueOf(
                template.convertSendAndReceive(
                        exchangeCommerce.getName(),
                        routingKeyGetProductsCommerceNotVfp,
                        email
                )
        );
    }

    @Autowired
    public void setTemplate(RabbitTemplate template) {
        this.template = template;
    }

    @Autowired
    public void setExchangeCommerce(DirectExchange exchangeCommerce) {
        this.exchangeCommerce = exchangeCommerce;
    }

}
