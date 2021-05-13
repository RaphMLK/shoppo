package fr.shoppo.ms_stat.infrastructure.implementation;

import fr.shoppo.ms_stat.domain.ClientService;
import fr.shoppo.ms_stat.infrastructure.dao.ClientDao;
import fr.shoppo.ms_stat.infrastructure.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    ClientDao clientDao;

    public int countVFPClient(){
        return clientDao.findAllByStatusVFP(true).size();
    }

    @Override
    public Optional<Client> findById(int id) {
        return clientDao.findById(id);
    }

    @Autowired
    public void setClientDao(ClientDao clientDao) {
        this.clientDao = clientDao;
    }
}
