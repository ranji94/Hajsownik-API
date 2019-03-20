package pl.webapp.arbitratus.Entity;

import javax.persistence.*;

@Entity
@Table(name="OBLIGATION")
public class Obligation { //TUTAJ OGÓLNIE BĘDĄ ROZLICZENIA TZN KTO KOMU ILE HAJSU WISI
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="OBLIGATION_ID")
    private long id;
    @Column(name="WHO", unique = true)
    private String username;
}
