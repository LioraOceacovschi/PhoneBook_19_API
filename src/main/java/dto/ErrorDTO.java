package dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class ErrorDTO {
    int status;
    String error;
    String message;
}
