package hcmute.edu.vn.service.impl;

import hcmute.edu.vn.converter.VoucherConverter;
import hcmute.edu.vn.dto.VoucherDto;
import hcmute.edu.vn.dto.response.PageResponse;
import hcmute.edu.vn.dto.response.VoucherResponse;
import hcmute.edu.vn.model.Voucher;
import hcmute.edu.vn.repository.VoucherRepository;
import hcmute.edu.vn.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository voucherRepository;
    private final VoucherConverter voucherConverter;

    @Override
    public VoucherDto getVoucher(Long voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new RuntimeException("Voucher not found with id: " + voucherId));
        return getVoucherDto(voucher);
    }

    public static VoucherDto getVoucherDto(Voucher voucher) {
        VoucherDto voucherDto = new VoucherDto();
        voucherDto.setId(voucher.getId());
        voucherDto.setCode(voucher.getCode());
        voucherDto.setDiscountPercent(voucher.getDiscountPercent());
        voucherDto.setDiscountAmount(voucher.getDiscountAmount());
        voucherDto.setActive(voucher.isActive());
        voucherDto.setCreatedAt(voucher.getCreatedAt());
        voucherDto.setExpirationDate(voucher.getExpirationDate());
        voucherDto.setQuantity(voucher.getQuantity());
        return voucherDto;
    }

    @Override
    public PageResponse<VoucherResponse> getVouchers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Voucher> vouchers = voucherRepository.findAll(pageable);
        return voucherConverter.toPageResponse(vouchers);
    }

    @Override
    public void addVoucher(VoucherDto voucherDto) {
        try{
            Voucher createVoucher = new Voucher();
            createVoucher.setCode(voucherDto.getCode());
            createVoucher.setQuantity(voucherDto.getQuantity());
            createVoucher.setDiscountAmount(voucherDto.getDiscountAmount());
            createVoucher.setDiscountPercent(voucherDto.getDiscountPercent());
            createVoucher.setExpirationDate(voucherDto.getExpirationDate());
            createVoucher.setActive(true);
            createVoucher.setCreatedAt(LocalDate.now());

            voucherRepository.save(createVoucher);
        }catch (Exception e){
            throw new RuntimeException("Lỗi khi thêm voucher: " + e.getMessage());
        }
    }

    @Override
    public void updateVoucher(Long voucherId, VoucherDto voucherDto) {
        Voucher existingVoucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new RuntimeException("Voucher not found with id: " + voucherId));

        existingVoucher.setCode(voucherDto.getCode());
        existingVoucher.setQuantity(voucherDto.getQuantity());
        existingVoucher.setDiscountAmount(voucherDto.getDiscountAmount());
        existingVoucher.setDiscountPercent(voucherDto.getDiscountPercent());
        existingVoucher.setExpirationDate(voucherDto.getExpirationDate());

        voucherRepository.save(existingVoucher);
    }

    @Override
    public void updateActive(Long voucherId) {
        Voucher existingVoucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new RuntimeException("Voucher not found with id: " + voucherId));

        existingVoucher.setActive(!existingVoucher.isActive());
        voucherRepository.save(existingVoucher);
    }

    @Override
    public void deleteVoucher(Long voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new RuntimeException("Voucher not found with id: " + voucherId));

        voucherRepository.delete(voucher);
    }
}
