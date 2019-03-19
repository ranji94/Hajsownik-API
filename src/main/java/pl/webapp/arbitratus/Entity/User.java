package pl.webapp.arbitratus.Entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="APPUSER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USER_ID")
    private long id;
    @Column(name="USERNAME")
    private String username;
    @Column(name="PASSWORD")
    private String password;
    @OneToMany(mappedBy = "user")
    private Set<ShoppingList> shoppingList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<ShoppingList> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(Set<ShoppingList> shoppingList) {
        this.shoppingList = shoppingList;
    }
}
