package fr.shoppo.msclient.domain.bo;

import fr.shoppo.msclient.infrastructure.dao.VfpPlaqueDao;
import fr.shoppo.msclient.infrastructure.dao.VfpTransportDao;
import fr.shoppo.msclient.infrastructure.entity.Client;
import fr.shoppo.msclient.infrastructure.entity.VfpPlaque;
import fr.shoppo.msclient.infrastructure.entity.VfpTransport;
import org.springframework.data.repository.CrudRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SuppressWarnings(value = "rawtypes")
public enum AvantageVFP {
    NONE("Aucun avantage"),/*ne jamais changer de place !*/
    USED("Avantage quotidien : already used"),/*ne jamais changer de place !*/

    /*TRIGGER !*/
    PARK((client,daos) -> {
        var plaqueDao = (VfpPlaqueDao)daos.get("PARK");
        plaqueDao.save(VfpPlaque.of(client));

        System.out.printf("SET PARK to client %s",client.getEmail());
    },"Parking gratuit",USED),
    TICKET((client,daos) -> {
        var transportDao = (VfpTransportDao)daos.get("TICKET");
        transportDao.save(VfpTransport.of(client));

        System.out.printf("SET TICKET to client %s",client.getEmail());
    },"Tickets de metro gratuits",USED),
    LOSE((client,daos) -> {

        /*perte des avantages*/
        /* supprime la plaque d'immat des immat autorisé*/
        /* supprime l'utilisateur du ref des tickets de metro*/
    },"(temporaire)",NONE),

    ;

    BiConsumer<Client,Map<String,? extends CrudRepository>> value;
    String description;

    AvantageVFP(String description){
        value = (c,daos) -> {};
        this.description = description;
    }

    AvantageVFP(
            BiConsumer<Client,Map<String,? extends CrudRepository>> value,
            String description,
            AvantageVFP next
    ) {
        this.value = value.andThen((client,dao) -> client.setAvantage(next));
        this.description = description;
    }

    public static AvantageVFP fromDescription(String description){
        return from(e -> e.description.equalsIgnoreCase(description));
    }

    public static AvantageVFP fromName(String name){
        return from(e -> e.name().equalsIgnoreCase(name));
    }

    public boolean matchWithDescriptionOrName(String str){
        return this.description.equalsIgnoreCase(str) || this.name().equalsIgnoreCase(str);
    }

    public static AvantageVFP from(Predicate<AvantageVFP> filter){
        return Arrays.stream(values())
                .filter(filter)
                .findFirst().orElse(NONE);
    }

    public <T extends Map<String,? extends CrudRepository>> void run(Client client, T daos){
        this.value.accept(client,daos);
    }

    public static List<AvantageOutput> listAllAvantages(){
        return Arrays
                .stream(values())
                .filter(e -> !e.equals(USED) && !e.equals(LOSE) && !e.equals(NONE))
                .map(AvantageOutput::new)
                .collect(Collectors.toList());
    }
}
