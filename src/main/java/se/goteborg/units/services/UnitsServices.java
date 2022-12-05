package se.goteborg.units.services;

import org.springframework.stereotype.Service;
import se.goteborg.units.model.Units;
import se.goteborg.units.repository.UnitsRepository;
import java.util.List;
import java.util.Optional;

@Service
public class UnitsServices {
    private final UnitsRepository unitsRepository;

    public UnitsServices(UnitsRepository unitsRepository) {
        this.unitsRepository = unitsRepository;
    }

    public Optional<Units> createUnit(Units units) {
        Optional<Units> name =  findUnitsByName(units.getName());
        if (name.isPresent()) {
            return Optional.empty();
        } else
            return Optional.of(unitsRepository.save(new Units(units.getId(),
                    units.getName(),
                    units.getAddress(),
                    units.getCategory(),
                    units.getTotalVisits()
            )));
    }


    public Optional<List<Units>> findAllUnits() {
        var findAll = unitsRepository.findAll();
        if (findAll.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(findAll);
        }
    }

    public Optional<Units> findUnitsByName(String name) {
        return (unitsRepository.findByName(name));
    }
}
