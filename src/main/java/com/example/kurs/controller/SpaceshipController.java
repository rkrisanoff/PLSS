package com.example.kurs.controller;

import com.example.kurs.dto.SpaceshipRequestDto;
import com.example.kurs.dto.SpaceshipUpdateRequestDto;
import com.example.kurs.entity.Department;
import com.example.kurs.entity.MicroreactorType;
import com.example.kurs.entity.Spaceship;
import com.example.kurs.service.DepartmentService;
import com.example.kurs.service.ReactorService;
import com.example.kurs.service.SpaceshipService;
import com.example.kurs.utils.JsonProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/spaceships")
public class SpaceshipController {
    @Autowired
    private SpaceshipService spaceshipService;
    @Autowired
    private JsonProvider jsonProvider;

    @Autowired
    private ReactorService reactorService;
    @Autowired
    private DepartmentService departmentService;
    @PostMapping("/create")
    public ResponseEntity create(@RequestBody SpaceshipRequestDto spaceshipRequestDto){
        Spaceship spaceship = spaceshipService.create(spaceshipRequestDto);
        if (spaceship != null){
            return ResponseEntity.ok("Created.");
        }
        return ResponseEntity.badRequest().body("Department id not specified.");
    }

    @PostMapping("/{id}/update")
    public ResponseEntity update(@PathVariable Long id, @RequestBody SpaceshipUpdateRequestDto spaceshipUpdateRequestDto){
        Spaceship spaceship = spaceshipService.update(id, spaceshipUpdateRequestDto);
        if (spaceship != null){
            return ResponseEntity.ok("Updated.");
        }
        return ResponseEntity.badRequest().body("Invalid department.");
    }

    @GetMapping("/{id}/delete")
    public ResponseEntity delete(@PathVariable Long id){
        spaceshipService.deleteById(id);
        return ResponseEntity.ok("Deleted.");
    }

    @GetMapping("/all")
    public ResponseEntity getAll() throws JsonProcessingException {
        List<Spaceship> spaceshipList = spaceshipService.listall();
        return ResponseEntity.ok(jsonProvider.convertToJson(spaceshipList));
    }

    @GetMapping("/{id}/microreactors")
    public ResponseEntity getReactors(@PathVariable Long id) throws JsonProcessingException {
        if (id == null){
            return ResponseEntity.badRequest().body("Spaceship id not specified.");
        }
        List<MicroreactorType> reactors = reactorService.findBySpaceshipId(id);
        return ResponseEntity.ok(jsonProvider.convertToJson(reactors));
    }

    @GetMapping("/{id}/work")
    public ResponseEntity work(@PathVariable Long id){
        if (id == null){
            return ResponseEntity.badRequest().body("Invalid spaceship id.");
        }
        Spaceship spaceship = spaceshipService.findById(id);
        if (spaceship == null){
            return ResponseEntity.badRequest().body("Spaceship " + id + " not found.");
        }
        List<MicroreactorType> reactors = reactorService.findBySpaceshipId(id);
        if (reactors == null || reactors.isEmpty()){
            return ResponseEntity.ok("Spaceship " + id + " has no reactors.");
        }
        Integer b2h6cons = 0;
        Integer b5h12cons = 0;
        Integer b10h14cons = 0;
        Integer b12h12cons = 0;
        for (int i = 0; i < reactors.size(); i++){
            b2h6cons += reactors.get(i).getB2_h6_consumption_rate();
            b5h12cons += reactors.get(i).getB5_h12_consumption_rate();
            b10h14cons += reactors.get(i).getB10_h14_consumption_rate();
            b12h12cons += reactors.get(i).getB12_h12_consumption_rate();
        }
        spaceship.setB2_h6_quantity(spaceship.getB2_h6_quantity() - b2h6cons);
        spaceship.setB5_h12_quantity(spaceship.getB5_h12_quantity() - b5h12cons);
        spaceship.setB10_h14_quantity(spaceship.getB10_h14_quantity() - b10h14cons);
        spaceship.setB12_h12_quantity(spaceship.getB12_h12_quantity() - b12h12cons);
        spaceship.setIncome(spaceship.getIncome() + 100);

        Department department = departmentService.findById(spaceship.getDepartment_id());
        Integer sum = b2h6cons + b5h12cons + b10h14cons + b12h12cons + spaceship.getIncome();
        department.setCurrent_resource(department.getCurrent_resource() + sum);
        Department savedDep = departmentService.update(spaceship.getDepartment_id(), department);
        Spaceship savedShip = spaceshipService.udpateSpaceship(id, spaceship);
        if (savedShip == null || savedDep == null){
            return ResponseEntity.badRequest().body("Error while updating spaceship or department balance.");
        }
        return ResponseEntity.ok("Work is done.");
    }


}
