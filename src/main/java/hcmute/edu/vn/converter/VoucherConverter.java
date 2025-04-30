package hcmute.edu.vn.converter;

import hcmute.edu.vn.dto.request.VoucherRequest;
import hcmute.edu.vn.dto.response.PageResponse;
import hcmute.edu.vn.dto.response.VoucherResponse;
import hcmute.edu.vn.model.Voucher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface VoucherConverter {
    VoucherConverter INSTANCE = Mappers.getMapper(VoucherConverter.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "quantity", expression = "java(voucherRequest.getQuantity())")
    Voucher toEntity(VoucherRequest voucherRequest);

    @Mapping(target = "remainingQuantity", expression = "java(voucher.getQuantity())")
    VoucherResponse toResponse(Voucher voucher);

    default List<VoucherResponse> toResponseList(List<Voucher> vouchers) {
        return vouchers.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

    }

    default List<Voucher> toEntityList(List<VoucherRequest> voucherRequests) {
        return voucherRequests.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    default PageResponse<VoucherResponse> toPageResponse(Page<Voucher> voucherPage) {
        return PageResponse.<VoucherResponse>builder()
                .content(toResponseList(voucherPage.getContent()))
                .pageNumber(voucherPage.getNumber())
                .pageSize(voucherPage.getSize())
                .totalElements(voucherPage.getTotalElements())
                .numberOfElements(voucherPage.getNumberOfElements())
                .totalPages(voucherPage.getTotalPages())
                .last(voucherPage.isLast())
                .first(voucherPage.isFirst())
                .build();
    }
} 