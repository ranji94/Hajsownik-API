package pl.webapp.arbitratus.Entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="SHOPPINGLIST")
@Getter
@Setter
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
}
