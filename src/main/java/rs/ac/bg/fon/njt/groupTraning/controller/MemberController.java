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
import rs.ac.bg.fon.njt.groupTraning.dto.MemberDto;
import rs.ac.bg.fon.njt.groupTraning.model.Member;
import rs.ac.bg.fon.njt.groupTraning.model.User;
import rs.ac.bg.fon.njt.groupTraning.model.Trainer;
import rs.ac.bg.fon.njt.groupTraning.model.Group;
import rs.ac.bg.fon.njt.groupTraning.service.GroupService;
import rs.ac.bg.fon.njt.groupTraning.service.MemberService;
import rs.ac.bg.fon.njt.groupTraning.service.TrainerService;
import rs.ac.bg.fon.njt.groupTraning.service.UserService;

@RestController
@RequestMapping("/api/members")
@CrossOrigin(origins = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class MemberController {

    
    private final MemberService memberService;
    private final GroupService groupService;
    private final UserService userService;
     private final TrainerService trainerService;       

    @Autowired
    public MemberController(MemberService memberService,GroupService groupService,UserService userService,TrainerService trainerService) {
        this.memberService = memberService;
        this.groupService=groupService;
        this.userService=userService;
        this.trainerService=trainerService;
        
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
    public ResponseEntity<List<MemberDto>> getAllMembers() {
       List<Member> memebers = memberService.loadMembers();
        List<MemberDto> memberDtos = new ArrayList<>();
        User user = null;
        Group group=null;
        for (Member m:memebers) {
            try {
               user = userService.loadUserById(m.getUser());
               group=groupService.loadGroupById(m.getGroupId());
                if (user != null || group!=null) {
                    memberDtos.add(new MemberDto(m.getId(), m.getName(),m.getLastname(),m.getNumber(),m.getJmbg(), group, user.getUsername()));
                }
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }
        return ResponseEntity.ok(memberDtos);
    }
     @RequestMapping(value = "/group/{id}")
    public ResponseEntity<List<MemberDto>> getMembersByGroupId(@PathVariable Long id) {
        List<MemberDto> memberDtos = new ArrayList<>();
            User user = null;
            Group group=null;
        try {
            List<Member> members = memberService.loadMembersByGroupId(id);
            
            for (Member m:members) {
                try {
                    user = userService.loadUserById(m.getUser());
                    group=groupService.loadGroupById(m.getGroupId());
                    if (user != null && group!=null) {
                        memberDtos.add(new MemberDto(m.getId(),m.getName(),m.getLastname(),m.getNumber(),m.getJmbg(),group, user.getUsername()));
                    }
                } catch (Exception ex) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                }
            }
            
        } catch (Exception ex) {
            Logger.getLogger(PresenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ResponseEntity.ok(memberDtos);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public ResponseEntity<Member>   addMember(@RequestBody Member toAdd) {
        Member memberDB=null;
        try {
          try{
              memberDB = this.memberService.loadMemberByJmbg(toAdd.getJmbg());
          }catch(Exception ex){
              memberDB=null;
          }
//          Member memberDB=null;
            System.out.println(toAdd);
            if (memberDB == null) {
                return new ResponseEntity<>(memberService.addMember(toAdd), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<?> updateMember(@PathVariable Long id,
            @RequestBody Member toUpdate) {
        try {
            Member memberDB = memberService.loadMemberById(id);
            Group group=groupService.loadGroupById(toUpdate.getGroupId());
            Trainer trainer=trainerService.loadTrainerById(group.getTrainer());
            if (memberDB != null) {
                memberDB.setName(toUpdate.getName());
                memberDB.setLastname(toUpdate.getLastname());
                memberDB.setJmbg(toUpdate.getJmbg());
                memberDB.setNumber(toUpdate.getNumber());
                group.setTrainer(trainer.getId());
                memberDB.setGroupId(group.getId());
               

                Member m= memberService.updateMember(id, toUpdate);
                System.out.println("Member " + m.getName() +" updated");
                return ResponseEntity.ok(m);

            } else {
                return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable Long id) {
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
