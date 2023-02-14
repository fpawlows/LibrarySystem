package at.ac.fhsalzburg.swd.spring.test;

import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import antlr.collections.List;

import static org.mockito.BDDMockito.*;


import at.ac.fhsalzburg.swd.spring.model.User;
import at.ac.fhsalzburg.swd.spring.model.medias.Book;
import at.ac.fhsalzburg.swd.spring.model.medias.Media;
import at.ac.fhsalzburg.swd.spring.repository.CompartmentRepository;
import at.ac.fhsalzburg.swd.spring.repository.CopyRepository;
import at.ac.fhsalzburg.swd.spring.repository.GenreRepository;
import at.ac.fhsalzburg.swd.spring.repository.LocationRepository;
import at.ac.fhsalzburg.swd.spring.repository.MediaRepository;
import at.ac.fhsalzburg.swd.spring.repository.ShelfRepository;
import at.ac.fhsalzburg.swd.spring.repository.UserRepository;
import at.ac.fhsalzburg.swd.spring.services.MediaService;
import at.ac.fhsalzburg.swd.spring.services.MediaServiceInterface;
import at.ac.fhsalzburg.swd.spring.services.UserService;
import at.ac.fhsalzburg.swd.spring.services.UserServiceInterface;

import java.time.ZoneId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
@ActiveProfiles("test")
public class MediaServiceTest {

	private MediaRepository mediaRepo;
	private MediaService mediaService;
	private GenreRepository genreRepo;
	private CopyRepository copyRepo;
	private LocationRepository locationReop;
	private CompartmentRepository compartmentRepo;
	private ShelfRepository shelfRepo;
	



	@BeforeEach
	void setupService() {
	    mediaRepo = mock(MediaRepository.class);
	    genreRepo = mock(GenreRepository.class);
		copyRepo = mock(CopyRepository.class);
		locationReop = mock(LocationRepository.class);
		compartmentRepo = mock(CompartmentRepository.class);
		shelfRepo = mock(ShelfRepository.class);
		
	    String defaultDesc = "Default Media Description";
	    java.util.List<Integer> fskValues = Arrays.asList(12, 16, 18);
	    mediaService = new MediaService(mediaRepo, copyRepo, locationReop, compartmentRepo, shelfRepo, genreRepo, defaultDesc, fskValues);

	}

	@Test
	public void whenAddingMedia_thenReturnMedia() {
	    // given
		Date now = new Date();
		Book media = new Book(12, "TestAuthor", "Name", "Description", 12, now, null, null, null);
		mediaService.addMedia(media);

	    given(mediaRepo.save(any(Media.class))).willReturn(media);

	    // when
	    boolean mediaActual = mediaService.addBook(12, "TestAuthor", "Name", "Description", 12, now, null, null, null);

	    // then
	    assertTrue(mediaActual);

}}