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
import rs.ac.bg.fon.njt.groupTraning.model.Group;
import rs.ac.bg.fon.njt.groupTraning.service.GroupService;

@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
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
    public ResponseEntity<List<Group>> getAllgroups() {
        return ResponseEntity.ok(groupService.loadGroups());
    }

    /*
    @GetMapping("/event/{event}")
    public ResponseEntity<List<Performer>> getAllPerformersOfEvent(@PathVariable("event") Long eventId){
        List<Performance> performances = performanceService.getAllPerformancesOfEvent(eventId);
        List<Performer> result = performances.stream().map(Performance::getPerformer).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }*/

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public Group addGroup(@RequestBody Group toAdd) {
        return groupService.addGroup(toAdd);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<?> updateGroup(@PathVariable Long id,
            @RequestBody Group toUpdate) {
        try {
            Group g = groupService.updateGroup(id, toUpdate);
            System.out.println("Group " + g.getName() + " updated");
            return ResponseEntity.ok(g);
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
