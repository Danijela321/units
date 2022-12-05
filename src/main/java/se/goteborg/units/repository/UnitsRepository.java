package se.goteborg.units.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.goteborg.units.model.Units;

import java.util.Optional;

@Repository
public interface UnitsRepository extends JpaRepository<Units, String> {
    Optional<Units> findByName(String name);
}
