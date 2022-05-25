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
import rs.ac.bg.fon.njt.groupTraning.model.Member;
import rs.ac.bg.fon.njt.groupTraning.service.MemberService;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @RequestMapping("/{id}")
    public ResponseEntity<?> getMemeberById(@PathVariable Long id) {
        try {
            Member member = memberService.loadMemberById(id);
            System.out.println("Memberr " + member.getName() + " " + member.getLastname() + "returned");
            return ResponseEntity.ok(member);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping()
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(memberService.loadMembers());
    }

    /*
    @GetMapping("/event/{event}")
    public ResponseEntity<List<Performer>> getAllPerformersOfEvent(@PathVariable("event") Long eventId){
        List<Performance> performances = performanceService.getAllPerformancesOfEvent(eventId);
        List<Performer> result = performances.stream().map(Performance::getPerformer).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }*/

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public Member addMember(@RequestBody Member toAdd) {
        return memberService.addMember(toAdd);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<?> updateMember(@PathVariable Long id,
            @RequestBody Member toUpdate) {
        try {
            Member m = memberService.updateMember(id, toUpdate);
            System.out.println("Member " + m.getName() + " " + m.getLastname() + " updated");
            return ResponseEntity.ok(m);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<?> deleteTrainer(@PathVariable Long id) {
        try {
            Member m = memberService.deleteMemeberById(id);
            System.out.println("Member " + m.getName() + " " + m.getLastname() + " deleted.");
            return ResponseEntity.ok(m);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
