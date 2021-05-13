package fr.shoppo.msclient.domain.service;

import java.util.UUID;

public interface VfpUseService {

    boolean clientIsTransport(UUID qrCode);

    boolean clientIsPark(String plaque);
}
