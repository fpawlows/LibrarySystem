package at.ac.fhsalzburg.swd.spring.model.ids;

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
@Data //Getters, setters, constructors
public class MessageId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user;

    @Column(name = "message_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageId)) return false;
        MessageId messageId = (MessageId) o;
        return Objects.equals(getUser(), messageId.getUser()) && Objects.equals(getId(), messageId.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getId());
    }
}
