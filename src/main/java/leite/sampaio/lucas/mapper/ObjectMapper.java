package leite.sampaio.lucas.mapper;

import leite.sampaio.lucas.dto.PersonDTO;
import leite.sampaio.lucas.model.Person;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ObjectMapper {
    private static final ModelMapper mapper = new ModelMapper();

    static {
        mapper.createTypeMap(Person.class, PersonDTO.class).addMapping(Person::getId, PersonDTO::setKey);
        mapper.createTypeMap(PersonDTO.class, Person.class).addMapping(PersonDTO::getKey, Person::setId);
    }

    public static <O, D> D parseObject(O origin, Class<D> destination) {
        return mapper.map(origin, destination);
    }

    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
        return origin.stream().map(o -> mapper.map(o, destination)).collect(Collectors.toList());
    }
}
