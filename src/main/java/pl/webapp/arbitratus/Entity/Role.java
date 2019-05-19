package pl.webapp.arbitratus.Entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import pl.webapp.arbitratus.Enum.RoleName;

import javax.persistence.*;

@Entity
@Table(name="ROLES")
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
    private RoleName name;
    public Role(){}
    public Role(RoleName name) {this.name=name;}
}
