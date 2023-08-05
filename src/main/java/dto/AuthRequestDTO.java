package dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class AuthRequestDTO {
    String username;
    String password;
}
