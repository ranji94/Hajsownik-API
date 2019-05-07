package pl.webapp.arbitratus.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="OBLIGATIONSHOPPINGLIST")
@Getter
@Setter
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
}
