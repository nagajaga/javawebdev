package namesandaddresses;

import java.util.List;

import javax.persistence.Entity;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    @EntityGraph(value = "Person.address")
    List<Person> findAll();
}
