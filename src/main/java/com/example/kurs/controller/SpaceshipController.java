package com.example.kurs.controller;

import com.example.kurs.dto.RecycleDto;
import com.example.kurs.dto.SpaceshipRequestDto;
import com.example.kurs.dto.SpaceshipUpdateRequestDto;
import com.example.kurs.entity.Department;
import com.example.kurs.entity.MicroreactorType;
import com.example.kurs.entity.Spaceship;
import com.example.kurs.repo.SpaceshipRepo;
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
    @Autowired
    private SpaceshipRepo spaceshipRepo;

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
        if (reactors == null || reactors.isEmpty()){
            return ResponseEntity.badRequest().body("Microreactors in spaceship " + id + " not found");
        }
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

    @PostMapping("/{id}/recycle")
    public ResponseEntity recycle(@PathVariable Long id, @RequestBody RecycleDto recycleDto){
        if (id == null){
            return ResponseEntity.badRequest().body("Spaceship id is invalid.");
        }
        Spaceship spaceship = spaceshipService.findById(id);
        if (spaceship == null){
            return ResponseEntity.badRequest().body("Spaceship id " + id + " is invalid.");
        }
        Integer b2 = recycleDto.getB2_h6_quantity() != null ? recycleDto.getB2_h6_quantity() : 0;
        Integer b5 = recycleDto.getB5_h12_quantity() != null ? recycleDto.getB5_h12_quantity() : 0;
        Integer b10 = recycleDto.getB10_h14_quantity() != null ? recycleDto.getB10_h14_quantity() : 0;
        Integer b12 = recycleDto.getB12_h12_quantity() != null ? recycleDto.getB12_h12_quantity() : 0;
        Integer sum = b2 + b5 + b10 + b12;
        Department department = departmentService.findById(spaceship.getDepartment_id());
        if (department.getExtracted_bor_quantity() < sum){
            return ResponseEntity.badRequest().body("Not enough extracted bor in department " + spaceship.getDepartment_id());
        }
        department.setExtracted_bor_quantity(department.getExtracted_bor_quantity() - sum);
        spaceship.setB2_h6_quantity(spaceship.getB2_h6_quantity() != null ? spaceship.getB2_h6_quantity() + b2 : b2);
        spaceship.setB5_h12_quantity(spaceship.getB5_h12_quantity() != null ? spaceship.getB5_h12_quantity() + b5 : b5);
        spaceship.setB10_h14_quantity(spaceship.getB10_h14_quantity() != null ? spaceship.getB10_h14_quantity() + b10 : b10);
        spaceship.setB12_h12_quantity(spaceship.getB12_h12_quantity() != null ? spaceship.getB12_h12_quantity() + b12 : b12 );
        Spaceship savedShip = spaceshipService.udpateSpaceship(id, spaceship);
        Department savedDep = departmentService.update(department.getId(), department);
        if (savedShip == null || savedDep == null){
            return ResponseEntity.badRequest().body("Some error occurred while updating department and spaceship");
        }
        return ResponseEntity.ok("Recycled.");
    }
}
