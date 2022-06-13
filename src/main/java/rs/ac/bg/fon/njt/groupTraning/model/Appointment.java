package rs.ac.bg.fon.njt.groupTraning.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Appointment {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Date date;
    private String time;

    @Column(name="[groupId]")
    private Long groupId;
    
    private Long user;

    public Appointment(Date date, String time, Long groupId, Long user) {
        this.date = date;
        this.time = time;
        this.groupId = groupId;
        this.user = user;
    }
    
    
}
