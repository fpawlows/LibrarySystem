package at.ac.fhsalzburg.swd.spring.util;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import at.ac.fhsalzburg.swd.spring.dto.*;
import at.ac.fhsalzburg.swd.spring.dto.medias.*;
import at.ac.fhsalzburg.swd.spring.model.*;
import at.ac.fhsalzburg.swd.spring.model.ids.CompartmentId;
import at.ac.fhsalzburg.swd.spring.model.ids.CopyId;
import at.ac.fhsalzburg.swd.spring.model.ids.PaymentId;
import at.ac.fhsalzburg.swd.spring.model.medias.*;
import at.ac.fhsalzburg.swd.spring.services.LoanService;
import at.ac.fhsalzburg.swd.spring.services.MediaService;
import at.ac.fhsalzburg.swd.spring.services.PaymentService;
import org.modelmapper.*;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import at.ac.fhsalzburg.swd.spring.services.UserService;

// initial version from https://stackoverflow.com/questions/47929674/modelmapper-mapping-list-of-entites-to-list-of-dto-objects
public class ObjectMapperUtils {

    private final static ModelMapper modelMapper;

    /**
     * Hide from public usage.
     */
    private ObjectMapperUtils() {
    }

    static {
        modelMapper = new ModelMapper();

        // https://github.com/modelmapper/modelmapper/issues/319
        // string blank condition
        Condition<?, ?> isStringBlank = (Condition<?, ?>) new AbstractCondition<Object, Object>() {
            @Override
            public boolean applies(MappingContext<Object, Object> context) {
                if (context.getSource() instanceof String) {
                    return null != context.getSource() && !"".equals(context.getSource());
                } else {
                    return context.getSource() != null;
                }
            }
        };

        // initial configuration
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT)
            .setSkipNullEnabled(true) // skip null fields
            .setFieldMatchingEnabled(true);
            //.setPropertyCondition(isStringBlank); // skip empty strings
        //TODO make sure if this skipping is good ddecision, but without it such instances with empty strings
        // are not mapped - the table is empty in html

        // create a typemap to override default behaviour for DTO to entity mapping
        TypeMap<UserDTO, User> typeMapUser = modelMapper.getTypeMap(UserDTO.class, User.class);
        if (typeMapUser == null) {
            typeMapUser = modelMapper.createTypeMap(UserDTO.class, User.class);
        }
        // create a provider to be able to merge the dto data with the data in the database:
        // whenever we are mapping UserDTO to User, the data from UserDTO and the existing User in the database are merged
        Provider<User> userDelegatingProvider = new Provider<User>() {

            public User get(ProvisionRequest<User> request) {
                // it is also possible to get a service instance from the application context programmatically
                UserService userService = (UserService) WebApplicationContextUtils.getWebApplicationContext(
                        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getServletContext())
                    .getBean("userService");
                return userService.getByUsername(((UserDTO) request.getSource()).getUsername());
            }
        };

        // a provider to fetch a user instance from a repository
        typeMapUser.setProvider(userDelegatingProvider);


        TypeMap<CompartmentDTO, Compartment> typeMapCompartment = modelMapper.getTypeMap(CompartmentDTO.class, Compartment.class);
        if (typeMapCompartment == null) {
            typeMapCompartment = modelMapper.createTypeMap(CompartmentDTO.class, Compartment.class);
        }
        Provider<Compartment> CompartmentDelegatingProvider = new Provider<Compartment>() {

            public Compartment get(ProvisionRequest<Compartment> request) {
                MediaService mediaService = (MediaService) WebApplicationContextUtils.getWebApplicationContext(
                        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getServletContext())
                    .getBean("mediaService");
                return mediaService.getCompartmentById( IdMapperUtils.map(((CompartmentDTO) request.getSource()).getCompartmentId(), CompartmentId.class));
            }
        };

        typeMapCompartment.setProvider(CompartmentDelegatingProvider);


        TypeMap<GenreDTO, Genre> typeMapGenre = modelMapper.getTypeMap(GenreDTO.class, Genre.class);
        if (typeMapGenre == null) {
            typeMapGenre = modelMapper.createTypeMap(GenreDTO.class, Genre.class);
        }
        Provider<Genre> GenreDelegatingProvider = new Provider<Genre>() {

            public Genre get(ProvisionRequest<Genre> request) {
                MediaService mediaService = (MediaService) WebApplicationContextUtils.getWebApplicationContext(
                        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getServletContext())
                    .getBean("mediaService");
                return mediaService.getGenreById(((GenreDTO) request.getSource()).getId());
            }
        };

        typeMapGenre.setProvider(GenreDelegatingProvider);


        TypeMap<LoanDTO, Loan> typeMapLoan = modelMapper.getTypeMap(LoanDTO.class, Loan.class);
        if (typeMapLoan == null) {
            typeMapLoan = modelMapper.createTypeMap(LoanDTO.class, Loan.class);
        }
        Provider<Loan> LoanDelegatingProvider = new Provider<Loan>() {

            public Loan get(ProvisionRequest<Loan> request) {
                LoanService loanService = (LoanService) WebApplicationContextUtils.getWebApplicationContext(
                        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getServletContext())
                    .getBean("loanService");
                return loanService.getLoanById(((LoanDTO) request.getSource()).getId());
            }
        };

        typeMapLoan.setProvider(LoanDelegatingProvider);


        TypeMap<LocationDTO, Location> typeMapLocation = modelMapper.getTypeMap(LocationDTO.class, Location.class);
        if (typeMapLocation == null) {
            typeMapLocation = modelMapper.createTypeMap(LocationDTO.class, Location.class);
        }
        Provider<Location> LocationDelegatingProvider = new Provider<Location>() {

            public Location get(ProvisionRequest<Location> request) {
                MediaService mediaService = (MediaService) WebApplicationContextUtils.getWebApplicationContext(
                        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getServletContext())
                    .getBean("mediaService");
                return mediaService.getLocationById(((LocationDTO) request.getSource()).getId());
            }
        };

        typeMapLocation.setProvider(LocationDelegatingProvider);


        TypeMap<PaymentDTO, Payment> typeMapPayment = modelMapper.getTypeMap(PaymentDTO.class, Payment.class);
        if (typeMapPayment == null) {
            typeMapPayment = modelMapper.createTypeMap(PaymentDTO.class, Payment.class);
        }
        Provider<Payment> PaymentDelegatingProvider = new Provider<Payment>() {

            public Payment get(ProvisionRequest<Payment> request) {
                PaymentService paymentService = (PaymentService) WebApplicationContextUtils.getWebApplicationContext(
                        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getServletContext())
                    .getBean("paymentService");
                return paymentService.getPaymentById( IdMapperUtils.map(((PaymentDTO) request.getSource()).getPaymentId(), PaymentId.class));
            }
        };

        typeMapPayment.setProvider(PaymentDelegatingProvider);


        TypeMap<ReservationDTO, Reservation> typeMapReservation = modelMapper.getTypeMap(ReservationDTO.class, Reservation.class);
        if (typeMapReservation == null) {
            typeMapReservation = modelMapper.createTypeMap(ReservationDTO.class, Reservation.class);
        }
        Provider<Reservation> ReservationDelegatingProvider = new Provider<Reservation>() {

            public Reservation get(ProvisionRequest<Reservation> request) {
                LoanService loanService = (LoanService) WebApplicationContextUtils.getWebApplicationContext(
                        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getServletContext())
                    .getBean("loanService");
                return loanService.getReservationById(((ReservationDTO) request.getSource()).getReservationId());
            }
        };

        typeMapReservation.setProvider(ReservationDelegatingProvider);


        TypeMap<ShelfDTO, Shelf> typeMapShelf = modelMapper.getTypeMap(ShelfDTO.class, Shelf.class);
        if (typeMapShelf == null) {
            typeMapShelf = modelMapper.createTypeMap(ShelfDTO.class, Shelf.class);
        }
        Provider<Shelf> ShelfDelegatingProvider = new Provider<Shelf>() {

            public Shelf get(ProvisionRequest<Shelf> request) {
                MediaService mediaService = (MediaService) WebApplicationContextUtils.getWebApplicationContext(
                        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getServletContext())
                    .getBean("mediaService");
                return mediaService.getShelfById(((ShelfDTO) request.getSource()).getId());
            }
        };
        typeMapShelf.setProvider(ShelfDelegatingProvider);


        TypeMap<CopyDTO, Copy> typeMapCopy = modelMapper.getTypeMap(CopyDTO.class, Copy.class);
        if (typeMapCopy == null) {
            typeMapCopy = modelMapper.createTypeMap(CopyDTO.class, Copy.class);
        }
        Provider<Copy> copyDelegatingProvider = new Provider<Copy>() {

            public Copy get(ProvisionRequest<Copy> request) {
                MediaService mediaService = (MediaService) WebApplicationContextUtils.getWebApplicationContext(
                        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getServletContext())
                    .getBean("mediaService");
                return mediaService.getCopyById( IdMapperUtils.map(((CopyDTO) request.getSource()).getCopyId(), CopyId.class));

            }
        };
        typeMapCopy.setProvider(copyDelegatingProvider);






        // MEDIA Mappings


        // create a typemap to override default behaviour for DTO to entity mapping
        TypeMap<MediaDTO, Media> typeMapMedia = modelMapper.getTypeMap(MediaDTO.class, Media.class);
        if (typeMapMedia == null) {
            typeMapMedia = modelMapper.createTypeMap(MediaDTO.class, Media.class);
        }

        TypeMap<BookDTO, Book> typeMapBook = modelMapper.getTypeMap(BookDTO.class, Book.class);
        if (typeMapBook == null) {
            typeMapBook = modelMapper.createTypeMap(BookDTO.class, Book.class);
        }

        TypeMap<AudioDTO, Audio> typeMapAudio = modelMapper.getTypeMap(AudioDTO.class, Audio.class);
        if (typeMapAudio == null) {
            typeMapAudio = modelMapper.createTypeMap(AudioDTO.class, Audio.class);
        }

        TypeMap<MovieDTO, Movie> typeMapMovie = modelMapper.getTypeMap(MovieDTO.class, Movie.class);
        if (typeMapMovie == null) {
            typeMapMovie = modelMapper.createTypeMap(MovieDTO.class, Movie.class);
        }

        TypeMap<PaperDTO, Paper> typeMapPaper = modelMapper.getTypeMap(PaperDTO.class, Paper.class);
        if (typeMapPaper == null) {
            typeMapPaper = modelMapper.createTypeMap(PaperDTO.class, Paper.class);
        }

        Provider<Media> mediaDelegatingProvider = new Provider<Media>() {

            public Media get(ProvisionRequest<Media> request) {
                MediaService mediaService = (MediaService) WebApplicationContextUtils.getWebApplicationContext(
                        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getServletContext())
                    .getBean("mediaService");
                if (((MediaDTO) request.getSource()).getId()!=null) return mediaService.getMediaById(((MediaDTO) request.getSource()).getId());
                else {
                    return request.getSource() instanceof BookDTO ? new Book()
                        : request.getSource() instanceof PaperDTO ? new Paper()
                        : request.getSource() instanceof AudioDTO ? new Audio()
                        : request.getSource() instanceof MovieDTO ? new Movie()
                        : null;
                }
                //TODO usun to i tam ponizej gdize jest new Book()
                //return ((MediaDTO) request.getSource()).getId()!=null ? mediaService.getById(((MediaDTO) request.getSource()).getId()) : new Media(null, null, null);
            }
        };

        // a provider to fetch a user instance from a repository
        typeMapMedia.setProvider(mediaDelegatingProvider);


        Provider<Paper> paperDelegatingProvider = new Provider<Paper>() {

            public Paper get(ProvisionRequest<Paper> request) {
                MediaService mediaService = (MediaService) WebApplicationContextUtils.getWebApplicationContext(
                        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getServletContext())
                    .getBean("mediaService");
                return ((PaperDTO) request.getSource()).getId()!=null ? (Paper) mediaService.getMediaById(((PaperDTO) request.getSource()).getId()) : new Paper();
            }
        };

        Provider<Movie> movieDelegatingProvider = new Provider<Movie>() {

            public Movie get(ProvisionRequest<Movie> request) {
                MediaService mediaService = (MediaService) WebApplicationContextUtils.getWebApplicationContext(
                        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getServletContext())
                    .getBean("mediaService");
                return ((MovieDTO) request.getSource()).getId()!=null ? (Movie) mediaService.getMediaById(((MovieDTO) request.getSource()).getId()) : new Movie();
            }
        };

        Provider<Audio> audioDelegatingProvider = new Provider<Audio>() {

            public Audio get(ProvisionRequest<Audio> request) {
                MediaService mediaService = (MediaService) WebApplicationContextUtils.getWebApplicationContext(
                        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getServletContext())
                    .getBean("mediaService");
                return ((AudioDTO) request.getSource()).getId()!=null ? (Audio) mediaService.getMediaById(((AudioDTO) request.getSource()).getId()) : new Audio();
            }
        };

        Provider<Book> bookDelegatingProvider = new Provider<Book>() {

            public Book get(ProvisionRequest<Book> request) {
                MediaService mediaService = (MediaService) WebApplicationContextUtils.getWebApplicationContext(
                        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getServletContext())
                    .getBean("mediaService");
                return ((BookDTO) request.getSource()).getId()!=null ? (Book) mediaService.getMediaById(((BookDTO) request.getSource()).getId()) : new Book();
            }
        };


        typeMapBook.setProvider(bookDelegatingProvider);
        typeMapAudio.setProvider(audioDelegatingProvider);
        typeMapMovie.setProvider(movieDelegatingProvider);
        typeMapPaper.setProvider(paperDelegatingProvider);
    }

    //public static ModelMapper getModelMapper() {
    //	return modelMapper;
    //}

    /**
     * <p>Note: outClass object must have default constructor with no arguments</p>
     *
     * @param <D>      type of result object.
     * @param <T>      type of source object to map from.
     * @param entity   entity that needs to be mapped.
     * @param outClass class of result object.
     * @return new object of <code>outClass</code> type.
     */
    public static <D, T> D map(final T entity, Class<D> outClass) {
        return modelMapper.map(entity, outClass);
    }

    /**
     * <p>Note: outClass object must have default constructor with no arguments</p>
     *
     * @param entityList list of entities that needs to be mapped
     * @param outCLass   class of result list element
     * @param <D>        type of objects in result list
     * @param <T>        type of entity in <code>entityList</code>
     * @return list of mapped object with <code><D></code> type.
     */
    public static <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
        return entityList.stream()
            .map(entity -> map(entity, outCLass))
            .collect(Collectors.toList());
    }

    /**
     * Maps {@code source} to {@code destination}.
     *
     * @param source      object to map from
     * @param destination object to map to
     */
    public static <S, D> D map(final S source, D destination) {
        modelMapper.map(source, destination);
        return destination;
    }



}
