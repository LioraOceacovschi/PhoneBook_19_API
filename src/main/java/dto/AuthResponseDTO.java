package dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class AuthResponseDTO {
    String token;
}
