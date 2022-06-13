package rs.ac.bg.fon.njt.groupTraning.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.njt.groupTraning.dto.TrainerDto;
import rs.ac.bg.fon.njt.groupTraning.model.Trainer;
import rs.ac.bg.fon.njt.groupTraning.model.User;
import rs.ac.bg.fon.njt.groupTraning.service.TrainerService;
import rs.ac.bg.fon.njt.groupTraning.service.UserService;

@RestController
@RequestMapping("/api/trainers")
@CrossOrigin(origins="*")
public class TrainerController {

    private final TrainerService trainerService;
    private final UserService userService;

    @Autowired
    public TrainerController(TrainerService trainerService, UserService userService) {
        this.trainerService = trainerService;
        this.userService = userService;
    }

    @RequestMapping("/{id}")
    public ResponseEntity<?> getTrainerById(@PathVariable Long id) {
        try {
            Trainer trainer = trainerService.loadTrainerById(id);
            System.out.println("Trainer " + trainer.getName() + " " + trainer.getLastname() + "returned");
            return ResponseEntity.ok(trainer);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping()
    public ResponseEntity<List<TrainerDto>> getAllTrainers() {
        List<Trainer> trainers = trainerService.loadTrainers();
        
        List<TrainerDto> trainerDtos = new ArrayList<>();
        User user = null;
        for (Trainer t : trainers) {
            try {
                user = userService.loadUserById(t.getUser());
                if (user != null) {
                    trainerDtos.add(new TrainerDto(
                            t.getId(), t.getName(), t.getLastname(), t.getNumber(), t.getEmail(), user.getUsername()));
                }
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }
        return ResponseEntity.ok(trainerDtos);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Trainer> addTrainer(@RequestBody Trainer newTrainer) {
        Trainer trainerDB = this.trainerService.loadTrainerByEmailAndNumber(newTrainer.getEmail(), newTrainer.getNumber());
        if (trainerDB == null) {
            return new ResponseEntity<>(trainerService.addTrainer(newTrainer), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<?> updateTrainer(@PathVariable Long id,
            @RequestBody Trainer toUpdate) {
        try {
            Trainer trainerDB = trainerService.loadTrainerById(id);
//            Trainer trainerDB1 = this.trainerService.loadTrainerByEmailAndNumber(toUpdate.getEmail(), toUpdate.getNumber());
            
            if (trainerDB != null) {
                trainerDB.setName(toUpdate.getName());
                trainerDB.setLastname(toUpdate.getLastname());
                trainerDB.setEmail(toUpdate.getEmail());
                trainerDB.setNumber(toUpdate.getNumber());

                Trainer t = trainerService.updateTrainer(id, trainerDB);
                System.out.println("Trainer " + t.getName() + " " + t.getLastname() + " updated");
                return ResponseEntity.ok(t);

            } else {
                return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<?> deleteTrainer(@PathVariable Long id) {
        try {
            Trainer t = trainerService.deleteTrainerById(id);
            System.out.println("Trainer " + t.getName() + " " + t.getLastname() + " deleted.");
            return ResponseEntity.ok(t);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
