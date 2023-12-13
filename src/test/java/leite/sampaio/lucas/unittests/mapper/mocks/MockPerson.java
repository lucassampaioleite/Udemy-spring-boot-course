package leite.sampaio.lucas.unittests.mapper.mocks;

import leite.sampaio.lucas.dto.PersonDTO;
import leite.sampaio.lucas.model.Person;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MockPerson {

    public Person mockEntity() {
        return mockEntity(0);
    }

    public PersonDTO mockDTO() {
        return mockDTO(0);
    }

    public List<Person> mockEntityList() {
        return IntStream.range(0, 14).mapToObj(this::mockEntity).collect(Collectors.toList());
    }

    public List<PersonDTO> mockVOList() {
        return IntStream.range(0, 14).mapToObj(this::mockDTO).collect(Collectors.toList());
    }

    public Person mockEntity(Integer number) {
        Person person = new Person();
        person.setAddress("Address Test" + number);
        person.setFirstName("First Name Test" + number);
        person.setGender(((number % 2) == 0) ? "Male" : "Female");
        person.setId(number.longValue());
        person.setLastName("Last Name Test" + number);
        return person;
    }

    public PersonDTO mockDTO(Integer number) {
        PersonDTO person = new PersonDTO();
        person.setAddress("Address Test" + number);
        person.setFirstName("First Name Test" + number);
        person.setGender(((number % 2) == 0) ? "Male" : "Female");
        person.setId(number.longValue());
        person.setLastName("Last Name Test" + number);
        return person;
    }

}
