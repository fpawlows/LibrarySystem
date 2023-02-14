package at.ac.fhsalzburg.swd.spring.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import at.ac.fhsalzburg.swd.spring.model.Location;
import at.ac.fhsalzburg.swd.spring.model.medias.Book;
import at.ac.fhsalzburg.swd.spring.model.medias.Media;
import at.ac.fhsalzburg.swd.spring.repository.MediaRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class MediaServiceTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MediaRepository bookRepository;

    @Test
    public void addNewBook() {
        // given
        Book givenMedia = new Book("asdf", "asdf", 0, 1234, "A");
        entityManager.persist(givenMedia);
        entityManager.flush();

        // when
        Optional<Media> optionalMedia = bookRepository.findById(givenMedia.getId());

        // then
        if (optionalMedia.isEmpty()){
            assertTrue(false);
        }
        else {
            Media foundMedia = optionalMedia.get();
            assertEquals(givenMedia, foundMedia);
        }
    }
}
