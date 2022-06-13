package rs.ac.bg.fon.njt.groupTraning.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import rs.ac.bg.fon.njt.groupTraning.model.Appointment;
import rs.ac.bg.fon.njt.groupTraning.model.Member;
        
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PresenceDto {
    
    
    private Boolean presence;
    private String message;
    private Appointment appointment;
    private Member member;
    private String user;
}
