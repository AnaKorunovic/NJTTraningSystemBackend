package rs.ac.bg.fon.njt.groupTraning.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.njt.groupTraning.model.Appointment;
import rs.ac.bg.fon.njt.groupTraning.repository.AppointmentRepository;

@Service
public class AppointmentService {
    
     private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(AppointmentRepository repository) {
        this.appointmentRepository = repository;
    }

    public Appointment loadAppointmentById(Long id) throws Exception {
        Optional<Appointment> optionalApp = appointmentRepository.findById(id);
        if (!optionalApp.isPresent()) throw new Exception("Appointment with id = " + id + "does not exist");
        return optionalApp.get();
    }
     public Appointment loadAppointmentbyGroup(Long id) throws Exception {
       return appointmentRepository.findByGroupId(id);
        
    }

    public List<Appointment> loadAppointments() {
        return appointmentRepository.findAll();
    }

  //  @Transactional
    public Appointment addAppointment(Appointment toAdd) {

System.out.println(toAdd);
       
        return appointmentRepository.save(toAdd);
    }

    @Transactional
    public Appointment updateAppointment(Long id, Appointment toUpdate) throws Exception {
        Optional<Appointment> optionalApp= appointmentRepository.findById(id);
        if (!optionalApp.isPresent())
            throw new Exception("Cannot update appointment. Appointment with id = " + id + "does not exist");
        toUpdate.setId(id);

        return appointmentRepository.save(toUpdate);
    }

   @Transactional
    public Appointment deleteAppointmentById(Long id) throws Exception {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
        if (!optionalAppointment.isPresent())
            throw new Exception("Cannot delete appointment. Appointment with id = " + id + "does not exist");
        appointmentRepository.deleteById(id);
        return optionalAppointment.get();
    }

    public Appointment loadAppointmentByDateAndTime(Date date, String time) throws Exception {
        Optional<Appointment> optionalApp= appointmentRepository.findByDateAndTime(date, time);
        if (!optionalApp.isPresent()) throw new Exception("DB dont contains appointment with date = " + date + " and time= "+time);
        return optionalApp.get();
    }
}
