package fr.shoppo.ms_commerce.infrastructure.service;

import fr.shoppo.ms_commerce.domain.service.CommerceService;
import fr.shoppo.ms_commerce.infrastructure.dao.CodeNafDao;
import fr.shoppo.ms_commerce.infrastructure.dao.CommercantDao;
import fr.shoppo.ms_commerce.infrastructure.dao.CommerceDao;
import fr.shoppo.ms_commerce.infrastructure.entity.CodeNaf;
import fr.shoppo.ms_commerce.infrastructure.entity.Commercant;
import fr.shoppo.ms_commerce.infrastructure.entity.Commerce;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CommerceServiceImplTest {

    CommerceDao commerceDao;
    CodeNafDao codeNafDao;
    CommercantDao commercantDao;

    CommerceService commerceService;

    @BeforeEach
    void setup(){
        codeNafDao = mock(CodeNafDao.class);
        commerceDao = mock(CommerceDao.class);
        commercantDao = mock(CommercantDao.class);

        commerceService = new CommerceServiceImpl(commerceDao,codeNafDao,commercantDao);
    }

    @Test
    void should_createCommerce_with_success(){
        var commerce = new Commerce();
        when(commerceDao.save(any())).thenReturn(commerce);
        assertEquals(Optional.of(commerce),commerceService.createOrUpdateCommerce(commerce));
    }


    @Test
    void should_createCommerce_with_empty_if_any_exception_thrown(){
        var commerce = new Commerce();
        when(commerceDao.save(any())).thenThrow(mock(DataAccessException.class));
        assertEquals(Optional.empty(),commerceService.createOrUpdateCommerce(commerce));
    }

    @Test
    void should_createCodeNaf_with_success(){
        var codeNaf = new CodeNaf();
        when(codeNafDao.save(any())).thenReturn(codeNaf);
        assertEquals(Optional.of(codeNaf),commerceService.createCodeNaf(codeNaf));
    }

    @Test
    void should_createCodeNaf_with_empty_if_any_exception_thrown(){
        var codeNaf = new CodeNaf();
        when(codeNafDao.save(any())).thenThrow(mock(DataAccessException.class));
        assertEquals(Optional.empty(),commerceService.createCodeNaf(codeNaf));
    }

    @Test
    void should_findBySiret(){
        var commerce = new Commerce();
        when(commerceDao.findBySiretCode("1")).thenReturn(Optional.of(commerce));
        when(commerceDao.findBySiretCode("2")).thenReturn(Optional.empty());

        assertEquals(Optional.of(commerce),commerceService.findBySiret("1"));
        assertEquals(Optional.empty(),commerceService.findBySiret("2"));
    }

    @Test
    void should_findCodeNaf(){
        var codeNaf = new CodeNaf();

        when(codeNafDao.findByCode("1")).thenReturn(Optional.of(codeNaf));
        when(codeNafDao.findByCode("2")).thenReturn(Optional.empty());
        codeNaf.setCode("1");
        assertEquals(Optional.of(codeNaf),commerceService.findCodeNaf(codeNaf));
        codeNaf.setCode("2");
        assertEquals(Optional.empty(),commerceService.findCodeNaf(codeNaf));

    }

    @Test
    void should_delete_commercant(){
        var commerce = new Commerce();
        var incorrectCommerce = new Commerce();
        var commercant = new Commercant();
        var incorrectCommercant = new Commercant();

        commercant.setId(0);
        incorrectCommercant.setId(1);
        incorrectCommercant.setEmail("incorrect");/*le equals risque de mal mock*/

        when(commercantDao.findByEmail(commercant.getEmail())).thenReturn(commercant);
        when(commercantDao.findByEmail(incorrectCommercant.getEmail())).thenReturn(incorrectCommercant);

        doNothing().when(commercantDao).deleteById(commercant.getId());
        doThrow(mock(DataAccessException.class)).when(commercantDao).deleteById(incorrectCommercant.getId());

        when(commerceDao.save(commerce)).thenReturn(commerce);
        when(commerceDao.save(incorrectCommerce)).thenThrow(mock(DataAccessException.class));


        commercant.setCommerce(commerce);
        assertEquals(Optional.of(commerce),commerceService.deleteCommercant(commerce,commercant));
        assertEquals(Optional.empty(),commerceService.deleteCommercant(commerce,incorrectCommercant));
    }

}