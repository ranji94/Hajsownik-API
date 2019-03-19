package pl.webapp.arbitratus.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.*;

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
    List<ItemShoppinglist> itemShoppinglists;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="User_Shoppinglist", joinColumns = @JoinColumn(name="SHOPPINGLIST_ID"), inverseJoinColumns = @JoinColumn(name="USER_ID"))
    @JsonIgnore
    private List<User> users = new ArrayList<>();



    public Shoppinglist() {}

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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<ItemShoppinglist> getItemShoppinglists() {
        return itemShoppinglists;
    }

    public void setItemShoppinglists(List<ItemShoppinglist> itemShoppinglists) {
        this.itemShoppinglists = itemShoppinglists;
    }
}
