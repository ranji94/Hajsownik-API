package pl.webapp.arbitratus.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="APPUSER")
@Getter
@Setter
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
}
