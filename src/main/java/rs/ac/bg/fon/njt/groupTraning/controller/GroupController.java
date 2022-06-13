package rs.ac.bg.fon.njt.groupTraning.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.njt.groupTraning.dto.GroupDto;
import rs.ac.bg.fon.njt.groupTraning.model.Group;
import rs.ac.bg.fon.njt.groupTraning.model.User;
import rs.ac.bg.fon.njt.groupTraning.model.Trainer;
import rs.ac.bg.fon.njt.groupTraning.service.GroupService;
import rs.ac.bg.fon.njt.groupTraning.service.TrainerService;
import rs.ac.bg.fon.njt.groupTraning.service.UserService;

@RestController
@RequestMapping("/api/groups")
@CrossOrigin(origins = "*")
public class GroupController {

    
    private final GroupService groupService;
    private final TrainerService trainerService;
    private final UserService userService;

    @Autowired
    public GroupController(GroupService groupService,TrainerService trainerService,UserService userService) {
        this.groupService = groupService;
        this.trainerService=trainerService;
        this.userService=userService;
    }

    @RequestMapping("/{id}")
    public ResponseEntity<?> getGroupById(@PathVariable Long id) {
        try {
            Group g = groupService.loadGroupById(id);
            System.out.println("Group " + g.getName() + " returned");
            return ResponseEntity.ok(g);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping()
    public ResponseEntity<List<GroupDto>> getAllgroups() {
        List<Group> groups = groupService.loadGroups();
        
        List<GroupDto> groupDtos = new ArrayList<>();
        User user = null;
        Trainer trainer=null;
        for (Group g:groups) {
            try {
                user = userService.loadUserById(g.getUser());
                trainer=trainerService.loadTrainerById(g.getTrainer());
                if (user != null || trainer!=null) {
                    groupDtos.add(new GroupDto(g.getId(), g.getName(), trainer, user.getUsername()));
                    
                }
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }
        return ResponseEntity.ok(groupDtos);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public ResponseEntity<Group> addGroup(@RequestBody Group toAdd) {
        Group groupDB = this.groupService.loadGroupByName(toAdd.getName());
        if (groupDB == null) {
            return new ResponseEntity<>(groupService.addGroup(toAdd), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<?> updateGroup(@PathVariable Long id,
            @RequestBody Group toUpdate) {
        try {
            Group groupDB = groupService.loadGroupById(id);
            Trainer trainer=trainerService.loadTrainerById(toUpdate.getTrainer());
            if (groupDB != null) {
                groupDB.setName(toUpdate.getName());
                groupDB.setTrainer(trainer.getId());
               

                Group g= groupService.updateGroup(id, groupDB);
                System.out.println("Group " + g.getName() +" updated");
                return ResponseEntity.ok(g);

            } else {
                return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
        try {
            Group g = groupService.deleteGroupById(id);
            System.out.println("Group " + g.getName() + " deleted");
            return ResponseEntity.ok(g);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
