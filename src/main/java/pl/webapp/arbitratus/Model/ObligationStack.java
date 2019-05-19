package pl.webapp.arbitratus.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
public class ObligationStack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private float amount;
    private String name;
}
