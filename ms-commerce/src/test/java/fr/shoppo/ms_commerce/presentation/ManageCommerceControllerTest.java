package fr.shoppo.ms_commerce.presentation;

import fr.shoppo.ms_commerce.domain.service.*;
import fr.shoppo.ms_commerce.infrastructure.entity.CodeNaf;
import fr.shoppo.ms_commerce.infrastructure.entity.Commercant;
import fr.shoppo.ms_commerce.infrastructure.entity.Commerce;
import fr.shoppo.ms_commerce.infrastructure.service.PasswordManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import static fr.shoppo.ms_commerce.domain.constant.MessageConstantEnum.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ManageCommerceControllerTest {

    ManageCommerceController manageCommerceController;

    ManageCommerce manageCommerce;
    CommerceService commerceService;
    CommercantService commercantService;
    InseeInformationService inseeInformationService;
    NotificationService notificationService;
    PasswordManager passwordManager;

    @BeforeEach
    void setup(){
        commerceService = mock(CommerceService.class);
        commercantService = mock(CommercantService.class);
        notificationService = mock(NotificationService.class);
        inseeInformationService = mock(InseeInformationService.class);
        passwordManager = new PasswordManager();
        manageCommerce = new ManageCommerce(commerceService,inseeInformationService,commercantService);
        manageCommerceController =  new ManageCommerceController();
        manageCommerceController.setManageCommerce(manageCommerce);
        manageCommerceController.setNotificationService(notificationService);
        passwordManager.setRandom(new SecureRandom());
        manageCommerce.setPasswordGenerator(passwordManager);
    }

    @Test
    void should_stop_when_commerce_sent_is_null(){
        var commercant = new Commercant();
        commercant.setEmail("toto@test.fr");
        assertThat(manageCommerceController.createCommerce(commercant)).isEqualTo(ERREUR_INVALID_INPUT.toString());
    }

    @Test
    void should_stop_when_commercant_sent_is_null(){
        assertThat(manageCommerceController.createCommerce(null)).isEqualTo(ERREUR_INVALID_INPUT.toString());
    }

    @Test
    void should_stop_when_commercant_mail_sent_is_null(){
        var commercant = new Commercant();
        var commerce = new Commerce();
        commercant.setCommerce(commerce);
        commerce.setSiretCode("13002358300012");
        assertThat(manageCommerceController.createCommerce(commercant)).isEqualTo(ERREUR_INVALID_INPUT.toString());
    }

    @Test
    void should_stop_if_insee_dont_find_any_commerce_with_siret(){
        var commerce = new Commerce();
        commerce.setSiretCode("13002358300012");
        var commercant = new Commercant();
        commercant.setEmail("toto@test.fr");
        commercant.setCommerce(commerce);
        var codeNaf = new CodeNaf();
        codeNaf.setCode("87.40Z");
        commerce.setCodeNaf(codeNaf);

        when(inseeInformationService.findCommerceBySiret("13002358300011"))
                .thenReturn(commerce);

        assertThat(manageCommerceController
                .createCommerce(commercant)).isEqualTo(ERREUR_COMMERCE_NOT_CREATED.toString());
    }

    @Test
    void should_return_error_if_commerce_hasnt_been_created(){
        var commerce = new Commerce();
        commerce.setSiretCode("13002358300011");
        var codenaf = new CodeNaf();
        commerce.setCodeNaf(codenaf);
        var commercant = new Commercant();
        commercant.setEmail("toto@test.fr");
        commercant.setCommerce(commerce);
        when(inseeInformationService.findCommerceBySiret("13002358300011"))
                .thenReturn(commerce);
        when(inseeInformationService.existingCodeNaf(any()))
                .thenReturn(codenaf);

        when(commerceService.createOrUpdateCommerce(any()))
                .thenReturn(Optional.empty());

        assertThat(manageCommerceController.createCommerce(commercant)).isEqualTo(ERREUR_COMMERCE_NOT_CREATED.toString());
    }

    @Test
    void should_return_ok_if_commerce_and_commercant_has_been_created(){
        var commerce = new Commerce();
        commerce.setSiretCode("13002358300011");
        var codenaf = new CodeNaf();
        var commercant = new Commercant();
        commercant.setEmail("toto@test.fr");
        commerce.setCodeNaf(codenaf);
        commercant.setCommerce(commerce);
        when(inseeInformationService.findCommerceBySiret("13002358300011"))
                .thenReturn(commerce);
        when(inseeInformationService.existingCodeNaf(any()))
                .thenReturn(codenaf);

        when(commerceService.createOrUpdateCommerce(any()))
                .thenReturn(Optional.of(commerce));

        assertThat(manageCommerceController.createCommerce(commercant)).isEqualTo(OK.toString());
    }

    @Test
    void should_return_error_if_commerce_has_been_added_for_the_second_time(){
        var commerce = new Commerce();
        commerce.setSiretCode("13002358300011");
        var codenaf = new CodeNaf();
        var commercant = new Commercant();
        commercant.setEmail("toto@test.fr");
        commerce.setCodeNaf(codenaf);
        commercant.setCommerce(commerce);
        when(inseeInformationService.existingCodeNaf(any()))
                .thenReturn(codenaf);
        when(commerceService.findBySiret("13002358300011"))
                .thenReturn(Optional.of(commerce));
        when(commerceService.createOrUpdateCommerce(any()))
                .thenReturn(Optional.of(commerce));

        assertThat(manageCommerceController.createCommerce(commercant)).isEqualTo(ERREUR_COMMERCE_NOT_CREATED.toString());
    }

    @Test
    void should_return_error_if_there_is_problem_to_save(){
        var commerce = new Commerce();
        commerce.setSiretCode("13002358300011");
        var codenaf = new CodeNaf();
        var commercant = new Commercant();
        commercant.setEmail("toto@test.fr");
        commerce.setCodeNaf(codenaf);
        commercant.setCommerce(commerce);
        when(inseeInformationService.existingCodeNaf(any()))
                .thenReturn(codenaf);
        when(commerceService.findBySiret("13002358300011"))
                .thenReturn(Optional.empty());
        when(commerceService.createOrUpdateCommerce(any()))
                .thenReturn(Optional.empty());

        assertThat(manageCommerceController.createCommerce(commercant)).isEqualTo(ERREUR_COMMERCE_NOT_CREATED.toString());
    }

    @Test
    void should_return_error_when_adding_commercant_and_is_already_in(){
        var commerce = new Commerce();
        commerce.setSiretCode("13002358300011");
        var codenaf = new CodeNaf();
        var commercant = new Commercant();
        commercant.setEmail("toto@test.fr");
        commerce.setCodeNaf(codenaf);
        commercant.setCommerce(commerce);

        when(commerceService.findBySiret("13002358300011"))
                .thenReturn(Optional.of(commerce));
        when(commerceService.createOrUpdateCommerce(any()))
                .thenReturn(Optional.of(commerce));
        when(commercantService.findByCommerce(commerce)).thenReturn(Optional.of(List.of(commercant)));

        assertThat(manageCommerceController.addCommercant(commercant)).isEqualTo(ERREUR_COMMERCANT_NOT_ADDED.toString());
    }

    @Test
    void should_return_error_when_adding_commercant_and_there_is_problem_at_the_end_of_process(){
        var commerce = new Commerce();
        commerce.setSiretCode("13002358300011");
        var codenaf = new CodeNaf();
        var commercant = new Commercant();
        commercant.setEmail("toto@test.fr");
        commerce.setCodeNaf(codenaf);
        commercant.setCommerce(commerce);

        when(commerceService.findBySiret("13002358300011"))
                .thenReturn(Optional.of(commerce));
        when(commerceService.createOrUpdateCommerce(any()))
                .thenReturn(Optional.empty());

        assertThat(manageCommerceController.addCommercant(commercant)).isEqualTo(ERREUR_COMMERCANT_NOT_ADDED.toString());
    }

    @Test
    void should_return_error_when_adding_commercant_and_commerce_not_exist(){
        var commerce = new Commerce();
        commerce.setSiretCode("13002358300011");
        var codenaf = new CodeNaf();
        var commercant = new Commercant();
        commercant.setEmail("toto@test.fr");
        commerce.setCodeNaf(codenaf);
        commercant.setCommerce(commerce);
        when(commerceService.findBySiret("13002358300011"))
                .thenReturn(Optional.empty());
        when(commerceService.createOrUpdateCommerce(any()))
                .thenReturn(Optional.of(commerce));
        assertThat(manageCommerceController.addCommercant(commercant)).isEqualTo(ERREUR_COMMERCE_NOT_FOUND.toString());
    }

    @Test
    void should_return_ok_when_everything_is_good(){
        var commerce = new Commerce();
        commerce.setSiretCode("13002358300011");
        var codenaf = new CodeNaf();
        var commercant = new Commercant();
        commercant.setEmail("toto@test.fr");
        commerce.setCodeNaf(codenaf);
        commercant.setCommerce(commerce);
        when(commerceService.findBySiret("13002358300011"))
                .thenReturn(Optional.of(commerce));
        when(commerceService.createOrUpdateCommerce(any()))
                .thenReturn(Optional.of(commerce));
        assertThat(manageCommerceController.addCommercant(commercant)).isEqualTo(OK.toString());
    }

    @Test
    void should_stop_if_siret_or_commercant_or_both_are_null(){
        var commercant = new Commercant();
        commercant.setCommerce(new Commerce());
        assertThat(manageCommerceController.addCommercant(commercant)).isEqualTo(ERREUR_INVALID_INPUT.toString());
        assertThat(manageCommerceController.deleteCommercant(commercant)).isEqualTo(ERREUR_INVALID_INPUT.toString());
    }

    @Test
    void should_remove_commercant_from_commerce(){
        var commerce = new Commerce();
        commerce.setSiretCode("13002358300011");
        var codenaf = new CodeNaf();
        var commercant = new Commercant();
        commercant.setEmail("toto@test.fr");
        commerce.setCodeNaf(codenaf);
        commercant.setCommerce(commerce);
        commercant.setOwner(false);
        when(commerceService.findBySiret("13002358300011"))
                .thenReturn(Optional.of(commerce));
        when(commerceService.deleteCommercant(commerce,commercant)).thenReturn(Optional.of(commerce));
        when(commercantService
                .findByCommerce(commerce)).thenReturn(Optional.of(List.of(commercant)));
        assertThat(manageCommerceController.deleteCommercant(commercant)).isEqualTo(OK.toString());
    }

    @Test
    void should_remove_commercant_from_commerce_but_commercant_is_owner(){
        var commerce = new Commerce();
        commerce.setSiretCode("13002358300011");
        var codenaf = new CodeNaf();
        var commercant = new Commercant();
        commercant.setEmail("toto@test.fr");
        commerce.setCodeNaf(codenaf);
        commercant.setCommerce(commerce);
        commercant.setOwner(true);

        when(commerceService.findBySiret("13002358300011"))
                .thenReturn(Optional.of(commerce));
        when(commerceService.deleteCommercant(commerce,commercant)).thenReturn(Optional.of(commerce));
        when(commercantService
                .findByCommerce(commerce)).thenReturn(Optional.of(List.of(commercant)));
        assertThat(manageCommerceController.deleteCommercant(commercant)).isEqualTo(ERREUR_COMMERCANT_OWNER_NOT_DELETED.toString());
    }

    @Test
    void should_remove_commercant_from_commerce_but_commerce_not_found(){
        var commerce = new Commerce();
        commerce.setSiretCode("13002358300011");
        var codenaf = new CodeNaf();
        var commercant = new Commercant();
        commercant.setEmail("toto@test.fr");
        commerce.setCodeNaf(codenaf);
        commercant.setCommerce(commerce);

        when(commerceService.findBySiret("13002358300011"))
                .thenReturn(Optional.empty());
        when(commerceService.deleteCommercant(commerce,commercant)).thenReturn(Optional.of(commerce));

        assertThat(manageCommerceController.deleteCommercant(commercant)).isEqualTo(ERREUR_COMMERCE_NOT_FOUND.toString());
    }

    @Test
    void should_remove_commercant_from_commerceService_thrown_something(){
        var commerce = new Commerce();
        commerce.setSiretCode("13002358300011");
        var codenaf = new CodeNaf();
        var commercant = new Commercant();
        commercant.setEmail("toto@test.fr");
        commerce.setCodeNaf(codenaf);
        commercant.setCommerce(commerce);

        when(commerceService.findBySiret("13002358300011"))
                .thenReturn(Optional.of(commerce));
        when(commerceService.deleteCommercant(commerce,commercant)).thenReturn(Optional.empty());
        when(commercantService
                .findByCommerce(commerce)).thenReturn(Optional.of(List.of(commercant)));
        assertThat(manageCommerceController.deleteCommercant(commercant)).isEqualTo(ERREUR.toString());
    }

    @Test
    void should_update_when_update_goes_ok(){
        when(commerceService.findBySiret(anyString())).thenReturn(Optional.of(new Commerce()));
        when(commerceService.createOrUpdateCommerce(any(Commerce.class))).thenReturn(Optional.of(new Commerce()));
        var commerce = new Commerce();
        commerce.setSiretCode("aa");
        assertThat(manageCommerceController.updateCommerce(commerce)).isEqualTo(OK.toString());
    }

    @Test
    void shouldnt_update_when_update_goes_wrong(){
        when(commerceService.findBySiret(anyString())).thenReturn(Optional.empty());
        when(commerceService.createOrUpdateCommerce(any(Commerce.class))).thenReturn(Optional.empty());
        assertThat(manageCommerceController.updateCommerce(new Commerce())).isEqualTo(ERREUR_COMMERCE_NOT_UPDATED.toString());
    }
}