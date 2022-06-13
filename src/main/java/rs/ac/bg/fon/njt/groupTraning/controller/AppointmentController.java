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
import rs.ac.bg.fon.njt.groupTraning.dto.AppointmentDto;
import rs.ac.bg.fon.njt.groupTraning.dto.MemberDto;
import rs.ac.bg.fon.njt.groupTraning.model.Group;
import rs.ac.bg.fon.njt.groupTraning.model.Member;
import rs.ac.bg.fon.njt.groupTraning.model.User;
import rs.ac.bg.fon.njt.groupTraning.model.Appointment;
import rs.ac.bg.fon.njt.groupTraning.service.GroupService;
import rs.ac.bg.fon.njt.groupTraning.service.AppointmentService;
import rs.ac.bg.fon.njt.groupTraning.service.UserService;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})

public class AppointmentController {

    private final AppointmentService appointmentService;
    private final GroupService groupService;
    private final UserService userService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, GroupService groupService, UserService userService) {
        this.appointmentService = appointmentService;
        this.groupService = groupService;
        this.userService = userService;

    }

    @RequestMapping("/{id}")
    public ResponseEntity<?> getAppointmentById(@PathVariable Long id) {
        try {
            Appointment app = appointmentService.loadAppointmentById(id);
            Group group = groupService.loadGroupById(app.getGroupId());
            User user = userService.loadUserById(app.getUser());
            AppointmentDto toReturn = new AppointmentDto(app.getId(), app.getDate(), app.getTime(), group, user.getUsername());
            System.out.println("Appointment " + app.getDate() + "/" + app.getTime() + "/" + app.getGroupId() + " returned");
            return ResponseEntity.ok(toReturn);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping()
    public ResponseEntity<List<AppointmentDto>> getAllAppointments() {
        List<Appointment> apps = appointmentService.loadAppointments();
        List<AppointmentDto> appDtos = new ArrayList<>();
        User user = null;
        Group group = null;
        for (Appointment a : apps) {
            try {
                user = userService.loadUserById(a.getUser());
                group = groupService.loadGroupById(a.getGroupId());
                if (user != null || group != null) {
                    appDtos.add(new AppointmentDto(a.getId(), a.getDate(), a.getTime(), group, user.getUsername()));
                }
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }
        return ResponseEntity.ok(appDtos);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public ResponseEntity<Appointment> addAppointment(@RequestBody Appointment toAdd) {
        try {
            Appointment appDBGroup = appointmentService.loadAppointmentbyGroup(toAdd.getGroupId());
            
            if (appDBGroup == null) {
                return new ResponseEntity<>(appointmentService.addAppointment(toAdd), HttpStatus.OK);
            } else if ( appDBGroup.getDate() == toAdd.getDate()) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            } else{
               return new ResponseEntity<>(appointmentService.addAppointment(toAdd), HttpStatus.OK);

            }
            
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<?> updateAppointment(@PathVariable Long id,
            @RequestBody Appointment toUpdate) {
        try {
            Appointment appDB = appointmentService.loadAppointmentById(id);
            Group group = groupService.loadGroupById(toUpdate.getGroupId());

            if (appDB != null) {
                appDB.setDate(toUpdate.getDate());
                appDB.setTime(toUpdate.getTime());
                appDB.setGroupId(group.getId());

                Appointment a = appointmentService.updateAppointment(id, toUpdate);
                System.out.println("Appointment " + a.getDate() + "/" + a.getTime() + "/" + a.getGroupId() + " updated");
                return ResponseEntity.ok(a);

            } else {
                return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id) {
        try {
            Appointment a = appointmentService.deleteAppointmentById(id);
            System.out.println("Member " + a.getDate() + "/" + a.getTime() + "/" + a.getGroupId() + " deleted.");
            return ResponseEntity.ok(a);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
