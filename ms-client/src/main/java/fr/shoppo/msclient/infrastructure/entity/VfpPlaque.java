package fr.shoppo.msclient.infrastructure.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
public class VfpPlaque implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "plaque", referencedColumnName = "plaque")
    private Client client;

    @Column
    private Timestamp dateHoraire;

    public VfpPlaque() {
    }

    public static VfpPlaque of(Client client) {
        var in = new VfpPlaque();
        in.setClient(client);
        in.setDateHoraire(new Timestamp(System.currentTimeMillis()));
        return in;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Timestamp getDateHoraire() {
        return dateHoraire;
    }

    public void setDateHoraire(Timestamp dateHoraire) {
        this.dateHoraire = dateHoraire;
    }
}
