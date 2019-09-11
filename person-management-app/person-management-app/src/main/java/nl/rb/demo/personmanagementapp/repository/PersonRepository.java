package nl.rb.demo.personmanagementapp.repository;

import nl.rb.demo.personmanagementapp.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {}
