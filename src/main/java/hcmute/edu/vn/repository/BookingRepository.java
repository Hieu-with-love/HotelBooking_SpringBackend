package hcmute.edu.vn.repository;

import hcmute.edu.vn.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
