package fr.shoppo.ms_commerce.infrastructure.service;

import fr.shoppo.ms_commerce.domain.service.CommerceService;
import fr.shoppo.ms_commerce.infrastructure.dao.CodeNafDao;
import fr.shoppo.ms_commerce.infrastructure.dao.CommercantDao;
import fr.shoppo.ms_commerce.infrastructure.dao.CommerceDao;
import fr.shoppo.ms_commerce.infrastructure.entity.CodeNaf;
import fr.shoppo.ms_commerce.infrastructure.entity.Commercant;
import fr.shoppo.ms_commerce.infrastructure.entity.Commerce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CommerceServiceImpl implements CommerceService {
    private static final Logger logger = LoggerFactory.getLogger(CommerceServiceImpl.class);

    private final CommerceDao commerceDao;
    private final CommercantDao commercantDao;
    private final CodeNafDao codeNafDao;

    public CommerceServiceImpl(
            CommerceDao commerceDao,
            CodeNafDao codeNafDao,
            CommercantDao commercantDao) {
        this.commerceDao = commerceDao;
        this.codeNafDao = codeNafDao;
        this.commercantDao = commercantDao;
    }

    @Override
    public Optional<Commerce> createOrUpdateCommerce(Commerce commerce) {
        try {
            return Optional.of(commerceDao.save(commerce));
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<CodeNaf> createCodeNaf(CodeNaf codeNaf) {
        try {
            return Optional.of(codeNafDao.save(codeNaf));
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Commerce> deleteCommercant(Commerce commerce, Commercant commercant) {
        try {
            var commercantDB = commercantDao.findByEmail(commercant.getEmail());
            commercantDao.deleteById(commercantDB.getId());
            return Optional.of(commercantDB.getCommerce());
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Commerce> getAllCommercesForClient() {
        var allCommerce = commerceDao.findAll();
        allCommerce.forEach(e -> e.setSirenCode(null));
        return StreamSupport
                .stream(allCommerce.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Commerce> findBySiret(String siret) {
        return commerceDao.findBySiretCode(siret);
    }

    @Override
    public Optional<CodeNaf> findCodeNaf(CodeNaf codeNaf) {
        return codeNafDao.findByCode(codeNaf.getCode());
    }


}
