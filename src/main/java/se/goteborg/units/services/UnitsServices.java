package se.goteborg.units.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import se.goteborg.units.model.Units;
import se.goteborg.units.repository.UnitsRepository;

import java.util.*;

@Service
public class UnitsServices {
    Logger loggerService = LoggerFactory.getLogger(UnitsServices.class);

    private final UnitsRepository unitsRepository;
    Map<String, Integer> allUnitsAndVisits = new HashMap<String, Integer>();

    public UnitsServices(UnitsRepository unitsRepository) {
        this.unitsRepository = unitsRepository;
    }

    public Optional<Units> createUnit(Units units) {
        Optional<Units> name = findUnitsByName(units.getName());
        if (name.isPresent()) {
            loggerService.info("Unit already exists");
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
        List<Units> findAll = unitsRepository.findAll();
        List<Units> findAllplusOneVisits = new ArrayList<Units>();

        for (int i = 0; i < findAll.size(); i++) {
            findAllplusOneVisits.add(updateTotalVisitsColumn(findAll.get(i)).get());
        }

        if (findAllplusOneVisits.isEmpty()) {
            loggerService.info("There are no units");
            return Optional.empty();
        } else {
            loggerService.info(findAllplusOneVisits.toString());
            return Optional.of(findAllplusOneVisits);
        }
    }

    public Optional<Units> findUnitsByName(String name) {
        Optional<Units> unit = unitsRepository.findByName(name);
        if (unit.isPresent()) {
            return updateTotalVisitsColumn(unit.get());
        } else {
            loggerService.info(name + " does not exists");
            return Optional.empty();
        }
    }

    public Optional<Units> findUnitById(String id) {
        Optional<Units> unit = unitsRepository.findById(id);
        if (unit.isPresent()) {
            return updateTotalVisitsColumn(unit.get());
        } else {
            loggerService.info(id + " does not exists");
            return Optional.empty();
        }
    }

    public Optional<List<Units>> findUnitsByCategory(String oneCategory) {
        List<Units> allUnits = unitsRepository.findAll();
        List<Units> foundedUnits = new ArrayList<Units>();
        List<Units> updateradUnits = new ArrayList<Units>();

        for (int i = 0; i < allUnits.size(); i++) {
            if ((allUnits.get(i).getCategory().contains(oneCategory)))
                foundedUnits.add(allUnits.get(i));
        }

        if (!foundedUnits.isEmpty()) {
            for (int i = 0; i < foundedUnits.size(); i++)
                updateradUnits.add(updateTotalVisitsColumn(foundedUnits.get(i)).get());
            loggerService.info("Units with category " + oneCategory+": "+ updateradUnits);
            return Optional.of(updateradUnits);
        }else {
            loggerService.info("There are no units with category: " + oneCategory);
            return Optional.empty();
        }
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
            int existingTotalVisits=existingUnit.get().getTotalVisits();
            deleteUnit(id);
            return Optional.of(
                    unitsRepository.save(new Units(unit.getId(),
                            unit.getName(),
                            unit.getAddress(),
                            unit.getCategory(),
                            existingTotalVisits
                    )));
        } else {
            loggerService.info(id + " does not exists");
            return Optional.empty();
        }
    }


    @ConditionalOnProperty(name = "updating.enabled", matchIfMissing = true)
    @SuppressWarnings("null")
    // 1=secund , 0=minut, 0= hours, *-dayOfTheMonth *-month *-Day
    @Scheduled(cron = "1 0 0 * * *")
    private void transferTotalVisitsFromTableUnitsAtMidnight() {
        allUnitsAndVisits.clear();
        loggerService.info("After clear:Values of allUnitsAndVisits" + allUnitsAndVisits);
        List<Units> allUnits = unitsRepository.findAll();

        for (int i = 0; i < allUnits.size(); i++) {
            allUnitsAndVisits.put(allUnits.get(i).getId(), allUnits.get(i).getTotalVisits());
        }
        loggerService.info("Transfer values of id and total visits to method at one second after midnight" +
                "getDataFromTransferTotalVisitsFromTableUnitsAtMidnight" + allUnitsAndVisits);

    }

    public Optional<Map<String, Integer>> getDataFromTransferTotalVisitsFromTableUnitsAtMidnight() {
        loggerService.info("Values of id and total visits at 3am" + allUnitsAndVisits);
        return Optional.of(allUnitsAndVisits);
    }

    @ConditionalOnProperty(name = "updating.enabled", matchIfMissing = true)
    @SuppressWarnings("null")
    @Scheduled(cron = "0 1 0 * * *")
    private void setTotalVisitsFromTableUnitsToZeroAfterTransferingDataAtMidnight() {
        loggerService.info("Delete id and total visits, one minute after midnight");
        unitsRepository.setTotalVisitsToZeroAfterTransferingData();
    }

    private void deleteUnit(String id) {
        unitsRepository.delete(unitsRepository.findById(id).get());
    }
}
