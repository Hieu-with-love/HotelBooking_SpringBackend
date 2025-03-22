package hcmute.edu.vn.repository;

import hcmute.edu.vn.model.User;
import hcmute.edu.vn.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    Optional<VerificationCode> findByCode(String code);
    Optional<VerificationCode> findByUser(User user);
}
