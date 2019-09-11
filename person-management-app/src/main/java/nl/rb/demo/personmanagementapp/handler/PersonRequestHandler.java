package nl.rb.demo.personmanagementapp.handler;

import nl.rb.demo.personmanagementapp.exception.ResourceNotFoundException;
import nl.rb.demo.personmanagementapp.model.Person;
import nl.rb.demo.personmanagementapp.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PersonRequestHandler {
    @Autowired
    private PersonRepository personRepository;

    public List<Person> getAll() {
        return personRepository.findAll();
    }

    public Person getPerson(Long personId) throws ResourceNotFoundException {
        return personRepository
                .findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found on :: " + personId));
    }

    public Person saveOrUpdate(Person person) {
        return personRepository.save(person);
    }

    public Boolean delete(Person person) {
        personRepository.delete(person);
        try {
            getPerson(person.getId());
        } catch (ResourceNotFoundException e) {
            return true;
        }
        return false;
    }
}
