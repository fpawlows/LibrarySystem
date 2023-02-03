package at.ac.fhsalzburg.swd.spring.util;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import at.ac.fhsalzburg.swd.spring.dto.*;
import at.ac.fhsalzburg.swd.spring.dto.ids.CompartmentDTOId;
import at.ac.fhsalzburg.swd.spring.dto.ids.CopyDTOId;
import at.ac.fhsalzburg.swd.spring.dto.ids.PaymentDTOId;
import at.ac.fhsalzburg.swd.spring.dto.ids.ShelfDTOId;
import at.ac.fhsalzburg.swd.spring.dto.medias.*;
import at.ac.fhsalzburg.swd.spring.model.*;
import at.ac.fhsalzburg.swd.spring.model.ids.CompartmentId;
import at.ac.fhsalzburg.swd.spring.model.ids.CopyId;
import at.ac.fhsalzburg.swd.spring.model.ids.PaymentId;
import at.ac.fhsalzburg.swd.spring.model.ids.ShelfId;
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
public class IdMapperUtils {

    private final static ModelMapper modelMapper;

    /**
     * Hide from public usage.
     */
    private IdMapperUtils() {
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


        TypeMap<CompartmentDTOId, CompartmentId> typeMapCompartmentId = modelMapper.getTypeMap(CompartmentDTOId.class, CompartmentId.class);
        if (typeMapCompartmentId == null) {
            typeMapCompartmentId = modelMapper.createTypeMap(CompartmentDTOId.class, CompartmentId.class);
        }

        TypeMap<CopyDTOId, CopyId> typeMapCopyId = modelMapper.getTypeMap(CopyDTOId.class, CopyId.class);
        if (typeMapCopyId == null) {
            typeMapCopyId = modelMapper.createTypeMap(CopyDTOId.class, CopyId.class);
        }

        TypeMap<PaymentDTOId, PaymentId> typeMapPaymentId = modelMapper.getTypeMap(PaymentDTOId.class, PaymentId.class);
        if (typeMapPaymentId == null) {
            typeMapPaymentId = modelMapper.createTypeMap(PaymentDTOId.class, PaymentId.class);
        }

        TypeMap<ShelfDTOId, ShelfId> typeMapShelfId = modelMapper.getTypeMap(ShelfDTOId.class, ShelfId.class);
        if (typeMapShelfId == null) {
            typeMapShelfId = modelMapper.createTypeMap(ShelfDTOId.class, ShelfId.class);
        }


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
