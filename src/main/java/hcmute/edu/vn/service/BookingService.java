package hcmute.edu.vn.service;

import hcmute.edu.vn.dto.request.BookingRequest;
import hcmute.edu.vn.dto.response.BookingResponse;
import hcmute.edu.vn.model.Booking;

import java.util.List;

public interface BookingService {
    Booking createBooking(BookingRequest request);
    BookingResponse convertToBookingResponse(Booking booking);
}
