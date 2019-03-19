package pl.webapp.arbitratus.Entity;

import org.apache.commons.text.WordUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Item")
public class Item implements Serializable {
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
    @ManyToMany(mappedBy = "items")
    List<ShoppingList> shoppinglists = new ArrayList<>();

    public List<ShoppingList> getShoppinglists() {
        return shoppinglists;
    }

    public void setShoppinglists(List<ShoppingList> shoppinglists) {
        this.shoppinglists = shoppinglists;
    }

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
        this.name = WordUtils.capitalizeFully(name.trim());
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
        this.shop = WordUtils.capitalizeFully(shop.trim());
    }

}
