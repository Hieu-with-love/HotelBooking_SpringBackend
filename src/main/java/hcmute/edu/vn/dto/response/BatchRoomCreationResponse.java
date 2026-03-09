package hcmute.edu.vn.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Response cho batch creation
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BatchRoomCreationResponse {
    
    private int totalCreated;
    private String factoryPattern;
    private List<RoomFactoryResponse> rooms;
    private String message;
}
