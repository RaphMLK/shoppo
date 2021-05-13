package fr.shoppo.msclient.infrastructure.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
public class VfpTransport implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "id_client", referencedColumnName = "id")
    private Client client;

    @Column
    private Timestamp date;

    public VfpTransport() {
        /*binding*/
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

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public static VfpTransport of(Client client){
        var in = new VfpTransport();
        in.setClient(client);
        in.setDate(new Timestamp(System.currentTimeMillis()));
        return in;
    }
}
