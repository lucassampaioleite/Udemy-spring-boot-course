package leite.sampaio.lucas.services;

import leite.sampaio.lucas.controllers.PersonController;
import leite.sampaio.lucas.dto.PersonDTO;
import leite.sampaio.lucas.exceptions.RequiredObjectIsNullException;
import leite.sampaio.lucas.exceptions.ResourceNotFoundException;
import leite.sampaio.lucas.mapper.ObjectMapper;
import leite.sampaio.lucas.model.Person;
import leite.sampaio.lucas.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonServices {

    private final Logger logger = Logger.getLogger(PersonServices.class.getName());
    @Autowired
    PersonRepository repository;

    public PersonDTO findById(Long id) {
        logger.info("Finding one person!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        var dto = ObjectMapper.parseObject(entity, PersonDTO.class);
        dto.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());

        return dto;
    }

    public List<PersonDTO> findAll() {
        logger.info("Finding all people!");

        var dtos = ObjectMapper.parseListObjects(repository.findAll(), PersonDTO.class);
        dtos.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));

        return dtos;
    }

    public PersonDTO create(PersonDTO personDTO) {
        logger.info("Creating one person!");

        if (personDTO == null) throw new RequiredObjectIsNullException();

        var entity = repository.save(ObjectMapper.parseObject(personDTO, Person.class));

        var dto = ObjectMapper.parseObject(entity, PersonDTO.class);
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getKey())).withSelfRel());

        return dto;
    }

    public PersonDTO update(PersonDTO personDTO) {
        logger.info("Updating one person!");

        if (personDTO == null) throw new RequiredObjectIsNullException();

        var entity = repository.findById(personDTO.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(personDTO.getFirstName());
        entity.setLastName(personDTO.getLastName());
        entity.setAddress(personDTO.getAddress());
        entity.setGender(personDTO.getGender());

        var dto = ObjectMapper.parseObject(repository.save(entity), PersonDTO.class);
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getKey())).withSelfRel());

        return dto;
    }

    public void delete(Long id) {
        logger.info("Deleting one person!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        repository.delete(entity);
    }
}
