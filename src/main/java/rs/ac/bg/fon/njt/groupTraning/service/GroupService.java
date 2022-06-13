package rs.ac.bg.fon.njt.groupTraning.service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.njt.groupTraning.model.Group;
import rs.ac.bg.fon.njt.groupTraning.repository.GroupRepository;

@Service
public class GroupService {
    
   
    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository=groupRepository;
    }

    public Group loadGroupById(Long id) throws Exception {
        System.out.println("group id "+id);
        Optional<Group> optionalGroup = groupRepository.findById(id);
        System.out.println("group fonudend "+optionalGroup.get());
        if (!optionalGroup.isPresent()) throw new Exception("Group with id = " + id + "does not exist");
        return optionalGroup.get();
    }

     public Group loadGroupByName(String name){
        return this.groupRepository.findByName(name);
    }
    public List<Group> loadGroups() {
        return groupRepository.findAll();
    }

    //@Transactional
    public Group addGroup(Group toAdd) {
//        moguce nakon uspesne autentifikacije
      //  newTrainer.getUser().setRole(User.Role.ROLE_ADMIN);
        return groupRepository.save(toAdd);
    }

   // @Transactional
    public Group updateGroup(Long id, Group toUpdate) throws Exception {
        Optional<Group> optionalGroup= groupRepository.findById(id);
        if (!optionalGroup.isPresent())
            throw new Exception("Cannot update group. group with id = " + id + "does not exist");
        toUpdate.setId(id);
//        ovo smemo da uradimo jer je jwt token vec proveren od strane Spring Security
  //      newPerformer.getUser().setRole(User.Role.ROLE_ADMIN);
        return groupRepository.save(toUpdate);
    }

   @Transactional
    public Group deleteGroupById(Long id) throws Exception {
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (!optionalGroup.isPresent())
            throw new Exception("Cannot delete group. Group with id = " + id + "does not exist");
        groupRepository.deleteById(id);
        return optionalGroup.get();
    }
    
}
