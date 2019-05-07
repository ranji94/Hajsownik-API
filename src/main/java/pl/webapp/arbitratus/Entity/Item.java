package pl.webapp.arbitratus.Entity;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.text.WordUtils;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="ITEM")
@Getter
@Setter
public class Item { //implements Serializable
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    private long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "PRICE")
    private float price;
    @Column(name = "SHOP")
    private String shop;
    @Column(name = "APPROVED")
    private boolean approved = true; //DOMYSLNIE BĘDZIE FALSE, CHODZI O TO ŻE ADMINISTRATOR BĘDZIE ZATWIERDZAŁ
    // CZY PRODUKT DODANY PRZEZ UŻYTKOWNIKA TRAFI DO OGÓLNEJ BAZY Z KTÓREJ BĘDZIE SIĘ WYSZUKIWAŁO PRODUKTY
    @OneToMany(mappedBy = "item")
    List<ItemShoppinglist> itemShoppinglists;

    public Item(){
    }

    public void setName(String name)
    {
        this.name = WordUtils.capitalizeFully(name).trim();
    }

    public void setShop(String shop) {
        this.shop = WordUtils.capitalizeFully(shop).trim();
    }
}
