package pl.webapp.arbitratus.Entity;

import javax.persistence.*;

@Entity
@Table(name="OBLIGATION")
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

    public Obligation(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getWhom() {
        return whom;
    }

    public void setWhom(String whom) {
        this.whom = whom;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
