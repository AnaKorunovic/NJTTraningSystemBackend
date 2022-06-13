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
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class UserDto {
    Long id;
    String username;
    String name;
    String lastname;
    String token;
    
}
