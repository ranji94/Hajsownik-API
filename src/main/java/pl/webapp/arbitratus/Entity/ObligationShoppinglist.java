package pl.webapp.arbitratus.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="OBLIGATIONSHOPPINGLIST")
public class ObligationShoppinglist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="OBLIGATIONSHOPPINGLIST_ID")
    private long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="OBLIGATION_ID")
    @JsonIgnore
    private Obligation obligation;  //TUTAJ BĘDA WSZYSTKIE OBLIGACJE W DANEJ LISCIE ZAKUPOWEJ
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="SHOPPINGLIST_ID")
    @JsonIgnore
    private Shoppinglist shoppinglist;  //LISTA ZAKUPOWA

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Obligation getObligation() {
        return obligation;
    }

    public void setObligation(Obligation obligation) {
        this.obligation = obligation;
    }

    public Shoppinglist getShoppinglist() {
        return shoppinglist;
    }

    public void setShoppinglist(Shoppinglist shoppinglist) {
        this.shoppinglist = shoppinglist;
    }
}
