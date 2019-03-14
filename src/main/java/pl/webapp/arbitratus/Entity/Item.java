package pl.webapp.arbitratus.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="Item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ITEM_ID")
    private long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "PRICE")
    private float price;
    @Column(name = "SHOP")
    private String shop;
    @ManyToOne
    @JoinColumn(name = "SHOPPINGLIST_ID")
    @JsonIgnore
    private ShoppingList shoppinglist;

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

    public void setName(String name) {
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

    public ShoppingList getShoppinglist() {
        return shoppinglist;
    }

    public void setShoppinglist(ShoppingList shoppinglist) {
        this.shoppinglist = shoppinglist;
    }
}
