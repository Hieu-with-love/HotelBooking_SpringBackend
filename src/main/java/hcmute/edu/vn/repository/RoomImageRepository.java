package hcmute.edu.vn.repository;

import hcmute.edu.vn.model.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomImageRepository extends JpaRepository<RoomImage, Long> {
    @Query("SELECT ri FROM RoomImage ri WHERE ri.room.id = :roomId")
    List<RoomImage> findRoomImagesByRoomId(Long roomId);
}
