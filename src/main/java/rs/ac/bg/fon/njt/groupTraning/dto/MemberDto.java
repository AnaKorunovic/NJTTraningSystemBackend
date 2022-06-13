package rs.ac.bg.fon.njt.groupTraning.dto;

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
public class MemberDto {
    
    private Long id;
    
    private String name;
    private String lastname;
    private String number;
    private String jmbg;
    private Group groupId;
    private String user;
}
