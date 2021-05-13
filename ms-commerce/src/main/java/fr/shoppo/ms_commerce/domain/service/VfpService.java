package fr.shoppo.ms_commerce.domain.service;

import fr.shoppo.ms_commerce.domain.bo.AddVfpByProductGroup;
import fr.shoppo.ms_commerce.domain.bo.ProductVfpDTO;
import fr.shoppo.ms_commerce.infrastructure.entity.ProductVfp;

import java.util.List;
import java.util.Optional;

public interface VfpService {

    String addVfpByProduct(AddVfpByProductGroup addVfpByProductGroup);

    String deleteVfp(int idVfp);

    List<ProductVfpDTO> getVfp(String email);

    Optional<ProductVfp> getVfpById(int id);
}
