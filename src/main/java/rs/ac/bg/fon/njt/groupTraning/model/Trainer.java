package rs.ac.bg.fon.njt.groupTraning.model;

import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "trainers")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Trainer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String lastname;
    private String number;
    private String email;
   
//    @ManyToOne
//    @JoinColumn(name = "user")
//    private User user;
    private Long user;

    public Trainer( String name, String lastname, String number, String email, Long user) {
        this.name = name;
        this.lastname = lastname;
        this.number = number;
        this.email = email;
        this.user = user;
    }

    @Override
    public String toString() {
        return name+" "+lastname+"/"+email;
    }
    
    
    
    
}
