package leite.sampaio.lucas.repositories;

import leite.sampaio.lucas.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
