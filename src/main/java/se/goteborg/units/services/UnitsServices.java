package se.goteborg.units.services;

import org.springframework.stereotype.Service;
import se.goteborg.units.model.Units;
import se.goteborg.units.repository.UnitsRepository;

import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UnitsServices {
    private final UnitsRepository unitsRepository;

    public UnitsServices(UnitsRepository unitsRepository) {
        this.unitsRepository = unitsRepository;
    }

    public Optional<Units> createUnit(Units units) {
        Optional<Units> name = findUnitsByName(units.getName());
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
        //  return (unitsRepository.findByName(name));
        Optional<Units> unit = unitsRepository.findByName(name);
        if (unit.isPresent())
            return updateTotalVisitsColumn(unit.get());
        else
            return Optional.empty();
    }

    public Optional<Units> findUnitById(String id) {
        Optional<Units> unit = unitsRepository.findById(id);
        if (unit.isPresent())
            return updateTotalVisitsColumn(unit.get());
        else
            return Optional.empty();
    }


    public Optional<List<Units>> findUnitsByCategory(String oneCategory) {
        List<Units> allUnits = findAllUnits().get();
        List<Units> foundedUnits = new ArrayList<Units>();
        List<Units> updateradUnits = new ArrayList<Units>();


        for (int i = 0; i < allUnits.size(); i++) {
            if ((allUnits.get(i).getCategory().contains(oneCategory)))
                foundedUnits.add(allUnits.get(i));
        }


        if (!foundedUnits.isEmpty()) {
            for (int i = 0; i < foundedUnits.size(); i++)
                updateradUnits.add(updateTotalVisitsColumn(foundedUnits.get(i)).get());
        }
        if (!foundedUnits.isEmpty())
            return Optional.of(updateradUnits);
        else
            return Optional.empty();


    }


    private Optional<Units> updateTotalVisitsColumn(Units units) {
        return Optional.of(
                unitsRepository.save(new Units(units.getId(),
                        units.getName(),
                        units.getAddress(),
                        units.getCategory(),
                        (units.getTotalVisits() + 1)
                )));
    }


    public Optional<Units> updateUnit(String id, Units unit) {
        Optional<Units> existingUnit = unitsRepository.findById(id);
        if (existingUnit.isPresent()) {
            return Optional.of(
                    unitsRepository.save(new Units(unit.getId(),
                            unit.getName(),
                            unit.getAddress(),
                            unit.getCategory(),
                            unit.getTotalVisits()
                    )));
        } else {
            return Optional.empty();
        }
    }
}
