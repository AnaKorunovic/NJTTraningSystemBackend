package rs.ac.bg.fon.njt.groupTraning.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "members")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Member {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String lastname;
    private String number;
    private String jmbg;
    
//    @ManyToOne
//    @JoinColumn(name = "group")
    @Column(name="[groupId]")
    private Long groupId;
    
//    @ManyToOne
//    @JoinColumn(name = "user")
//    private User user;
    private Long user;

    public Member( String name, String lastname, String number, String jmbg, Long group, Long user) {
        this.name = name;
        this.lastname = lastname;
        this.number = number;
        this.jmbg = jmbg;
        this.groupId = group;
        this.user = user;
    }
    
    
}
