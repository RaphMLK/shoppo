package fr.shoppo.msclient.infrastructure.service;

import fr.shoppo.msclient.infrastructure.dao.VfpPlaqueDao;
import fr.shoppo.msclient.infrastructure.dao.VfpTransportDao;
import fr.shoppo.msclient.infrastructure.entity.VfpPlaque;
import fr.shoppo.msclient.infrastructure.entity.VfpTransport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class VfpUseServiceImplTest {

    @InjectMocks
    private VfpUseServiceImpl vfpUseService;

    @Mock
    private VfpPlaqueDao vfpPlaqueDao;

    @Mock
    private VfpTransportDao vfpTransportDao;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void clientIsTransport_NotPresent(){
        var qrCode = UUID.randomUUID();
        when(vfpTransportDao.findVfpTransportByClient_QrCode(qrCode)).thenReturn(new HashSet<>());

        assertFalse(vfpUseService.clientIsTransport(qrCode));
    }

    @Test
    void clientIsTransport_ok(){
        var qrCode = UUID.randomUUID();
        var transport = new VfpTransport();
        transport.setDate(new Timestamp(System.currentTimeMillis()));
        var hashSet = new HashSet<VfpTransport>();
        hashSet.add(transport);
        when(vfpTransportDao.findVfpTransportByClient_QrCode(qrCode)).thenReturn(hashSet);

        assertTrue(vfpUseService.clientIsTransport(qrCode));
    }

    @Test
    void clientIsPark_NotPresent() {
        var plaque = "toto";
        when(vfpPlaqueDao.findVfpPlaqueByClient_Plaque(plaque)).thenReturn(new HashSet<>());
        assertFalse(vfpUseService.clientIsPark(plaque));
    }

    @Test
    void clientIsPark_ok(){
        var plaque = "toto";
        var plaqueVfp = new VfpPlaque();
        plaqueVfp.setDateHoraire(new Timestamp(System.currentTimeMillis()));
        var hashSet = new HashSet<VfpPlaque>();
        hashSet.add(plaqueVfp);
        when(vfpPlaqueDao.findVfpPlaqueByClient_Plaque(plaque)).thenReturn(hashSet);
        assertTrue(vfpUseService.clientIsPark(plaque));
    }

}