package nl.rb.demo.personmanagementapp.controller;

import nl.rb.demo.personmanagementapp.exception.ResourceNotFoundException;
import nl.rb.demo.personmanagementapp.handler.PersonRequestHandler;
import nl.rb.demo.personmanagementapp.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api")
public class PersonController {

    @Autowired
    private PersonRequestHandler personRequestHandler;

    @GetMapping("/persons")
    public List<Person> getAllPersons() {
        return personRequestHandler.getAll();
    }

    @GetMapping("/persons/{id}")
    public ResponseEntity<Person> getPersonsById(@PathVariable(value = "id") Long personId)
            throws ResourceNotFoundException {
        Person person = personRequestHandler.getPerson(personId);
        return ResponseEntity.ok().body(person);
    }

    @PostMapping("/persons")
    public Person createPerson(@Valid @RequestBody Person person) {
        return personRequestHandler.saveOrUpdate(person);
    }

    @PutMapping("/persons/{id}")
    public ResponseEntity<Person> updatePerson(
            @PathVariable(value = "id") Long personId, @RequestBody Person personDetails)
            throws ResourceNotFoundException {
        Person person = personRequestHandler.getPerson(personId);
        person.setCurrentAddress(personDetails.getCurrentAddress());
        final Person updatedPerson = personRequestHandler.saveOrUpdate(person);
        return ResponseEntity.ok(updatedPerson);
    }

    @DeleteMapping("/persons/{id}")
    public ResponseEntity deletePerson(@PathVariable(value = "id") Long personId) throws Exception {
        Person person = personRequestHandler.getPerson(personId);
        Boolean result = personRequestHandler.delete(person);
        if (result) {
            return ResponseEntity.ok("Delete Success");
        } else {
            return ResponseEntity.status(501).body("Technical Error");
        }
    }
}
