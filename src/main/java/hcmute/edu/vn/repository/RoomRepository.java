package hcmute.edu.vn.repository;

import hcmute.edu.vn.dto.request.SearchRoomsCriteria;
import hcmute.edu.vn.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT r FROM Room r WHERE " +
            "(:#{#criteria.adults} IS NULL OR r.numberOfAdults >= :#{#criteria.adults}) AND " +
            "(:#{#criteria.children} IS NULL OR r.numberOfChildren >= :#{#criteria.children}) AND " +
            "(:#{#criteria.bedType} IS NULL OR r.type = :#{#criteria.bedType})")
    List<Room> findRoomsByCriteria(SearchRoomsCriteria criteria);
}
