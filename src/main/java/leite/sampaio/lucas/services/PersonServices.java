package leite.sampaio.lucas.services;

import leite.sampaio.lucas.dto.PersonDTO;
import leite.sampaio.lucas.exceptions.ResourceNotFoundException;
import leite.sampaio.lucas.mapper.ObjectMapper;
import leite.sampaio.lucas.model.Person;
import leite.sampaio.lucas.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private final Logger logger = Logger.getLogger(PersonServices.class.getName());
    @Autowired
    PersonRepository repository;

    public PersonDTO findById(Long id) {
        logger.info("Finding one person!");
        return ObjectMapper.parseObject(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!")), PersonDTO.class);
    }

    public List<PersonDTO> findAll() {
        logger.info("Finding all people!");
        return ObjectMapper.parseListObjects(repository.findAll(), PersonDTO.class);
    }

    public PersonDTO create(PersonDTO personDTO) {
        logger.info("Creating one person!");
        var entity = repository.save(ObjectMapper.parseObject(personDTO, Person.class));
        return ObjectMapper.parseObject(entity, PersonDTO.class);
    }

    public PersonDTO update(PersonDTO personDTO) {
        logger.info("Updating one person!");
        var entity = repository.findById(personDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(personDTO.getFirstName());
        entity.setLastName(personDTO.getLastName());
        entity.setAddress(personDTO.getAddress());
        entity.setGender(personDTO.getGender());

        return ObjectMapper.parseObject(repository.save(entity), PersonDTO.class);
    }

    public void delete(Long id) {
        logger.info("Deleting one person!");
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(entity);
    }
}
