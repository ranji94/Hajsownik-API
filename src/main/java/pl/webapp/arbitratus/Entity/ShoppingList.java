package pl.webapp.arbitratus.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name="SHOPPINGLIST")
public class ShoppingList implements Serializable {
    private static final long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="SHOPPINGLIST_ID")
    private long id;
    @Column(name="CREATED_AT")
    @CreationTimestamp
    private Date createdAt;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="Item_ShoppingList", joinColumns = @JoinColumn(name="SHOPPINGLIST_ID"),inverseJoinColumns = @JoinColumn(name="ITEM_ID"))
    List<Item> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="USER_ID", nullable = true)
    @JsonIgnore
    private User user;

    public ShoppingList() {}

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
