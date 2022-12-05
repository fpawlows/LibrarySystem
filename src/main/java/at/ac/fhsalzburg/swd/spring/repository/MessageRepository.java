package at.ac.fhsalzburg.swd.spring.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.ac.fhsalzburg.swd.spring.model.Message;
import at.ac.fhsalzburg.swd.spring.model.ids.MessageId;


@Repository
public interface MessageRepository extends CrudRepository<Message, MessageId> {

	@Transactional(timeout = 10)
    Optional<Message> findById(MessageId id);

}
