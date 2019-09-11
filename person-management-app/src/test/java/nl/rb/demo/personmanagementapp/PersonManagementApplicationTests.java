package nl.rb.demo.personmanagementapp;

import nl.rb.demo.personmanagementapp.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = PersonManagementApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonManagementApplicationTests {

    private static final String CONTEXT_ROOT = "/person-management-app";

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getUrl() {
        return "http://localhost:" + port + CONTEXT_ROOT;
    }

    @Test
    public void testGetAllPersons() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(getUrl() + "/api/persons",
                HttpMethod.GET, entity, String.class);

        assertNotNull(response.getBody());
    }

    @Test
    public void testCreatePerson() {
        Person person = new Person();
        person.setFirstName("First Name");
        person.setLastName("Last Name");
        person.setAge(35);
        Long currentTime = System.currentTimeMillis();
        person.setDob(new java.util.Date(currentTime));
        person.setCurrentAddress("2045PK");

        ResponseEntity<Person> postResponse = restTemplate.postForEntity(getUrl() + "/api/persons", person, Person.class);
        Person result = postResponse.getBody();

        assertEquals(person.getFirstName(), result.getFirstName());
        assertEquals(person.getLastName(), result.getLastName());
        assertEquals(person.getDob(), result.getDob());
        assertEquals(person.getAge(), result.getAge());
        assertEquals(person.getCurrentAddress(), result.getCurrentAddress());

    }

    @Test
    public void testGetPersonByPersonId() {
        Person person = restTemplate.getForObject(getUrl() + "/api/persons/1", Person.class);
        assertNotNull(person);
    }

    @Test
    public void testUpdatePersonCurrentAddress() {

        //Person Record Creation
        Person person = new Person();
        person.setFirstName("First Name 2");
        person.setLastName("Last Name 2");
        person.setAge(34);
        Long currentTime = System.currentTimeMillis();

        person.setDob(new java.util.Date(currentTime));
        person.setCurrentAddress("2045PK, The Netherlands");

        ResponseEntity<Person> postResponse = restTemplate.postForEntity(getUrl() + "/api/persons", person, Person.class);

        Person recordCreated = postResponse.getBody();
        Long id = recordCreated.getId();
        person = restTemplate.getForObject(getUrl() + "/api/persons/" + id, Person.class);

        //updating the current address
        person.setCurrentAddress("1123WE, The Holland");

        //Person Record Update
        restTemplate.put(getUrl() + "/api/persons/" + id, person);

        Person result = restTemplate.getForObject(getUrl() + "/api/persons/" + id, Person.class);
        assertEquals(person.getFirstName(), result.getFirstName());
        assertEquals(person.getLastName(), result.getLastName());
        assertEquals(person.getDob(), result.getDob());
        assertEquals(person.getAge(), result.getAge());
        assertEquals(person.getCurrentAddress(), result.getCurrentAddress());
    }

    @Test
    public void testDeletePerson() {

        //Person Record Creation
        Person person = new Person();
        person.setFirstName("First Name 3");
        person.setLastName("Last Name 3");
        person.setAge(30);
        Long currentTime = System.currentTimeMillis();

        person.setDob(new java.util.Date(currentTime));
        person.setCurrentAddress("2322PK, The Netherlands");

        ResponseEntity<Person> postResponse = restTemplate.postForEntity(getUrl() + "/api/persons", person, Person.class);

        Person recordCreated = postResponse.getBody();
        assertNotNull(recordCreated);
        Long id = recordCreated.getId();

        restTemplate.delete(getUrl() + "/api/persons/" + id);

        Person result = restTemplate.getForObject(getUrl() + "/api/persons/" + id, Person.class);

        assertEquals(null, result.getFirstName());
        assertEquals(null, result.getLastName());
        assertEquals(null, result.getDob());
        assertEquals(null, result.getCurrentAddress());
    }
}
