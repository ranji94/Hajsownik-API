package pl.webapp.arbitratus.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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
    @OneToMany(mappedBy = "obligation")
    List<ObligationShoppinglist> obligationShoppinglists;
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

    public List<ObligationShoppinglist> getObligationShoppinglists() {
        return obligationShoppinglists;
    }

    public void setObligationShoppinglists(List<ObligationShoppinglist> obligationShoppinglists) {
        this.obligationShoppinglists = obligationShoppinglists;
    }
}
