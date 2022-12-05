package se.goteborg.units.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.goteborg.units.model.Units;

@Repository
public interface UnitsRepository extends JpaRepository<Units, String> {
}
