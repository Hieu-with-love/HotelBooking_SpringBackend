package hcmute.edu.vn.service.impl;

import hcmute.edu.vn.converter.RoomConverter;
import hcmute.edu.vn.converter.UserConverter;
import hcmute.edu.vn.dto.request.BookingRequest;
import hcmute.edu.vn.dto.request.RoomRequest;
import hcmute.edu.vn.dto.response.BookingResponse;
import hcmute.edu.vn.dto.response.RoomResponse;
import hcmute.edu.vn.dto.response.UserResponse;
import hcmute.edu.vn.model.*;
import hcmute.edu.vn.repository.*;
import hcmute.edu.vn.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    private final UserConverter userConverter;

    @Override
    @Transactional
    public Booking createBooking(BookingRequest request) {
        // 1. Validate and get payment method
        Payment payment = paymentRepository.findById(request.getPaymentMethod().getId())
                .orElseThrow(() -> new RuntimeException("Payment method not found"));

        // 2. Get current user
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email " + email));

        // 3. Calculate total price
        BigDecimal totalPrice = calculateTotalPrice(request.getRooms());

        // 4. Create and save the booking first
        Booking booking = new Booking();
        booking.setCheckIn(request.getCheckInDate());
        booking.setCheckOut(request.getCheckOutDate());
        booking.setTotalPrice(totalPrice);
        booking.setSpecialRequest(request.getSpecialRequests());
        booking.setUser(user);
        booking.setPaymentType(payment);
        booking.setStatus(request.getStatus());
        
        // Save booking first to get the ID
        Booking savedBooking = bookingRepository.save(booking);
        
        // 5. Create booking details
        List<BookingDetail> bookingDetails = createBookingDetails(savedBooking, request.getRooms());
        
        // 6. Save all booking details in a single batch
        bookingDetailRepository.saveAll(bookingDetails);
        
        return savedBooking;
    }
    
    /**
     * Calculate the total price for all rooms in the booking
     */
    private BigDecimal calculateTotalPrice(List<RoomRequest> rooms) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (RoomRequest room : rooms) {
            totalPrice = totalPrice.add(room.getPrice());
        }
        return totalPrice;
    }
    
    /**
     * Create booking details for each room in the booking
     */
    private List<BookingDetail> createBookingDetails(Booking booking, List<RoomRequest> roomRequests) {
        List<BookingDetail> bookingDetails = new ArrayList<>();
        
        for (RoomRequest roomRequest : roomRequests) {
            // Get the room from the database
            Room room = roomRepository.findById(roomRequest.getId())
                    .orElseThrow(() -> new RuntimeException("Room not found with id " + roomRequest.getId()));
            
            // Create booking detail
            BookingDetail bookingDetail = new BookingDetail();
            bookingDetail.setBooking(booking);
            bookingDetail.setRoom(room);
            bookingDetail.setPrice(roomRequest.getPrice());
            
            // Add to list (don't save yet)
            bookingDetails.add(bookingDetail);
        }
        
        return bookingDetails;
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

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email " + email));
        bookingResponse.setUser(userConverter.toResponse(user));
        return bookingResponse;
    }
}
