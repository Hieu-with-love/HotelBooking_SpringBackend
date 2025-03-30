package hcmute.edu.vn.service;

import hcmute.edu.vn.dto.VoucherDto;
import hcmute.edu.vn.model.Voucher;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VoucherService {
    VoucherDto getVoucher(Long voucherId);
    Page<Voucher> getVouchers(int page, int size);
    void addVoucher(VoucherDto voucherDto);
    void updateVoucher(Long voucherId, VoucherDto voucherDto);
    void updateActive(Long voucherId);
    void deleteVoucher(Long voucherId);
}
