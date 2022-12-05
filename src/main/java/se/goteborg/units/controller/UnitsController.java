package se.goteborg.units.controller;

import jakarta.validation.Valid;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import se.goteborg.units.model.Units;
import se.goteborg.units.services.UnitsServices;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/goteborg.se/api")
public class UnitsController {

    private final UnitsServices unitsServices;

    public UnitsController(UnitsServices unitsServices) {
        this.unitsServices = unitsServices;
    }

    @PostMapping("/")
    public ResponseEntity<Units> createUnit(@Valid final @RequestBody Units units) {
        return unitsServices.createUnit(units).map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Units> updateUnit(@RequestBody Units unit, @PathVariable String id) {
        return unitsServices.updateUnit(id, unit).map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @GetMapping(value = {"/"})
    public ResponseEntity<List<Units>> getAllUnits() {
        return unitsServices.findAllUnits().map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<Units> getUnitsByNamn(@PathVariable(value = "name") String name) {
        return unitsServices.findUnitsByName(name).map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<Units> getUnitsById(@PathVariable(value = "id") String id) {
        return unitsServices.findUnitById(id).map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/category/{category}")
    public ResponseEntity<List<Units>>getUnitsByCategory(@PathVariable(value = "category") String oneCategory) {
        return unitsServices.findUnitsByCategory(oneCategory).map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ConditionalOnProperty(name = "updating.enabled", matchIfMissing = true)
    @SuppressWarnings("null")
    @Scheduled(cron = "0 0 3 * * *")
    @GetMapping(value = "/units")
    public ResponseEntity< Map<String,Integer> >getUnitsAndVisitsAt3am() {
        return unitsServices.getDataFromTransferTotalVisitsFromTableUnitsAtMidnight().map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

}
