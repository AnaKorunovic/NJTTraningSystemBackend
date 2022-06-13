package rs.ac.bg.fon.njt.groupTraning.service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.njt.groupTraning.model.Presence;
import rs.ac.bg.fon.njt.groupTraning.repository.PresenceRepository;

@Service
public class PresenceService {
    
    private final PresenceRepository presenceRepository;

    @Autowired
    public PresenceService(PresenceRepository repository) {
        this.presenceRepository = repository;
    }

    public Presence loadPresenceByMemAndApp(Long memId,Long appId) throws Exception {
        Optional<Presence> optionalPresence= presenceRepository.findByMemberAndAppointment(memId, appId);
        if (!optionalPresence.isPresent()) throw new Exception("Presence with MemberId = " + memId +" and AppId= "+appId+" does not exist");
        return optionalPresence.get();
    }
     public List<Presence> loadPresenceByApp(Long appId) throws Exception {
       List<Presence> presences = presenceRepository.findByAppointment(appId);
        if (presences.isEmpty()) throw new Exception("Presence with appId = " + appId + "does not exist");
        return presences;
    }

    public List<Presence> loadPresences() {
        return presenceRepository.findAll();
    }

  //  @Transactional
    public Presence addPresence(Presence toAdd) {
//        moguce nakon uspesne autentifikacije
System.out.println(toAdd);
        return presenceRepository.save(toAdd);
    }

    @Transactional
    public Presence updatePresence(Long memId,Long appId, Presence toUpdate) throws Exception {
        Optional<Presence> optionalPresence= presenceRepository.findByMemberAndAppointment(memId, appId);
        if (!optionalPresence.isPresent())
            throw new Exception("Cannot update presence. Presence with memId = " + memId +" and appId= "+appId+" does not exist");

        return presenceRepository.save(toUpdate);
    }

   @Transactional
    public Presence deletePresenceByMemAndApp(Long memId,Long appId) throws Exception {
        Optional<Presence> optionalPresence = presenceRepository.findByMemberAndAppointment(memId, appId);
        if (!optionalPresence.isPresent())
            throw new Exception("Cannot delete presence. Presence with memId = " + memId +" and appId= "+appId+" does not exist");
        presenceRepository.deleteByMemberAndAppointment(memId,appId);
        return optionalPresence.get();
    }
}
