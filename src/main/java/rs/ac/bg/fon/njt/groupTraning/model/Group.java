package rs.ac.bg.fon.njt.groupTraning.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "groups")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Group {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
//    @ManyToOne
//    @JoinColumn(name = "trainer")
    private Long trainer;
   
//    @ManyToOne
//    @JoinColumn(name = "user")
//    private User user;
    private Long user;

    public Group( String name, Long trainer, Long user) {
        this.id = id;
        this.name = name;
        this.trainer = trainer;
        this.user = user;
    }

    
    
    
}
