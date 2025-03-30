package hcmute.edu.vn.controller.partner;

import hcmute.edu.vn.dto.VoucherDto;
import hcmute.edu.vn.service.VoucherService;
import hcmute.edu.vn.service.impl.VoucherServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static hcmute.edu.vn.service.impl.VoucherServiceImpl.getVoucherDto;

@RestController
@RequestMapping("/api/partner/vouchers")
@RequiredArgsConstructor
public class VoucherController {
    private final VoucherService voucherService;

    @GetMapping
    public ResponseEntity<List<VoucherDto>> loadVouchers(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        List<VoucherDto> vouchers = voucherService.getVouchers(page,size).getContent().stream()
                .map(
                        VoucherServiceImpl::getVoucherDto
                ).toList();

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
