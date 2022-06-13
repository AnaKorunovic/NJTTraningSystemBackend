package rs.ac.bg.fon.njt.groupTraning.service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.njt.groupTraning.model.Trainer;
import rs.ac.bg.fon.njt.groupTraning.repository.TrainerRepository;

@Service
public class TrainerService {
    
    private final TrainerRepository trainerRepository;

    @Autowired
    public TrainerService(TrainerRepository repository) {
        this.trainerRepository = repository;
    }

    public Trainer loadTrainerById(Long id) throws Exception {
        Optional<Trainer> optionalTrainer = trainerRepository.findById(id);
        if (!optionalTrainer.isPresent()) throw new Exception("Trainer with id = " + id + "does not exist");
        return optionalTrainer.get();
    }
    
    public Trainer loadTrainerByName(String name){
        return this.trainerRepository.findByName(name);
    }
    
     public Trainer loadTrainerByEmailAndNumber(String email,String number){
        return this.trainerRepository.findByEmailAndNumber(email, number);
    }

    public List<Trainer> loadTrainers() {
        return trainerRepository.findAll();
    }

    @Transactional
    public Trainer addTrainer(Trainer newTrainer) {
//        moguce nakon uspesne autentifikacije
        return trainerRepository.save(newTrainer);
    }

    @Transactional
    public Trainer updateTrainer(Long id, Trainer toUpdate) throws Exception {
        Optional<Trainer> optionalTrainer= trainerRepository.findById(id);
        if (!optionalTrainer.isPresent())
            throw new Exception("Cannot update trainer. Trainer with id = " + id + "does not exist");
        toUpdate.setId(id);
//        ovo smemo da uradimo jer je jwt token vec proveren od strane Spring Security
  //      newPerformer.getUser().setRole(User.Role.ROLE_ADMIN);
        return trainerRepository.save(toUpdate);
    }

   @Transactional
    public Trainer deleteTrainerById(Long id) throws Exception {
        Optional<Trainer> optionalTrainer = trainerRepository.findById(id);
        if (!optionalTrainer.isPresent())
            throw new Exception("Cannot delete trainer. Trainer with id = " + id + "does not exist");
        trainerRepository.deleteById(id);
        return optionalTrainer.get();
    }

    
}
