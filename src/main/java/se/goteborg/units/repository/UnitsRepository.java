package se.goteborg.units.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import se.goteborg.units.model.Units;
import java.util.Optional;

@Repository
public interface UnitsRepository extends JpaRepository<Units, String> {
    Optional<Units> findByName(String name);

    @Transactional
    @Modifying
    @Query("UPDATE Units e SET e.totalVisits = 0")
    void setTotalVisitsToZeroAfterTransferingData();
}


