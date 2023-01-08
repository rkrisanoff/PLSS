package com.example.kurs.controller;

import com.example.kurs.dto.RobotDto;
import com.example.kurs.entity.Robot;
import com.example.kurs.service.RobotService;
import com.example.kurs.utils.JsonProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/robots")
public class RobotController {
    @Autowired
    private RobotService robotService;
    @Autowired
    private JsonProvider jsonProvider;

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
        robot.setBody_series(robotDto.getBody_series());
        robot.setBrain_series(robotDto.getBrain_series());
        robot.setEye_series(robotDto.getEye_series());
        robot.setOperator_post_id(robotDto.getOperator_post_id());
        robot.setHit_points(robotDto.getHit_points());
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
        robot.setHit_points(robotDto.getHit_points() != null ? robotDto.getHit_points() : origin.getHit_points());
        robot.setId(id);
        robotService.update(robot);
        return ResponseEntity.ok("Updated");
    }


}
