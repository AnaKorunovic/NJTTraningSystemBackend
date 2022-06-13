package rs.ac.bg.fon.njt.groupTraning.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import rs.ac.bg.fon.njt.groupTraning.model.Group;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AppointmentDto {
    
    private Long id;
    private Date date;
    private String time;
    private Group groupId;
    private String user;
}
