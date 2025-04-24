package hcmute.edu.vn.repository;

import hcmute.edu.vn.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    @Query("SELECT v FROM Voucher v WHERE v.code = :code")
    Voucher findByCode(@RequestParam("code") String code);
}
