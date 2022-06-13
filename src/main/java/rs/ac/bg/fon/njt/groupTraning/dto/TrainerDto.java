package rs.ac.bg.fon.njt.groupTraning.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TrainerDto {
    
    private Long id;
    private String name;
    private String lastname;
    private String number;
    private String email;
    private String user;
}
