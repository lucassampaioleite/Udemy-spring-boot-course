package leite.sampaio.lucas.services;

import leite.sampaio.lucas.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();
    private final Logger logger = Logger.getLogger(PersonServices.class.getName());

    public Person findById(String id) {
        logger.info("Finding one person!");
        Person person = new Person();
        person.setId(Long.valueOf(id));
        person.setFirstName("Lucas");
        person.setLastName("Leite");
        person.setAddress("Rua X, 999, Jaboatão dos Guararapes, Pernambuco, Brasil");
        person.setGender("Male");
        return person;
    }

    public List<Person> findAll() {
        logger.info("Finding all people!");
        return IntStream.range(0, 8).mapToObj(this::mockPerson).collect(Collectors.toList());
    }

    public Person create(Person person) {
        logger.info("Creating one person!");
        return person;
    }

    public Person update(Person person) {
        logger.info("Updating one person!");
        return person;
    }

    public void delete(String id) {
        logger.info("Deleting one person!");
    }

    private Person mockPerson(int i) {
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Person name " + i);
        person.setLastName("Last name " + i);
        person.setAddress("Some address in Brazil " + i);
        person.setGender("Male");
        return person;
    }

}
