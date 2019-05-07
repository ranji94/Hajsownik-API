package pl.webapp.arbitratus.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="APPUSER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USER_ID")
    private long id;
    @Column(name="USERNAME", unique = true)
    private String username;
    @Column(name="PASSWORD")
    private String password;
    @OneToMany(mappedBy = "user")
    List<UserShoppinglist> userShoppinglists;

    public User() {
    }

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

    public List<UserShoppinglist> getUserShoppinglists() {
        return userShoppinglists;
    }

    public void setUserShoppinglists(List<UserShoppinglist> userShoppinglists) {
        this.userShoppinglists = userShoppinglists;
    }
}
