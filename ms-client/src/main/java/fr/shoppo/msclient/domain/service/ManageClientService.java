package fr.shoppo.msclient.domain.service;

import fr.shoppo.msclient.infrastructure.entity.Client;
import fr.shoppo.msclient.infrastructure.exception.ClientException;

public interface ManageClientService {
    Client resetPassword(String email) throws ClientException;
    Client createClient(Client client) throws ClientException;
    Client login(String email, String password) throws ClientException;
    Client changePasswordNeed(String email, String password) throws ClientException;
    boolean updateSolde(String email, float amount,boolean applyEffect);
    Client getClientByEmail(String email) throws ClientException;
    Client getClientByQrCode(String qrCode) throws ClientException;
    boolean updateAvantage(String email, String str) throws ClientException;
}
