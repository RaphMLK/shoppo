package fr.shoppo.ms_commerce.infrastructure.entity;

import fr.shoppo.ms_commerce.infrastructure.entity.embeddable.Adresse;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.hibernate.annotations.CascadeType.ALL;

@Entity
public class Commerce implements Serializable {

    @Column
    String sirenCode;

    @Id
    String siretCode;

    @Column
    String enseigne;

    @Column
    String lienPhoto;

    @Column(length = 1500)
    String description;

    @ManyToOne
    @Cascade(ALL)
    @JoinColumn(name = "ref_code_naf", nullable = false)
    CodeNaf codeNaf;

    @Embedded
    Adresse adresse;

    @ManyToMany
    @JoinColumn
    @Cascade(ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    List<Horaire> horaire = new ArrayList<>();


    public Commerce generateBaseHoraire(){
        if(horaire.size() <= 0) {
            List.of("lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi", "dimanche")
                    .forEach(jour ->
                        this.horaire.add(
                                Horaire.of(jour)
                        )
                    );
        }
        return this;
    }

    public static Commerce of(){
        return of(null,null,null);
    }
    public static Commerce of(
            String sirenCode,
            String siretCode,
            String enseigne) {
        return of(sirenCode,siretCode,enseigne,null,null,null,null,null);
    }
    public static Commerce of(
            String sirenCode,
            String siretCode,
            String enseigne,
            String lienPhoto,
            String description,
            CodeNaf codeNaf,
            Adresse adresse,
            List<Horaire> horaire
    ) {
        var c = new Commerce();
        c.sirenCode = sirenCode;
        c.siretCode = siretCode;
        c.enseigne = enseigne;
        c.lienPhoto = lienPhoto;
        c.description = description;
        c.codeNaf = codeNaf;
        c.adresse = adresse;
        c.horaire = horaire;
        return c;
    }

    public String getSirenCode() {
        return sirenCode;
    }

    public void setSirenCode(String sirenCode) {
        this.sirenCode = sirenCode;
    }

    public String getSiretCode() {
        return siretCode;
    }

    public void setSiretCode(String siretCode) {
        this.siretCode = siretCode;
    }

    public String getEnseigne() {
        return enseigne;
    }

    public void setEnseigne(String enseigne) {
        this.enseigne = enseigne;
    }

    public String getLienPhoto() {
        return lienPhoto;
    }

    public void setLienPhoto(String lienPhoto) {
        this.lienPhoto = lienPhoto;
    }

    public CodeNaf getCodeNaf() {
        return codeNaf;
    }

    public void setCodeNaf(CodeNaf codeNaf) {
        this.codeNaf = codeNaf;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Horaire> getHoraire() {
        return horaire;
    }

    public void setHoraire(List<Horaire> horaire) {
        this.horaire = horaire;
    }

    public Commerce() {
        /*for binding*/
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commerce commerce = (Commerce) o;
        return Objects.equals(siretCode, commerce.siretCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sirenCode);
    }

    public boolean equalsSirets(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commerce commerce = (Commerce) o;
        return Objects.equals(siretCode, commerce.siretCode);
    }
}
