package at.ac.fhsalzburg.swd.spring.dto.ids;

import at.ac.fhsalzburg.swd.spring.dto.UserDTO;
import at.ac.fhsalzburg.swd.spring.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTOId implements Serializable {
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private UserDTO user;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageDTOId)) return false;
        MessageDTOId that = (MessageDTOId) o;
        return Objects.equals(user, that.user) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, id);
    }
}
