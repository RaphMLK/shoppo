package fr.shoppo.ms_commerce.infrastructure.service;

import fr.shoppo.ms_commerce.infrastructure.dao.CommercantDao;
import fr.shoppo.ms_commerce.infrastructure.entity.Commercant;
import fr.shoppo.ms_commerce.infrastructure.exception.CommercantErrorLogin;
import fr.shoppo.ms_commerce.infrastructure.exception.CommercantNotFoundException;
import fr.shoppo.ms_commerce.infrastructure.exception.CommerceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CommercantServiceImplTest {

    CommercantDao commercantDao;
    PasswordManager passwordManager;

    CommercantServiceImpl commercantService;

    @BeforeEach
    void setup(){
        commercantDao = mock(CommercantDao.class);
        passwordManager = mock(PasswordManager.class);
        commercantService = new CommercantServiceImpl(commercantDao);
        commercantService.setPassworGenerator(passwordManager);
    }

    @Test
    void should_reset_password_with_success() throws CommercantNotFoundException {
        //Given
        var commercant = new Commercant();
        when(commercantDao.findByEmail(any())).thenReturn(commercant);
        when(commercantDao.save(any())).thenReturn(commercant);
        when(passwordManager.generateSecureRandomPassword()).thenReturn("secured");
        when(passwordManager.hash("secured")).thenCallRealMethod();
        //When
        assertEquals(commercant,commercantService.resetPassword(""));
        //Then
        assertEquals("secured",commercant.getPassword());
    }

    @Test
    void should_reset_password_throw_exception_if_not_found() {
        when(commercantDao.findByEmail(any())).thenReturn(null);
        assertThrows(CommercantNotFoundException.class,() -> commercantService.resetPassword(""));
    }


    @Test
    void should_login_with_success() throws CommerceException {
        var commercant = new Commercant();
        when(commercantDao.findByEmail(any())).thenReturn(commercant);
        when(passwordManager.hash("secured")).thenCallRealMethod();
        commercant.setPassword(passwordManager.hash("secured"));

        assertEquals(commercant,commercantService.login("","secured"));
    }

    @Test
    void should_login_with_error_login_throw_exception()  {
        var commercant = new Commercant();
        when(commercantDao.findByEmail(any())).thenReturn(commercant);
        when(passwordManager.hash("secured")).thenCallRealMethod();
        commercant.setPassword("");

        assertThrows(CommercantErrorLogin.class,()->commercantService.login("","secured"));
    }

    @Test
    void should_login_with_commercant_not_found_throw_exception()  {
        var commercant = new Commercant();
        when(commercantDao.findByEmail(any())).thenReturn(null);

        assertThrows(CommercantNotFoundException.class,()->commercantService.login("","secured"));
    }

    @Test
    void getCommercantTest() throws CommercantNotFoundException {
        var commercant = new Commercant();
        when(commercantDao.findByEmail("email")).thenReturn(null);
        assertEquals(Optional.empty(),commercantService.getCommercant("email"));

        when(commercantDao.findByEmail("email")).thenReturn(commercant);
        assertEquals(Optional.of(commercant),commercantService.getCommercant("email"));
    }

    @Test
    void changePasswordNeedTest() throws CommerceException {
        when(commercantDao.findByEmail("test")).thenReturn(null);

        assertThrows(CommercantNotFoundException.class,()->commercantService.changePasswordNeed("test","test"));

        var commercant = new Commercant();
        when(commercantDao.findByEmail("test")).thenReturn(commercant);
        when(passwordManager.hash("test")).thenReturn("secured");

        commercant.setEmail("test");
        commercant.setPassword("secured");
        commercant.setChangePassword(false);

        assertEquals(commercant, commercantService.changePasswordNeed("test","test"));

    }

}