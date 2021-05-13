package fr.shoppo.ms_commerce.domain.service;

import fr.shoppo.ms_commerce.infrastructure.entity.CodeNaf;
import fr.shoppo.ms_commerce.infrastructure.entity.Commerce;

public interface InseeInformationService {
    CodeNaf existingCodeNaf(String codeNaf);

    Commerce findCommerceBySiret(String siretCode);
}
