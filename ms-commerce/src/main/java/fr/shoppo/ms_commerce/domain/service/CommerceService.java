package fr.shoppo.ms_commerce.domain.service;

import fr.shoppo.ms_commerce.domain.bo.AddVfpByProductGroup;
import fr.shoppo.ms_commerce.infrastructure.entity.CodeNaf;
import fr.shoppo.ms_commerce.infrastructure.entity.Commercant;
import fr.shoppo.ms_commerce.infrastructure.entity.Commerce;
import fr.shoppo.ms_commerce.infrastructure.exception.CommercantNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CommerceService {
    Optional<Commerce> createOrUpdateCommerce(Commerce commerce);
    Optional<Commerce> findBySiret(String siret);
    Optional<CodeNaf> findCodeNaf(CodeNaf codeNaf);
    Optional<CodeNaf> createCodeNaf(CodeNaf codeNaf);
    Optional<Commerce> deleteCommercant(Commerce commerce, Commercant commercant);
    List<Commerce> getAllCommercesForClient();
}
