/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.groupTraning.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.njt.groupTraning.dto.PresenceDto;
import rs.ac.bg.fon.njt.groupTraning.model.Appointment;
import rs.ac.bg.fon.njt.groupTraning.model.Member;
import rs.ac.bg.fon.njt.groupTraning.model.Presence;
import rs.ac.bg.fon.njt.groupTraning.model.User;
import rs.ac.bg.fon.njt.groupTraning.service.AppointmentService;
import rs.ac.bg.fon.njt.groupTraning.service.MemberService;
import rs.ac.bg.fon.njt.groupTraning.service.PresenceService;
import rs.ac.bg.fon.njt.groupTraning.service.UserService;

@RestController
@RequestMapping("/api/presences")
@CrossOrigin(origins = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})

public class PresenceController {
    
    private final MemberService memberService;
    private final AppointmentService appService;
    private final UserService userService;
     private final PresenceService presenceService;       

    @Autowired
    public PresenceController(MemberService memberService,AppointmentService appService,UserService userService,PresenceService presenceService) {
        this.memberService = memberService;
        this.appService=appService;
        this.userService=userService;
        this.presenceService=presenceService;
        
    }

//    @RequestMapping("/{id}")
//    public ResponseEntity<?> getMemeberById(@PathVariable Long id) {
//        try {
//            Member member = memberService.loadMemberById(id);
//            System.out.println("Memberr " + member.getName() + " " + member.getLastname() + "returned");
//            return ResponseEntity.ok(member);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }

    @RequestMapping()
    public ResponseEntity<List<PresenceDto>> getAllPresences() {
       List<Presence> presences = presenceService.loadPresences();
        List<PresenceDto> presenceDtos = new ArrayList<>();
        User user = null;
        Member member=null;
        Appointment app=null;
        for (Presence p:presences) {
            try {
               user = userService.loadUserById(p.getUser());
               member=memberService.loadMemberById(p.getMember());
               app=appService.loadAppointmentById(p.getAppointment());
                if (user != null && member!=null && app!=null) {
                    presenceDtos.add(new PresenceDto(p.getPresence(),p.getMessage(),app,member, user.getUsername()));
                }
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }
        return ResponseEntity.ok(presenceDtos);
    }
     @RequestMapping(value = "/appointment/{id}")
    public ResponseEntity<List<PresenceDto>> getPresencesByAppointment(@PathVariable Long id) {
        List<PresenceDto> presenceDtos = new ArrayList<>();
            User user = null;
            Member member=null;
            Appointment app=null;
        try {
            List<Presence> presences = presenceService.loadPresenceByApp(id);
            
            for (Presence p:presences) {
                try {
                    user = userService.loadUserById(p.getUser());
                    member=memberService.loadMemberById(p.getMember());
                    app=appService.loadAppointmentById(p.getAppointment());
                    if (user != null && member!=null && app!=null) {
                        presenceDtos.add(new PresenceDto(p.getPresence(),p.getMessage(),app,member, user.getUsername()));
                    }
                } catch (Exception ex) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                }
            }
            
        } catch (Exception ex) {
            Logger.getLogger(PresenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ResponseEntity.ok(presenceDtos);
    }

    /*
    @GetMapping("/event/{event}")
    public ResponseEntity<List<Performer>> getAllPerformersOfEvent(@PathVariable("event") Long eventId){
        List<Performance> performances = performanceService.getAllPerformancesOfEvent(eventId);
        List<Performer> result = performances.stream().map(Performance::getPerformer).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }*/

    @PostMapping(value = "/add")
    public ResponseEntity<Presence>   addPresence(@RequestBody Presence toAdd) {
        try {
Presence presenceDB=this.presenceService.loadPresenceByMemAndApp(toAdd.getMember(), toAdd.getAppointment());
         
            System.out.println(toAdd);
            if (presenceDB == null) {
                return new ResponseEntity<>(presenceService.addPresence(toAdd), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping(value = "/addList")
    public ResponseEntity<List<Presence>>   addPresences(@RequestBody ObjectNode json ) throws IOException {
        
        JsonNode toAdd = json.get("toAdd");

        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.readerFor(new TypeReference<List<Presence>>() {
        });

        List<Presence> presences = reader.readValue(toAdd);

        boolean success=true;
//        try {
//            if(toAdd.size()>0){
//                System.out.println("r.controller.Presen000000000000");
//Presence presenceDB=this.presenceService.loadPresenceByMemAndApp(toAdd.get(0).getMember(), toAdd.get(0).getAppointment());
//         
//            if (presenceDB == null) {
                for(Presence p:presences){
                    Presence added=presenceService.addPresence(p);
                    System.out.println("rs.ac.bg.fon.njt.groupTraning.controller.PresenceController.addPresences()");
                    if(added==null) {
                        success=false;
                        System.out.println("dswqdw");

                        break;
                    }
                }
//            }
        if(success)return new ResponseEntity<>(presences, HttpStatus.OK);
         else return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            
//            } else return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        } catch (Exception ex) {
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updatePrsence(@PathVariable(name = "mem") Long memId,
            @PathVariable(name = "app") Long appId,
            @RequestBody Presence toUpdate) {
        try {
            Presence presenceDB = presenceService.loadPresenceByMemAndApp(memId, appId);
            Appointment app=appService.loadAppointmentById(toUpdate.getAppointment());
            Member mem=memberService.loadMemberById(toUpdate.getMember());
            if (presenceDB != null) {
                presenceDB.setPresence(toUpdate.getPresence());
                presenceDB.setMessage(toUpdate.getMessage());
                
               

                Presence p= presenceService.updatePresence(memId, appId, toUpdate);
                System.out.println("Presence mem=" + memId +"/app= "+appId +" updated");
                return ResponseEntity.ok(p);

            } else {
                return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deletePresence(@PathVariable(name = "mem") Long memId,
            @PathVariable(name = "app") Long appId) {
        try {
            Presence p = presenceService.deletePresenceByMemAndApp(memId, appId);
            System.out.println("Presence mem=" + memId +"/app= "+appId +" deleted.");
            return ResponseEntity.ok(p);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
