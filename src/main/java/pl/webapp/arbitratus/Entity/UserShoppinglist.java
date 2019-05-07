package pl.webapp.arbitratus.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="USERSHOPPINGLIST")
@Getter
@Setter
public class UserShoppinglist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USERSHOPPINGLIST_ID")
    private long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="USER_ID")
    @JsonIgnore
    private User user;  //TUTAJ BĘDA WSZYSTKIE OSOBY KTÓRE BEDA WSPÓŁDZIELIC KOSZTY ZA DANĄ LISTE ZAKUPOWA
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="SHOPPINGLIST_ID")
    @JsonIgnore
    private Shoppinglist shoppinglist;  //LISTA ZAKUPOWA
    @Column(name="OWNER")
    private String owner;  //OSOBA KTORA PLACILA I NA KORZYSC TEJ OSOBY BEDA DLUGI POWYZSZYCH USEROW
}
