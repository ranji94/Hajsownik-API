package pl.webapp.arbitratus.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="ITEM_SHOPPINGLIST")
@Getter
@Setter
public class ItemShoppinglist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ITEMSHOPPINGLIST_ID")
    private long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="ITEM_ID")
    @JsonIgnore
    private Item item;  //JAKI PRZEDMIOT DODANO DO...
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="SHOPPINGLIST_ID")
    @JsonIgnore
    private Shoppinglist shoppinglist; //...JAKIEJ LISTY
    @Column(name="QUANTITY")
    private int quantity;  //W ILOSCI
    @Column(name="TOTAL")
    private float total;  //ILOSC RAZY CENA PRZEDMIOTU, WYPELNIA SIĘ SAMO PO UŻYCIU METODY ZAIMPLEMENTOWANIEJ W SHOPPINGSERVICE
}
