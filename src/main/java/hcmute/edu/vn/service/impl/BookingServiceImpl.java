package hcmute.edu.vn.service.impl;

import hcmute.edu.vn.converter.RoomConverter;
import hcmute.edu.vn.dto.request.BookingRequest;
import hcmute.edu.vn.dto.request.RoomRequest;
import hcmute.edu.vn.dto.response.BookingResponse;
import hcmute.edu.vn.dto.response.RoomResponse;
import hcmute.edu.vn.model.*;
import hcmute.edu.vn.repository.*;
import hcmute.edu.vn.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final VoucherRepository voucherRepository;
    private final RoomRepository roomRepository;
    private final BookingDetailRepository bookingDetailRepository;
    private final PaymentRepository paymentRepository;
    private final RoomConverter roomConverter;

    @Override
    public Booking createBooking(BookingRequest request) {
        Payment payment = paymentRepository.findById(request.getPaymentMethod().getId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        Booking booking = new Booking();
        booking.setCheckIn(request.getCheckInDate());
        booking.setCheckOut(request.getCheckOutDate());

/*        Voucher voucher = voucherRepository.findByCode(request.getVoucherCode());*/
        BigDecimal price = BigDecimal.ZERO;
        for (RoomRequest room : request.getRooms()){
            price = price.add(room.getPrice());
        }

        /*if (voucher.getDiscountPercent() > 0){
            price = price.subtract(price.multiply(voucher.getDiscountAmount()).divide(BigDecimal.valueOf(100)));
        }else if (voucher.getDiscountAmount().compareTo(BigDecimal.ZERO) > 0){
            price = price.subtract(voucher.getDiscountAmount());
        }*/

        booking.setTotalPrice(price);
        booking.setSpecialRequest(request.getSpecialRequests());

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email " + email));
        booking.setUser(user);
        booking.setPaymentType(payment);
        booking.setStatus(request.getStatus());

        Booking savedBooking = bookingRepository.save(booking);

        // Handle save booking details
        if (request.getRooms().size() == 1){
            BookingDetail bookingDetail = new BookingDetail();
            bookingDetail.setBooking(booking);
            Room room = roomRepository.findById(request.getRooms().get(0).getId())
                    .orElseThrow(() -> new RuntimeException("Room not found with id " + request.getRooms().get(0).getId()));
            bookingDetail.setRoom(room);
            bookingDetail.setPrice(request.getRooms().get(0).getPrice());
            bookingDetailRepository.save(bookingDetail);

        }else if (request.getRooms().size() > 1){
            for (RoomRequest roomRequest : request.getRooms()){
                BookingDetail bookingDetail = new BookingDetail();
                bookingDetail.setBooking(booking);
                Room room = roomRepository.findById(roomRequest.getId())
                        .orElseThrow(() -> new RuntimeException("Room not found with id " + roomRequest.getId()));
                bookingDetail.setRoom(room);
                bookingDetail.setPrice(room.getPrice());
                bookingDetailRepository.save(bookingDetail);
            }
        }
        return savedBooking;
    }

    @Override
    public BookingResponse convertToBookingResponse(Booking booking) {
        BookingResponse bookingResponse = new BookingResponse();
        bookingResponse.setBookingId(booking.getId());
        String paymentMethod = booking.getPaymentType().getType().equals("CASH") ? "counter" : "vnpay";
        bookingResponse.setSelectedPaymentMethod(paymentMethod);
        bookingResponse.setTotalPrice(booking.getTotalPrice());
        bookingResponse.setCheckInDate(booking.getCheckIn());
        bookingResponse.setCheckOutDate(booking.getCheckOut());

        List<Room> rooms = roomRepository.findRoomsByBookingId(booking.getId());
        List<RoomResponse> roomsResponse = roomConverter.toRoomResponseList(rooms);
        bookingResponse.setSelectedRooms(roomsResponse);


        return bookingResponse;
    }
}
