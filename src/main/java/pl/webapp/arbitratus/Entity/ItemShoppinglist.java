package pl.webapp.arbitratus.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="ITEM_SHOPPINGLIST")
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Shoppinglist getShoppinglist() {
        return shoppinglist;
    }

    public void setShoppinglist(Shoppinglist shoppinglist) {
        this.shoppinglist = shoppinglist;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

}
