package dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class ContactDTO {
    String id;
    String name;
    String lastName;
    String phone;
    String email;
    String address;
    String description;
}
