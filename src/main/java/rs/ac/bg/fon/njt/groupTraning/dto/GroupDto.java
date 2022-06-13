package rs.ac.bg.fon.njt.groupTraning.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import rs.ac.bg.fon.njt.groupTraning.model.Trainer;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GroupDto {
    
    private Long id;
    private String name;
    private Trainer trainer;
    private String user;
}
