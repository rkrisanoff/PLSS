package com.example.kurs.controller;

import com.example.kurs.dto.ExtractRequestDto;
import com.example.kurs.dto.RobotDto;
import com.example.kurs.entity.*;
import com.example.kurs.service.*;
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
    @Autowired
    private BrainService brainService;
    @Autowired
    private  EyesService eyesService;
    @Autowired
    private PostService postService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AsteroidService asteroidService;

    @GetMapping("/all")
    public ResponseEntity getAll() throws JsonProcessingException {
        List<Robot> robots = robotService.getAll();
        String json = jsonProvider.convertToJson(robots);
        return ResponseEntity.ok(json);
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody RobotDto robotDto){
        Robot robot = new Robot();

        if (robotDto.getAsteroid_id() != null){
            Asteroid asteroid = asteroidService.findById(robotDto.getAsteroid_id());
            if (asteroid == null){
                return ResponseEntity.badRequest().body("Invalid asteroid id " + robot.getAsteroid_id());
            }
            robot.setAsteroid_id(robotDto.getAsteroid_id());
        }

        if (robotDto.getBody_series() == null){
            return ResponseEntity.badRequest().body("No body specified.");
        } else {
            Body body = bodyService.findByReleaseSeries(robotDto.getBody_series());
            if (body == null) {
                return ResponseEntity.badRequest().body("Invalid body id " + robotDto.getBody_series());
            }
            robot.setBody_series(robotDto.getBody_series());

        }
        if (robotDto.getBrain_series() == null){
            return ResponseEntity.badRequest().body("No brain specified.");
        }else {
            PositronicBrain brain = brainService.findByReleaseSeries(robotDto.getBrain_series());
            if (brain == null) {
                return ResponseEntity.badRequest().body("Invalid brain id " + robotDto.getBrain_series());
            }
            robot.setBrain_series(robotDto.getBrain_series());
        }
        if (robotDto.getEye_series() == null){
            return ResponseEntity.badRequest().body("No eyes specified.");
        } else {
            EyesSensors eyes = eyesService.findByReleaseSeries(robotDto.getEye_series());
            if (eyes == null){
                return ResponseEntity.badRequest().body("Invalid eyes id " + robotDto.getEye_series());
            }
            robot.setEye_series(robotDto.getEye_series());
        }
        if (robotDto.getOperator_post_id() != null){
            Post post = postService.findById(robotDto.getOperator_post_id());
            if (robotDto.getOperator_post_id() == null){
                return ResponseEntity.badRequest().body("Invalid asteroid id " + robot.getAsteroid_id());
            }
            Role role = roleService.findById(post.getRoleId());
            if (!role.getCan_operate_robot()){
                return ResponseEntity.badRequest().body("This operator post " + robot.getOperator_post_id() + " cannot operate robots.");
            }
            robot.setOperator_post_id(robotDto.getOperator_post_id());
        }
        robot.setHit_points(bodyService.findByReleaseSeries(robotDto.getBody_series()).getMax_hit_points());
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
        if (robotDto.getOperator_post_id() != null){
            Post operatorPost = postService.findById(robotDto.getOperator_post_id());
            if (operatorPost == null){
                return ResponseEntity.badRequest().body("Post " + robotDto.getOperator_post_id() + " not found.");
            }
            Role operatorRole = roleService.findById(operatorPost.getRoleId());
            if (!operatorRole.getCan_operate_robot()){
                return ResponseEntity.badRequest().body("New operator post " + robotDto.getOperator_post_id() + " does not have authority to operate robots.");
            }
            robot.setOperator_post_id(robotDto.getOperator_post_id());
        } else {
            robot.setOperator_post_id(origin.getOperator_post_id());
        }

        if (robotDto.getAsteroid_id() != null){
            Post operatorPost = postService.findById(robot.getOperator_post_id());
            Role role = roleService.findById(operatorPost.getRoleId());
            if (!role.getCan_operate_robot()){
                return ResponseEntity.badRequest().body("Operator post " + robot.getOperator_post_id() + " does not have authority to move robot.");
            }

            robot.setAsteroid_id(robotDto.getAsteroid_id());
        } else {
            robot.setAsteroid_id(origin.getAsteroid_id());
        }

        if (robotDto.getBody_series() != null){
            Body body = bodyService.findByReleaseSeries(robotDto.getBody_series());
            if (body == null){
                return ResponseEntity.badRequest().body("Body " + robotDto.getBody_series() + " not found.");
            }
            robot.setBody_series(robotDto.getBody_series());
        } else {
            robot.setBody_series(origin.getBody_series());
        }

        if (robotDto.getEye_series() != null){
            EyesSensors eyesSensors = eyesService.findByReleaseSeries(robotDto.getEye_series());
            if (eyesSensors == null){
                return ResponseEntity.badRequest().body("Eyes " + robotDto.getEye_series() + " not found.");
            }
            robot.setEye_series(robotDto.getEye_series());
        } else {
            robot.setEye_series(origin.getEye_series());
        }

        if (robotDto.getBrain_series() != null){
            PositronicBrain brain = brainService.findByReleaseSeries(robotDto.getBrain_series());
            if (brain == null){
                return ResponseEntity.badRequest().body("Brain " + robotDto.getBrain_series() + " not found.");
            }
            robot.setBrain_series(robotDto.getBrain_series());
        } else {
            robot.setBrain_series(origin.getBrain_series());
        }
        Body body = bodyService.findByReleaseSeries(robot.getBody_series());
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

    @PostMapping("/{robot_id}/extract")
    public ResponseEntity extract(@PathVariable Long robot_id, @RequestBody ExtractRequestDto requestDto){
        Long deposit_id = requestDto.getDeposit_id();
        Robot robot = robotService.findById(robot_id);
        Post operator_post = postService.findById(robot.getOperator_post_id());
        Role operator_role = roleService.findById(operator_post.getRoleId());
        if (!operator_role.getCan_operate_robot()){
            return ResponseEntity.badRequest().body("Robot " + robot_id + " is attached to employee, that cannot operate robots.");
        }
        ExtractionStatus status = robotService.extract(robot, deposit_id, operator_post.getDepartmentId());
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

    @GetMapping("/{id}/repair")
    public ResponseEntity repair(@PathVariable Long id){
        Robot robot = robotService.repair(id);
        if (robot != null){
            return ResponseEntity.ok("Repaired.");
        } else {
            return ResponseEntity.badRequest().body("Robot " + id + " not found.");
        }
    }
}
