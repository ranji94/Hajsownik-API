package pl.webapp.arbitratus.Entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="SHOPPINGLIST")
public class Shoppinglist { //implements Serializable
    private static final long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="SHOPPINGLIST_ID")
    private long id;
    @Column(name="CREATED_AT")
    @CreationTimestamp
    private Date createdAt;
    //@ManyToMany(cascade = CascadeType.ALL)
    //@JoinTable(name="Item_ShoppingList", joinColumns = @JoinColumn(name="SHOPPINGLIST_ID"),inverseJoinColumns = @JoinColumn(name="ITEM_ID"))
    //List<Item> items = new ArrayList<>();
    @OneToMany(mappedBy = "shoppinglist")
    List<ItemShoppinglist> itemShoppinglists;  //ENCJA DO POWIĄZANIA LIST ZAKUPOWYCH Z PRZEDMIOTAMI
    @Column(name="LISTTOTAL")
    private Float listtotal;  //TO JEST SUMA WSZYSTKICH "TOTALI" Z ITEMSHOPPINGLIST
    @OneToMany(mappedBy = "shoppinglist")
    List<UserShoppinglist> userShoppinglists;  //ENCJA DO POWIĄZANIA LIST ZAKUPOWYCH Z UZYTKOWNIKAMI KTÓRZY BĘDĄ BULIC HAJS
    @OneToMany(mappedBy = "shoppinglist")
    List<ObligationShoppinglist> obligationShoppinglists;
    @Column(name="EDITED")
    boolean edited=false;
    public Shoppinglist() {}

    public static long getSerialVersionUID() {
        return SerialVersionUID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<ItemShoppinglist> getItemShoppinglists() {
        return itemShoppinglists;
    }

    public void setItemShoppinglists(List<ItemShoppinglist> itemShoppinglists) {
        this.itemShoppinglists = itemShoppinglists;
    }

    public Float getListtotal() {
        return listtotal;
    }

    public void setListtotal(Float listtotal) {
        this.listtotal = listtotal;
    }

    public List<UserShoppinglist> getUserShoppinglists() {
        return userShoppinglists;
    }

    public void setUserShoppinglists(List<UserShoppinglist> userShoppinglists) {
        this.userShoppinglists = userShoppinglists;
    }

    public List<ObligationShoppinglist> getObligationShoppinglists() {
        return obligationShoppinglists;
    }

    public void setObligationShoppinglists(List<ObligationShoppinglist> obligationShoppinglists) {
        this.obligationShoppinglists = obligationShoppinglists;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }
}
