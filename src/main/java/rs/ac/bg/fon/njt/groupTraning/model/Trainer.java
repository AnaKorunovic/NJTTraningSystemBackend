package rs.ac.bg.fon.njt.groupTraning.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
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
@ToString
@AllArgsConstructor
public class Trainer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String lastname;
    private String number;
    private String email;
   
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;
    
}
