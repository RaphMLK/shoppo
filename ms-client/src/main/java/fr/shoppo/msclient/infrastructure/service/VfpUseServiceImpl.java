package fr.shoppo.msclient.infrastructure.service;

import fr.shoppo.msclient.domain.service.VfpUseService;
import fr.shoppo.msclient.infrastructure.dao.VfpPlaqueDao;
import fr.shoppo.msclient.infrastructure.dao.VfpTransportDao;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.StreamSupport;

@Service
public class VfpUseServiceImpl implements VfpUseService {

    private VfpPlaqueDao vfpPlaqueDao;
    private VfpTransportDao vfpTransportDao;

    @Override
    public boolean clientIsTransport(UUID qrCode) {
        var response = new AtomicBoolean(false);

        StreamSupport.stream(vfpTransportDao.findVfpTransportByClient_QrCode(qrCode).spliterator(),false)
                .filter(vfpTransport -> DateUtils.isSameDay(vfpTransport.getDate(),
                        new Timestamp(System.currentTimeMillis())))
                .findAny()
                .ifPresent(vfpTransport -> response.set(true));
        return response.get();
    }

    @Override
    public boolean clientIsPark(String plaque) {
        var response = new AtomicBoolean(false);
        StreamSupport.stream(vfpPlaqueDao.findVfpPlaqueByClient_Plaque(plaque).spliterator(),false)
                .filter(vfpPlaque -> {
                    var curentDateMinus20minute = LocalDateTime.now().minusMinutes(20);
                    var dateTimeVfp = vfpPlaque.getDateHoraire().toLocalDateTime();
                    return !dateTimeVfp.isBefore(curentDateMinus20minute);
                }).findAny()
                .ifPresent(vfpPlaque -> response.set(true));


        return response.get();
    }

    @Autowired
    public void setVfpTransportDao(VfpTransportDao vfpTransportDao) {
        this.vfpTransportDao = vfpTransportDao;
    }

    @Autowired
    public void setVfpPlaqueDao(VfpPlaqueDao vfpPlaqueDao) {
        this.vfpPlaqueDao = vfpPlaqueDao;
    }
}
