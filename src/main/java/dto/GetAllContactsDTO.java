package dto;

import lombok.*;

import java.util.List;
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class GetAllContactsDTO {
    List<ContactDTO> contacts;
}
