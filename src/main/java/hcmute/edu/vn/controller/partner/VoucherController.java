package hcmute.edu.vn.controller.partner;

import hcmute.edu.vn.converter.VoucherConverter;
import hcmute.edu.vn.dto.VoucherDto;
import hcmute.edu.vn.dto.response.PageResponse;
import hcmute.edu.vn.dto.response.VoucherResponse;
import hcmute.edu.vn.model.Voucher;
import hcmute.edu.vn.service.VoucherService;
import hcmute.edu.vn.service.impl.VoucherServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static hcmute.edu.vn.service.impl.VoucherServiceImpl.getVoucherDto;

@CrossOrigin("https://hotel-booking-zeta-azure.vercel.app")
@RestController
@RequestMapping("/api/partner/vouchers")
@RequiredArgsConstructor
public class VoucherController {
    private final VoucherService voucherService;
    private final VoucherConverter voucherConverter;

    @GetMapping
    public ResponseEntity<?> loadVouchers(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        PageResponse<VoucherResponse> vouchers = voucherService.getVouchers(page,size);
        return ResponseEntity.ok(vouchers);
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<VoucherDto> loadVoucher(@PathVariable Long voucherId) {
        VoucherDto voucher = voucherService.getVoucher(voucherId);
        return ResponseEntity.ok(voucher);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createVoucher(@RequestBody VoucherDto voucherDto){
        try{
            voucherService.addVoucher(voucherDto);
            return ResponseEntity.ok("Added voucher successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Lỗi khi thêm voucher: " + e.getMessage());
        }
    }

    @PutMapping("/update/{voucherId}")
    public ResponseEntity<String> updateVoucher(@RequestBody VoucherDto voucherDto,
                                                @PathVariable Long voucherId){
        try{
            voucherService.updateVoucher(voucherId, voucherDto);
            return ResponseEntity.ok("Updated voucher successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Loi khi update" + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{voucherId}")
    public ResponseEntity<String> deleteVoucher(@PathVariable Long voucherId){
        try{
            voucherService.deleteVoucher(voucherId);
            return ResponseEntity.ok("Deleted voucher successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Loi khi delete" + e.getMessage());
        }
    }

}
