package hcmute.edu.vn.demo;

import hcmute.edu.vn.demo.abstractfactory.*;
import hcmute.edu.vn.demo.factory.*;
import hcmute.edu.vn.model.Room;

import java.math.BigDecimal;

/**
 * ╔═══════════════════════════════════════════════════════════════════════════╗
 * ║                    DESIGN PATTERN DEMONSTRATION                            ║
 * ║              Factory Method vs Abstract Factory Pattern                    ║
 * ╚═══════════════════════════════════════════════════════════════════════════╝
 * 
 * Bài toán: 
 * Khách sạn có nhiều loại phòng khác nhau (Standard, Deluxe, Suite). 
 * Mỗi loại phòng có cách tính giá, tiện nghi, và chính sách hủy phòng riêng.
 * 
 * Giải pháp 2 bước:
 * 
 * BƯỚC 1: FACTORY METHOD PATTERN
 * ─────────────────────────────────────────────────────────────────────────────
 * Mục đích: Tạo ra các loại PHÒNG khác nhau
 * Cấu trúc:
 *   - Room (product class - JPA entity)
 *   - StandardRoom, DeluxeRoom, SuiteRoom (builder classes tạo Room entity)
 *   - RoomFactory (factory class với static method)
 * 
 * Ưu điểm:
 *   ✓ Đơn giản, dễ hiểu, dễ triển khai
 *   ✓ Tập trung vào việc tạo ĐỐI TƯỢNG PHÒNG
 *   ✓ Phù hợp khi chỉ cần tạo một loại đối tượng
 * 
 * Hạn chế:
 *   ✗ Chỉ tạo ra PHÒNG, không có chính sách giá và hủy phòng đi kèm
 *   ✗ Nếu cần thêm các đối tượng liên quan, phải tạo thêm factory
 * 
 * 
 * BƯỚC 2: ABSTRACT FACTORY PATTERN
 * ─────────────────────────────────────────────────────────────────────────────
 * Mục đích: Tạo ra CẢ HỆ SINH THÁI của phòng
 * Cấu trúc:
 *   - RoomEcosystemFactory (interface)
 *   - StandardRoomEcosystemFactory, DeluxeRoomEcosystemFactory, 
 *     SuiteRoomEcosystemFactory (concrete factories)
 *   - Mỗi factory tạo ra: Room + PricingPolicy + CancellationPolicy
 * 
 * Ưu điểm:
 *   ✓ Tạo ra BỘ SẢN PHẨM HOÀN CHỈNH (Room + Policies)
 *   ✓ Đảm bảo các sản phẩm trong cùng một "dòng" tương thích với nhau
 *   ✓ Dễ mở rộng: Thêm dòng sản phẩm mới chỉ cần thêm 1 factory
 * 
 * Hạn chế:
 *   ✗ Phức tạp hơn Factory Method
 *   ✗ Cần nhiều class và interface hơn
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 */
public class DesignPatternDemo {
    
    public static void main(String[] args) {
        System.out.println("\n");
        System.out.println("╔═══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                     🏨 HOTEL BOOKING SYSTEM 🏨                        ║");
        System.out.println("║              Design Pattern Demo: Factory Method vs Abstract Factory ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════╝");
        
        // ────────────────────────────────────────────────────────────────────────
        // BƯỚC 1: FACTORY METHOD PATTERN DEMO
        // ────────────────────────────────────────────────────────────────────────
        demonstrateFactoryMethod();
        
        // ────────────────────────────────────────────────────────────────────────
        // BƯỚC 2: ABSTRACT FACTORY PATTERN DEMO
        // ────────────────────────────────────────────────────────────────────────
        demonstrateAbstractFactory();
        
        // ────────────────────────────────────────────────────────────────────────
        // SO SÁNH KẾT QUẢ
        // ────────────────────────────────────────────────────────────────────────
        compareResults();
    }
    
    /**
     * DEMO BƯỚC 1: FACTORY METHOD PATTERN
     * Chỉ tạo ra CÁC LOẠI PHÒNG
     */
    private static void demonstrateFactoryMethod() {
        System.out.println("\n");
        System.out.println("═══════════════════════════════════════════════════════════════════════");
        System.out.println("🔧 BƯỚC 1: FACTORY METHOD PATTERN - Tạo các loại PHÒNG");
        System.out.println("═══════════════════════════════════════════════════════════════════════");
        System.out.println();
        
        // Tạo phòng Standard
        System.out.println(">>> Khách hàng yêu cầu: Tạo phòng STANDARD");
        StandardRoom standardRoomBuilder = new StandardRoom();
        standardRoomBuilder.displayInfo();
        Room standardRoom = standardRoomBuilder.getRoom();
        System.out.println();
        
        // Test tính giá
        int nights = 3;
        BigDecimal price = standardRoomBuilder.calculatePrice(nights);
        System.out.println("💵 Tổng giá cho " + nights + " đêm: " + price + " VND");
        System.out.println();
        
        // Tạo phòng Deluxe
        System.out.println("\n>>> Khách hàng yêu cầu: Tạo phòng DELUXE");
        DeluxeRoom deluxeRoomBuilder = new DeluxeRoom();
        deluxeRoomBuilder.displayInfo();
        Room deluxeRoom = deluxeRoomBuilder.getRoom();
        System.out.println();
        
        nights = 5;
        price = deluxeRoomBuilder.calculatePrice(nights);
        System.out.println("💵 Tổng giá cho " + nights + " đêm: " + price + " VND");
        System.out.println();
        
        // Tạo phòng Suite
        System.out.println("\n>>> Khách hàng yêu cầu: Tạo phòng SUITE");
        SuiteRoom suiteRoomBuilder = new SuiteRoom();
        suiteRoomBuilder.displayInfo();
        Room suiteRoom = suiteRoomBuilder.getRoom();
        System.out.println();
        
        nights = 7;
        price = suiteRoomBuilder.calculatePrice(nights);
        System.out.println("💵 Tổng giá cho " + nights + " đêm: " + price + " VND");
        
        System.out.println("\n❌ HẠN CHẾ CỦA FACTORY METHOD:");
        System.out.println("   - Chỉ tạo được ĐỐI TƯỢNG PHÒNG");
        System.out.println("   - KHÔNG có chính sách giá chi tiết");
        System.out.println("   - KHÔNG có chính sách hủy phòng");
        System.out.println("   - Phải tự implement logic tính giá trong từng loại phòng");
    }
    
    /**
     * DEMO BƯỚC 2: ABSTRACT FACTORY PATTERN
     * Tạo ra HỆ SINH THÁI HOÀN CHỈNH (Room + PricingPolicy + CancellationPolicy)
     */
    private static void demonstrateAbstractFactory() {
        System.out.println("\n\n");
        System.out.println("═══════════════════════════════════════════════════════════════════════");
        System.out.println("🏭 BƯỚC 2: ABSTRACT FACTORY PATTERN - Tạo HỆ SINH THÁI hoàn chỉnh");
        System.out.println("═══════════════════════════════════════════════════════════════════════");
        System.out.println();
        
        // ────────────────────────────────────────────────────────────────
        // Tạo hệ sinh thái Standard
        // ────────────────────────────────────────────────────────────────
        System.out.println(">>> Khách hàng yêu cầu: Tạo HỆ SINH THÁI phòng STANDARD");
        System.out.println("🏭 Abstract Factory đang tạo: Room + PricingPolicy + CancellationPolicy...");
        
        RoomEcosystemFactory standardFactory = new StandardRoomEcosystemFactory();
        RoomBundle standardBundle = standardFactory.createRoomBundle();
        standardBundle.displayFullInfo();
        
        // Test các tình huống
        testRoomBundle(standardBundle, "STANDARD");
        
        // ────────────────────────────────────────────────────────────────
        // Tạo hệ sinh thái Deluxe
        // ────────────────────────────────────────────────────────────────
        System.out.println("\n>>> Khách hàng yêu cầu: Tạo HỆ SINH THÁI phòng DELUXE");
        System.out.println("🏭 Abstract Factory đang tạo: Room + PricingPolicy + CancellationPolicy...");
        
        RoomEcosystemFactory deluxeFactory = new DeluxeRoomEcosystemFactory();
        RoomBundle deluxeBundle = deluxeFactory.createRoomBundle();
        deluxeBundle.displayFullInfo();
        
        testRoomBundle(deluxeBundle, "DELUXE");
        
        // ────────────────────────────────────────────────────────────────
        // Tạo hệ sinh thái Suite
        // ────────────────────────────────────────────────────────────────
        System.out.println("\n>>> Khách hàng yêu cầu: Tạo HỆ SINH THÁI phòng SUITE");
        System.out.println("🏭 Abstract Factory đang tạo: Room + PricingPolicy + CancellationPolicy...");
        
        RoomEcosystemFactory suiteFactory = new SuiteRoomEcosystemFactory();
        RoomBundle suiteBundle = suiteFactory.createRoomBundle();
        suiteBundle.displayFullInfo();
        
        testRoomBundle(suiteBundle, "SUITE");
        
        System.out.println("\n✅ ƯU ĐIỂM CỦA ABSTRACT FACTORY:");
        System.out.println("   ✓ Tạo ra BỘ SẢN PHẨM HOÀN CHỈNH");
        System.out.println("   ✓ Mỗi loại phòng có CHÍNH SÁCH GIÁ riêng");
        System.out.println("   ✓ Mỗi loại phòng có CHÍNH SÁCH HỦY riêng");
        System.out.println("   ✓ Các chính sách ĐỒNG BỘ và TƯƠNG THÍCH với nhau");
        System.out.println("   ✓ Dễ mở rộng: Thêm loại phòng mới chỉ cần 1 factory");
    }
    
    /**
     * Test các tính năng của RoomBundle
     */
    private static void testRoomBundle(RoomBundle bundle, String roomType) {
        System.out.println("\n┌─────────────────────────────────────────────────────────┐");
        System.out.println("│  🧪 TEST TÍNH NĂNG - " + roomType);
        System.out.println("└─────────────────────────────────────────────────────────┘");
        
        // Test tính giá cho các khoảng thời gian khác nhau
        System.out.println("\n📊 Test tính giá:");
        int[] nightOptions = {1, 3, 7, 14};
        for (int nights : nightOptions) {
            BigDecimal totalPrice = bundle.calculateTotalPrice(nights);
            System.out.println("   - " + nights + " đêm: " + totalPrice + " VND");
        }
        
        // Test chính sách hủy phòng
        System.out.println("\n🚫 Test chính sách hủy:");
        int[] cancelDays = {1, 5, 10, 15};
        for (int days : cancelDays) {
            boolean canCancel = bundle.canCancelBooking(days);
            double fee = bundle.calculateCancellationFee(days);
            String feePercent = String.format("%.0f%%", fee * 100);
            String status = canCancel ? "✓ Có thể hủy" : "✗ Không thể hủy";
            
            System.out.println("   - Hủy trước " + days + " ngày: " + status + 
                             ", Phí hủy: " + feePercent);
        }
    }
    
    /**
     * So sánh kết quả giữa 2 pattern
     */
    private static void compareResults() {
        System.out.println("\n\n");
        System.out.println("╔═══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    📊 SO SÁNH 2 DESIGN PATTERN                        ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════╝");
        
        System.out.println("\n┌───────────────────────────────────────────────────────────────────────┐");
        System.out.println("│  FACTORY METHOD PATTERN                                               │");
        System.out.println("├───────────────────────────────────────────────────────────────────────┤");
        System.out.println("│  ✓ Đơn giản, dễ hiểu                                                  │");
        System.out.println("│  ✓ Phù hợp khi chỉ cần tạo 1 loại đối tượng                           │");
        System.out.println("│  ✗ Thiếu tính linh hoạt                                               │");
        System.out.println("│  ✗ Không tạo được bộ sản phẩm liên quan                               │");
        System.out.println("│                                                                       │");
        System.out.println("│  KẾT QUẢ: Chỉ có PHÒNG                                                │");
        System.out.println("│           ❌ Không có chính sách giá                                   │");
        System.out.println("│           ❌ Không có chính sách hủy                                   │");
        System.out.println("└───────────────────────────────────────────────────────────────────────┘");
        
        System.out.println("\n┌───────────────────────────────────────────────────────────────────────┐");
        System.out.println("│  ABSTRACT FACTORY PATTERN                                             │");
        System.out.println("├───────────────────────────────────────────────────────────────────────┤");
        System.out.println("│  ✓ Tạo bộ sản phẩm hoàn chỉnh                                         │");
        System.out.println("│  ✓ Các sản phẩm đồng bộ và tương thích                                │");
        System.out.println("│  ✓ Dễ mở rộng thêm dòng sản phẩm mới                                  │");
        System.out.println("│  ✗ Phức tạp hơn                                                        │");
        System.out.println("│  ✗ Cần nhiều class hơn                                                │");
        System.out.println("│                                                                       │");
        System.out.println("│  KẾT QUẢ: HỆ SINH THÁI HOÀN CHỈNH                                     │");
        System.out.println("│           ✅ PHÒNG + CHÍNH SÁCH GIÁ + CHÍNH SÁCH HỦY                   │");
        System.out.println("│           ✅ Tất cả đều tương thích với nhau                           │");
        System.out.println("└───────────────────────────────────────────────────────────────────────┘");
        
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                           🎯 KẾT LUẬN                                 ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════════╣");
        System.out.println("║  📌 Sử dụng FACTORY METHOD khi:                                       ║");
        System.out.println("║     - Chỉ cần tạo 1 loại đối tượng đơn giản                           ║");
        System.out.println("║     - Ưu tiên sự đơn giản                                             ║");
        System.out.println("║                                                                       ║");
        System.out.println("║  📌 Sử dụng ABSTRACT FACTORY khi:                                     ║");
        System.out.println("║     - Cần tạo BỘ SẢN PHẨM liên quan                                   ║");
        System.out.println("║     - Cần đảm bảo các sản phẩm TƯƠNG THÍCH với nhau                   ║");
        System.out.println("║     - Hệ thống có nhiều \"dòng sản phẩm\" khác nhau                    ║");
        System.out.println("║                                                                       ║");
        System.out.println("║  🏆 Trong hệ thống đặt phòng khách sạn:                               ║");
        System.out.println("║     ABSTRACT FACTORY là lựa chọn TỐT HƠN vì:                          ║");
        System.out.println("║     ✓ Mỗi loại phòng cần có chính sách giá và hủy riêng              ║");
        System.out.println("║     ✓ Đảm bảo tính nhất quán giữa phòng và chính sách của nó         ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════╝");
        
        System.out.println("\n");
    }
}
