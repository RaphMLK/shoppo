package fr.shoppo.msclient.infrastructure.service;

import fr.shoppo.msclient.domain.bo.AvantageVFP;
import fr.shoppo.msclient.domain.service.CheckerClient;
import fr.shoppo.msclient.domain.service.ManageClientService;
import fr.shoppo.msclient.domain.service.VFPStateManager;
import fr.shoppo.msclient.infrastructure.dao.ClientDao;
import fr.shoppo.msclient.infrastructure.entity.Client;
import fr.shoppo.msclient.infrastructure.exception.ClientErrorLogin;
import fr.shoppo.msclient.infrastructure.exception.ClientException;
import fr.shoppo.msclient.infrastructure.exception.ClientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import static fr.shoppo.msclient.domain.bo.AvantageVFP.USED;
import static fr.shoppo.msclient.domain.constant.MessageConstantEnum.ERREUR_INVALID_INPUT;

@Service
public class ManageClientServiceImpl implements ManageClientService {

    private final ClientDao clientDao;
    PasswordManager passwordManager;
    CheckerClient checkerClient;
    VFPStateManager stateManager;

    public ManageClientServiceImpl(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public Client resetPassword(String email) throws ClientException {
        var client = clientDao
                .findByEmail(email)
                .orElseThrow(ClientNotFoundException::new);
        var clearPassword = passwordManager.generateSecureRandomPassword();
        client.setPassword(
                checkerClient.passwordInputParser(
                        clearPassword
                )
        );
        client.setChangePassword(true);
        clientDao.save(client);
        client.setPassword(clearPassword);
        return client;
    }

    @Override
    public Client createClient(Client client) throws ClientException {
        if (!checkerClient.checkClient(client))
            throw new ClientException(ERREUR_INVALID_INPUT);
        if (this.clientDao
                .findByEmail(client.getEmail())
                .isPresent())
            throw new ClientException(ERREUR_INVALID_INPUT);
        client.setChangePassword(false);
        var uuidCorrect = false;
        while (!uuidCorrect) {
            var qrCode = UUID.randomUUID();
            if (this.clientDao
                    .findByQrCode(qrCode)
                    .isEmpty()) {
                client.setQrCode(qrCode);
                uuidCorrect = true;
            }
        }
        client.setChangePassword(true);
        clientDao.save(client);
        return client;
    }

    @Override
    public Client login(String email, String password) throws ClientException {
        var client = this.clientDao
                .findByEmail(email)
                .orElseThrow(ClientNotFoundException::new);
        var hashPass = checkerClient.passwordInputParser(password);
        if (client
                .getPassword()
                .equals(hashPass)
        ) {
            return client;
        }
        throw new ClientErrorLogin();
    }

    @Override
    public Client changePasswordNeed(String email, String password) throws ClientException {
        var client = this.clientDao
                .findByEmail(email)
                .orElseThrow(ClientNotFoundException::new);
        client.setPassword(checkerClient.passwordInputParser(password));
        client.setChangePassword(false);
        clientDao.save(client);
        return client;
    }

    @Override
    public boolean updateSolde(String email, float amount,boolean applyEffect){
        var response = new AtomicBoolean(!applyEffect);

        clientDao
                .findByEmail(email)
                .ifPresent(client -> {
                    if((client.getSolde()+amount)>=0){
                       if(amount<0)
                           stateManager.command(client);
                        if(applyEffect) {
                            client.setSolde(client.getSolde() + amount);
                            clientDao.save(client);
                            response.set(true);
                        }
                    }
                });
        return response.get();
    }

    @Override
    public boolean updateAvantage(String email, String str) throws ClientException {
        var avantage = AvantageVFP.from(e -> e.matchWithDescriptionOrName(str));

        var client = getClientByEmail(email);
        var avantageClient = client.getAvantage();
        var correctWay = client.isStatusVFP() && !USED.equals(avantageClient) &&!avantageClient.equals(avantage) ;
        if(correctWay) {
            client.setAvantage(avantage);
            clientDao.save(client);
        }

        return correctWay ;
    }

    @Override
    public Client getClientByEmail(String email) throws ClientException {
        return this.clientDao.findByEmail(email).orElseThrow(ClientNotFoundException::new);
    }

    @Override
    public Client getClientByQrCode(String qrCode) throws ClientException {
        return this.clientDao.findByQrCode(UUID.fromString(qrCode)).orElseThrow(ClientNotFoundException::new);
    }

    @Autowired
    public void setPasswordManager(PasswordManager passwordManager) {
        this.passwordManager = passwordManager;
    }

    @Autowired
    public void setCheckerClient(CheckerClient checkerClient) {
        this.checkerClient = checkerClient;
    }

    @Autowired
    public void setStateManager(VFPStateManager stateManager) {
        this.stateManager = stateManager;
    }
}
