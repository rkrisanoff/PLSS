package com.example.kurs.controller;

import com.example.kurs.dto.ExtractRequestDto;
import com.example.kurs.dto.RobotDto;
import com.example.kurs.entity.Body;
import com.example.kurs.entity.Robot;
import com.example.kurs.service.BodyService;
import com.example.kurs.service.RobotService;
import com.example.kurs.service.extraction.ExtractionStatus;
import com.example.kurs.utils.JsonProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/robots")
public class RobotController {
    @Autowired
    private RobotService robotService;
    @Autowired
    private JsonProvider jsonProvider;
    @Autowired
    private BodyService bodyService;

    @GetMapping("/all")
    public ResponseEntity getAll() throws JsonProcessingException {
        List<Robot> robots = robotService.getAll();
        String json = jsonProvider.convertToJson(robots);
        return ResponseEntity.ok(json);
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody RobotDto robotDto){
        Robot robot = new Robot();
        robot.setAsteroid_id(robotDto.getAsteroid_id());
        if (robotDto.getBody_series() == null){
            return ResponseEntity.badRequest().body("No body specified.");
        }
        robot.setBody_series(robotDto.getBody_series());
        if (robotDto.getBrain_series() == null){
            return ResponseEntity.badRequest().body("No brain specified.");
        }
        robot.setBrain_series(robotDto.getBrain_series());
        if (robotDto.getEye_series() == null){
            return ResponseEntity.badRequest().body("No eyes specified.");
        }
        robot.setEye_series(robotDto.getEye_series());
        robot.setOperator_post_id(robotDto.getOperator_post_id());
        robot.setHit_points(bodyService.findById(robotDto.getBody_series()).getMax_hit_points());
        robotService.create(robot);
        return ResponseEntity.ok("Created");
    }

    @GetMapping("/{id}/delete")
    public ResponseEntity delete(@PathVariable Long id){
        robotService.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }

    @PostMapping("/{id}/update")
    public ResponseEntity update(@PathVariable Long id, @RequestBody RobotDto robotDto){
        Robot robot = new Robot();
        Robot origin = robotService.findById(id);
        if (origin == null){
            return ResponseEntity.badRequest().body("Robot " + id + " does not exist");
        }
        robot.setAsteroid_id(robotDto.getAsteroid_id() != null ? robotDto.getAsteroid_id() : origin.getAsteroid_id());
        robot.setBody_series(robotDto.getBody_series() != null ? robotDto.getBody_series() : origin.getBody_series());
        robot.setBrain_series(robotDto.getBrain_series() != null ? robotDto.getBrain_series() : origin.getBrain_series());
        robot.setEye_series(robotDto.getEye_series() != null ? robotDto.getEye_series() : origin.getEye_series());
        robot.setOperator_post_id(robotDto.getOperator_post_id() != null ? robotDto.getOperator_post_id() : origin.getOperator_post_id());
        Body body = bodyService.findById(robot.getBody_series());
        if (body == null){
            return ResponseEntity.badRequest().body("Robot " + robot.getId() + " has invalid body " + robot.getBody_series());
        }
        Double hp = robotDto.getHit_points() != null ? robotDto.getHit_points() : origin.getHit_points();
        boolean tooMuchHp = false;
        if (hp > body.getMax_hit_points()){
            hp = body.getMax_hit_points();
            tooMuchHp = true;
        }
        robot.setHit_points(hp);
        robot.setId(id);
        robotService.update(robot);
        if (tooMuchHp){
            return ResponseEntity.ok("Updated. Hit points set to " + hp);
        }
        return ResponseEntity.ok("Updated");
    }

    @PostMapping("/{id}/extract")
    public ResponseEntity extract(@PathVariable Long id, @RequestBody ExtractRequestDto requestDto){
        Long department_id = requestDto.getDepartment_id();
        Long deposit_id = requestDto.getDeposit_id();
        Robot robot = robotService.findById(id);
        ExtractionStatus status = robotService.extract(robot,deposit_id, department_id);
        String msg = "OK";
        switch (status){
            case OK:
                return ResponseEntity.ok(msg);
            case ROBOT_DEAD:
                msg = "Extracted. Robot dead.";
                break;
            case NO_BORON:
                msg = "Deposit is empty.";
                break;
            case NO_BRAIN:
                msg = "Robot has no brain.";
                break;
            case NO_DEPARTMENT:
                msg = "Department not found.";
                break;
            case NO_DEPOSIT:
                msg = "Deposit not found.";
                break;
            case NOT_ON_TARGET_ASTEROID:
                msg = "Robot is not on target asteroid. Move it.";
                break;
            default:
                msg = "Some error occurred";
                break;
        }
        return ResponseEntity.badRequest().body(msg);
    }
}
