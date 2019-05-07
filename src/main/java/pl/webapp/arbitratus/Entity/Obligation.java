package pl.webapp.arbitratus.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="OBLIGATION")
@Getter
@Setter
public class Obligation { //TUTAJ OGÓLNIE BĘDĄ ROZLICZENIA TZN KTO KOMU ILE HAJSU WISI
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="OBLIGATION_ID")
    private long id;
    @Column(name="WHO")
    private String who;
    @Column(name="WHOM")
    private String whom;
    @Column(name="AMOUNT")
    private float amount = 0;
    @OneToMany(mappedBy = "obligation")
    List<ObligationShoppinglist> obligationShoppinglists;
    public Obligation(){}
}
