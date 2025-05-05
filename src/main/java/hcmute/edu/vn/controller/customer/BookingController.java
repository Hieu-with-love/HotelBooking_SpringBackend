package hcmute.edu.vn.controller.customer;

import hcmute.edu.vn.dto.request.BookingRequest;
import hcmute.edu.vn.dto.response.BookingResponse;
import hcmute.edu.vn.model.Booking;
import hcmute.edu.vn.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("https://zotels-booking.id.vn")
//@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/customer/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    // Add your booking-related endpoints here

    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@RequestBody BookingRequest req){
        try{
            Booking booking = bookingService.createBooking(req);
            BookingResponse bookingResponse = bookingService.convertToBookingResponse(booking);
            return ResponseEntity.ok(bookingResponse);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Failed to create booking: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getBookings() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable("id") Long id) {
        return null;
    }
}
