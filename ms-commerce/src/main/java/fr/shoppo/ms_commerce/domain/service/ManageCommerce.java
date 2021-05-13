package fr.shoppo.ms_commerce.domain.service;

import fr.shoppo.ms_commerce.infrastructure.entity.CodeNaf;
import fr.shoppo.ms_commerce.infrastructure.entity.Commercant;
import fr.shoppo.ms_commerce.infrastructure.entity.Commerce;
import fr.shoppo.ms_commerce.infrastructure.exception.CommerceException;
import fr.shoppo.ms_commerce.infrastructure.service.PasswordManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static fr.shoppo.ms_commerce.domain.constant.MessageConstantEnum.*;

@Component
public class ManageCommerce {

    final CommerceService commerceService;
    final CommercantService commercantService;
    final InseeInformationService inseeInformationService;
    PasswordManager passwordManager;

    public ManageCommerce(CommerceService commerceService, InseeInformationService inseeInformationService, CommercantService commercantService) {
        this.commerceService = commerceService;
        this.inseeInformationService = inseeInformationService;
        this.commercantService = commercantService;
    }

    public boolean isValidInputForCreatingCommerce(
            Commercant commercant
    ){
        var commerce = commercant != null ? commercant.getCommerce() : null;
        return commerce == null
                || commerce.getSiretCode() == null
                || commercant.getEmail() == null;
    }

    public Optional<Commerce> buildCommerce(
            String siret,
            Commercant commercant,
            boolean isCreation
    ) throws CommerceException {
        commercant.setChangePassword(true);
        var containerCommerce = new AtomicReference<Commerce>();
        var containerException = new AtomicReference<CommerceException>(null);
        commerceService
                .findBySiret(siret)
                .ifPresentOrElse(
                    commerce -> {
                        if(isCreation)
                            containerException.set(new CommerceException(ERREUR_COMMERCE_NOT_CREATED));
                        else {
                            commercant.setOwner(commerce == null);
                            var commercants = commercantService
                                    .findByCommerce(commerce)
                                    .orElse(List.of());
                            if (commercants.contains(commercant))
                                containerException.set(new CommerceException(ERREUR_COMMERCANT_NOT_ADDED));
                            else {
                                containerCommerce.set(commerce);
                            }
                        }
                    },
                    () -> {
                        if(!isCreation)
                            containerException.set(new CommerceException(ERREUR_COMMERCE_NOT_FOUND));
                        else {
                            commercant.setOwner(true);
                            var commerce = inseeInformationService.findCommerceBySiret(siret);
                            if (commerce != null) {
                                commerce.setCodeNaf(buildCodeNaf(commerce));
                                commerce.generateBaseHoraire();
                            }
                            containerCommerce.set(commerce);

                        }
                    }
                );
        if(containerException.get() != null)
            throw containerException.get();

        var commerce = containerCommerce.get();
        if (commerce == null) return Optional.empty();

        var clearPass = passwordManager.generateSecureRandomPassword();
        commercant.setPassword(passwordManager.hash(clearPass));

        return commerceService.createOrUpdateCommerce(commerce)
                .stream()
                .peek(c -> commercantService.save(commercant))
                .peek(c -> putClearPasswordToOwner(commercant,clearPass))
                .findFirst();
    }

    public void putClearPasswordToOwner(Commercant commercant, String clearPass) {
        commercant.setPassword(clearPass);
    }

    public CodeNaf buildCodeNaf(Commerce commerce){
        var codeNafCommerce = commerce.getCodeNaf();
        var codeNaf = commerceService.findCodeNaf(commerce.getCodeNaf());
        return codeNaf
                .orElse(inseeInformationService.existingCodeNaf(codeNafCommerce.getCode()));
    }

    public Optional<Commerce> deleteCommercant(
            String siret,
            Commercant commercant
    ) throws CommerceException {
        var commerce = commerceService
                .findBySiret(siret)
                .orElseThrow(() -> new CommerceException(ERREUR_COMMERCE_NOT_FOUND));
        var commercants = commercantService
                .findByCommerce(commerce)
                .orElseThrow(() -> new CommerceException(ERREUR_COMMERCE_NOT_FOUND));

        if(commercants.stream().filter(Commercant::isOwner).anyMatch(commercant::equals))
            throw new CommerceException(ERREUR_COMMERCANT_OWNER_NOT_DELETED);

        return commerceService.deleteCommercant(commerce,commercant);
    }

    public Optional<Commerce> updateCommerce(Commerce commerce){
        var inDB = commerceService.findBySiret(commerce.getSiretCode());
        inDB.ifPresent(commerce1 -> {

            commerce1.getHoraire().forEach( hr -> {
                var hrUpd = commerce.getHoraire();
                var hrUpdUnit = hrUpd.get(hrUpd.indexOf(hr));
                hr.setHeureDebut(hrUpdUnit.getHeureDebut());
                hr.setHeureDebut2(hrUpdUnit.getHeureDebut2());
                hr.setHeureFin(hrUpdUnit.getHeureFin());
                hr.setHeureFin2(hrUpdUnit.getHeureFin2());
                hr.setFermetureExceptionnelle(hrUpdUnit.isFermetureExceptionnelle());
            });

            commerce1.setDescription(commerce.getDescription());
            commerce1.setLienPhoto(commerce.getLienPhoto());
            commerce1.setEnseigne(commerce.getEnseigne());
            commerce1.setAdresse(commerce.getAdresse());
            commerceService.createOrUpdateCommerce(commerce1);
        });
        return inDB;
    }


    @Autowired
    public void setPasswordGenerator(PasswordManager passwordManager) {
        this.passwordManager = passwordManager;
    }
}
