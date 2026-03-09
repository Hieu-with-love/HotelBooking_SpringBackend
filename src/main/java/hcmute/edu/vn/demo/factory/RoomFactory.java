package hcmute.edu.vn.demo.factory;

import hcmute.edu.vn.model.Room;

/**
 * FACTORY METHOD PATTERN
 * 
 * Factory để tạo ra các loại phòng khác nhau
 * Factory to create different room types
 */
public class RoomFactory {
    
    /**
     * Tạo phòng dựa trên loại phòng
     * Create room based on room type
     * 
     * @param roomType Loại phòng: "STANDARD", "DELUXE", "SUITE"
     * @return Đối tượng phòng tương ứng
     */
    public static Room createRoom(String roomType) {
        if (roomType == null || roomType.isEmpty()) {
            throw new IllegalArgumentException("Loại phòng không được để trống!");
        }
        
        switch (roomType.toUpperCase()) {
            case "STANDARD":
                return new StandardRoom().getRoom();
//
            case "DELUXE":
                return new DeluxeRoom().getRoom();

            case "SUITE":
                return new SuiteRoom().getRoom();
                
            default:
                throw new IllegalArgumentException("Loại phòng không hợp lệ: " + roomType);
        }
    }
}
