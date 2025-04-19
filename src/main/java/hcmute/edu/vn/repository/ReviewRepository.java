package hcmute.edu.vn.repository;

import hcmute.edu.vn.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.room.id = :roomId")
    List<Review> findReviewsByRoomId(@Param("roomId") Long roomId);
}
