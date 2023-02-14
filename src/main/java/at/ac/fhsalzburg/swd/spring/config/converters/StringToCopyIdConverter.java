package at.ac.fhsalzburg.swd.spring.config.converters;

import at.ac.fhsalzburg.swd.spring.model.ids.CopyId;
import at.ac.fhsalzburg.swd.spring.model.medias.Media;
import org.springframework.core.convert.converter.Converter;
//PROTOTYPE - not used yet
public class StringToCopyIdConverter
    //TODO should be DTO
    implements Converter<String, CopyId> {

    @Override
    public CopyId convert(String from) {
        String[] data = from.split(",");
        return new CopyId();
            //Long.parseLong(data[0]);
    }
}
