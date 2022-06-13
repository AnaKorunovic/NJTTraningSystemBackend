package rs.ac.bg.fon.njt.groupTraning.repository;

import java.util.Date;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.njt.groupTraning.model.Appointment;


public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findByDate(Date date);
    Appointment findByGroupId(Long groupId);
    Optional<Appointment> findByDateAndTime(Date date, String time);
}
