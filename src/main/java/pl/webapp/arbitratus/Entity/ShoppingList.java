package pl.webapp.arbitratus.Entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="SHOPPINGLIST")
public class ShoppingList{
    @Id
    @GeneratedValue
    @Column(name="ID")
    private long id;
    @Column(name="CREATED_AT")
    @CreationTimestamp
    private Date createdAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="shoppinglist")
    private Set<Item> items;

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

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}
