package rs.ac.bg.fon.njt.groupTraning.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.njt.groupTraning.model.Trainer;
import rs.ac.bg.fon.njt.groupTraning.service.TrainerService;

@RestController
@RequestMapping("/trainers")
public class TrainerController {
    
    
    @Autowired
    private final TrainerService trainerService;

    @Autowired
    public TrainerController(TrainerService trainerService) {
        this.trainerService=trainerService;
    }

    @RequestMapping("/{id}")
    public ResponseEntity<?> getTrainerById(@PathVariable Long id){
        try {
            Trainer trainer=trainerService.loadTrainerById(id);
            System.out.println("Trainer " +trainer.getName()+" "+trainer.getLastname()+ "returned");
            return ResponseEntity.ok(trainer);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping()
    public ResponseEntity<List<Trainer>> getAllTrainers(){
        return ResponseEntity.ok(trainerService.loadTrainers());
    }
/*
    @GetMapping("/event/{event}")
    public ResponseEntity<List<Performer>> getAllPerformersOfEvent(@PathVariable("event") Long eventId){
        List<Performance> performances = performanceService.getAllPerformancesOfEvent(eventId);
        List<Performer> result = performances.stream().map(Performance::getPerformer).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }*/

    @RequestMapping(method = RequestMethod.POST,value="/add")
    public Trainer addTrainer(@RequestBody Trainer newTrainer){
        return trainerService.addTrainer(newTrainer);
    }

    @RequestMapping(method = RequestMethod.PUT,value="/{id}")
    public ResponseEntity<?> updateTrainer(@PathVariable Long id,
                             @RequestBody Trainer toUpdate){
        try {
            Trainer t=trainerService.updateTrainer(id, toUpdate);
            System.out.println("Trainer "+t.getName()+" "+t.getLastname()+" updated");
            return ResponseEntity.ok(t);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.DELETE,value="/{id}")
    public ResponseEntity<?> deleteTrainer(@PathVariable Long id){
        try {
            Trainer t=trainerService.deleteTrainerById(id);
             System.out.println("Trainer "+t.getName()+" "+t.getLastname()+" deleted.");
            return ResponseEntity.ok(t);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    
}
