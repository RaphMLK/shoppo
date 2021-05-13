package fr.shoppo.mainmsinterface.domain.service;

import fr.shoppo.mainmsinterface.domain.bo.User;
import fr.shoppo.mainmsinterface.domain.bo.client.AvantageInput;
import fr.shoppo.mainmsinterface.domain.bo.client.SoldeInput;

public interface ManageClientService {

    String resetPassword(String email);
    String login(User user);
    String createClient(User user);
    String changePassword(User user);
    String getClient(String email);
    String getClientByQrCode(String qrCode);
    String updateSolde(SoldeInput soldeInput);
    String listAvantage() ;
    String updateAvantage(AvantageInput avantageInput);
    boolean isPark(String plaque);
    boolean isTransport(String qrCode);
}
