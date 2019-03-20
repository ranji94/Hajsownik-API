package pl.webapp.arbitratus.Entity;

import org.apache.commons.text.WordUtils;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="ITEM")
public class Item { //implements Serializable
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    private long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "PRICE")
    private float price;
    @Column(name = "SHOP")
    private String shop;
    @Column(name = "APPROVED")
    private boolean approved = true; //DOMYSLNIE BĘDZIE FALSE, CHODZI O TO ŻE ADMINISTRATOR BĘDZIE ZATWIERDZAŁ
    // CZY PRODUKT DODANY PRZEZ UŻYTKOWNIKA TRAFI DO OGÓLNEJ BAZY Z KTÓREJ BĘDZIE SIĘ WYSZUKIWAŁO PRODUKTY
    @OneToMany(mappedBy = "item")
    List<ItemShoppinglist> itemShoppinglists;

    public Item(){
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public List<ItemShoppinglist> getItemShoppinglists() {
        return itemShoppinglists;
    }

    public void setItemShoppinglists(List<ItemShoppinglist> itemShoppinglists) {
        this.itemShoppinglists = itemShoppinglists;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
