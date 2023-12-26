package leite.sampaio.lucas.unittests.mockito.services;

import leite.sampaio.lucas.dto.PersonDTO;
import leite.sampaio.lucas.exceptions.RequiredObjectIsNullException;
import leite.sampaio.lucas.model.Person;
import leite.sampaio.lucas.repositories.PersonRepository;
import leite.sampaio.lucas.services.PersonServices;
import leite.sampaio.lucas.unittests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class PersonServicesTest {

    private MockPerson input;

    @InjectMocks
    private PersonServices service;

    @Mock
    private PersonRepository repository;

    @BeforeEach
    public void setUpMocks() throws Exception {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {

        List<Person> peopleList = input.mockEntityList();

        when(repository.findAll()).thenReturn(peopleList);

        var people = service.findAll();
        assertNotNull(people);
        assertEquals(14, people.size());

        var personOne = people.get(1);
        assertNotNull(personOne);
        assertNotNull(personOne.getKey());
        assertNotNull(personOne.getLinks());
        assertTrue(personOne.toString().contains("</api/person/v1/1>;rel=\"self\""));
        assertEquals("Address Test1", personOne.getAddress());
        assertEquals("First Name Test1", personOne.getFirstName());
        assertEquals("Female", personOne.getGender());
        assertEquals("Last Name Test1", personOne.getLastName());

        var personSix = people.get(6);
        assertNotNull(personSix);
        assertNotNull(personSix.getKey());
        assertNotNull(personSix.getLinks());
        assertTrue(personSix.toString().contains("</api/person/v1/6>;rel=\"self\""));
        assertEquals("Address Test6", personSix.getAddress());
        assertEquals("First Name Test6", personSix.getFirstName());
        assertEquals("Male", personSix.getGender());
        assertEquals("Last Name Test6", personSix.getLastName());

        var personThirteen = people.get(13);
        assertNotNull(personThirteen);
        assertNotNull(personThirteen.getKey());
        assertNotNull(personThirteen.getLinks());
        assertTrue(personThirteen.toString().contains("</api/person/v1/13>;rel=\"self\""));
        assertEquals("Address Test13", personThirteen.getAddress());
        assertEquals("First Name Test13", personThirteen.getFirstName());
        assertEquals("Female", personThirteen.getGender());
    }

    @Test
    public void testFindById() {
        Person person = input.mockEntity(1);

        when(repository.findById(1L)).thenReturn(Optional.of(person));

        var result = service.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("</api/person/v1/1>;rel=\"self\""));
        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Female", result.getGender());
        assertEquals("Last Name Test1", result.getLastName());
    }

    @Test
    public void testCreate() {
        Person originalPerson = input.mockEntity(1);
        Person persistedPerson = originalPerson;
        PersonDTO updatedPersonDTO = input.mockDTO(1);

        when(repository.save(originalPerson)).thenReturn(persistedPerson);

        var result = service.create(updatedPersonDTO);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("</api/person/v1/1>;rel=\"self\""));
        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Female", result.getGender());
        assertEquals("Last Name Test1", result.getLastName());
    }

    @Test
    public void testCreateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> service.create(null));
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testUpdate() {
        Person originalPerson = input.mockEntity(1);
        Person persistedPerson = originalPerson;
        PersonDTO updatedPersonDTO = input.mockDTO(1);

        when(repository.findById(1L)).thenReturn(Optional.of(originalPerson));
        when(repository.save(originalPerson)).thenReturn(persistedPerson);

        var result = service.update(updatedPersonDTO);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("</api/person/v1/1>;rel=\"self\""));
        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Female", result.getGender());
        assertEquals("Last Name Test1", result.getLastName());
    }

    @Test
    public void testUpdateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> service.update(null));
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void delete() {
        Person person = input.mockEntity(1);
        person.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(person));
        service.delete(1L);
    }
}
