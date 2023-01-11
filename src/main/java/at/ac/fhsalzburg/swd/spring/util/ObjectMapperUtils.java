package at.ac.fhsalzburg.swd.spring.util;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import at.ac.fhsalzburg.swd.spring.dto.medias.*;
import at.ac.fhsalzburg.swd.spring.model.medias.*;
import at.ac.fhsalzburg.swd.spring.services.MediaService;
import org.modelmapper.*;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import at.ac.fhsalzburg.swd.spring.dto.UserDTO;
import at.ac.fhsalzburg.swd.spring.model.User;
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
            .setFieldMatchingEnabled(true)
            .setPropertyCondition(isStringBlank); // skip empty strings

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


        // create a typemap to override default behaviour for DTO to entity mapping
        TypeMap<MediaDTO, Media> typeMapMedia = modelMapper.getTypeMap(MediaDTO.class, Media.class);
        if (typeMapMedia == null) {
            typeMapMedia = modelMapper.createTypeMap(MediaDTO.class, Media.class)
                .addMappings(mapper -> mapper.skip(Media::setId))
  /*              .include(BookDTO.class, Book.class)
                .include(AudioDTO.class, Audio.class)
                .include(PaperDTO.class, Paper.class)
                .include(MovieDTO.class, Movie.class)
                */;
        }

        TypeMap<BookDTO, Book> typeMapBook = modelMapper.getTypeMap(BookDTO.class, Book.class);
        if (typeMapBook == null) {
            typeMapBook = modelMapper.createTypeMap(BookDTO.class, Book.class)
                .addMappings(mapper -> mapper.skip(Book::setId));
        }

        TypeMap<AudioDTO, Audio> typeMapAudio = modelMapper.getTypeMap(AudioDTO.class, Audio.class);
        if (typeMapAudio == null) {
            typeMapAudio = modelMapper.createTypeMap(AudioDTO.class, Audio.class)
                .addMappings(mapper -> mapper.skip(Audio::setId));
        }

        TypeMap<MovieDTO, Movie> typeMapMovie = modelMapper.getTypeMap(MovieDTO.class, Movie.class);
        if (typeMapMovie == null) {
            typeMapMovie = modelMapper.createTypeMap(MovieDTO.class, Movie.class)
                .addMappings(mapper -> mapper.skip(Movie::setId));
        }

        TypeMap<PaperDTO, Paper> typeMapPaper = modelMapper.getTypeMap(PaperDTO.class, Paper.class);
        if (typeMapPaper == null) {
            typeMapPaper = modelMapper.createTypeMap(PaperDTO.class, Paper.class)
                .addMappings(mapper -> mapper.skip(Paper::setId));
        }

        Provider<Media> mediaDelegatingProvider = new Provider<Media>() {

            public Media get(ProvisionRequest<Media> request) {
                MediaService mediaService = (MediaService) WebApplicationContextUtils.getWebApplicationContext(
                        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getServletContext())
                    .getBean("mediaService");
                if (((MediaDTO) request.getSource()).getId()!=null) return mediaService.getById(((MediaDTO) request.getSource()).getId());
                else {
                    return request.getSource() instanceof BookDTO ? new Book()
                        : request.getSource() instanceof PaperDTO ? new Paper()
                        : request.getSource() instanceof AudioDTO ? new Audio()
                        : request.getSource() instanceof MovieDTO ? new Movie()
                        : null;
                }
                //return ((MediaDTO) request.getSource()).getId()!=null ? mediaService.getById(((MediaDTO) request.getSource()).getId()) : new Media(null, null, null);
            }
        };

        // a provider to fetch a user instance from a repository
        typeMapMedia.setProvider(mediaDelegatingProvider);

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
