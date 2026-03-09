# Design Pattern Demo: Factory Method vs Abstract Factory

## 📋 Tổng quan

Demo này minh họa sự khác biệt giữa **Factory Method Pattern** và **Abstract Factory Pattern** trong hệ thống đặt phòng khách sạn.

### Bài toán

Khách sạn có nhiều loại phòng khác nhau (Standard, Deluxe, Suite). Mỗi loại phòng có:

- Cách tính giá riêng
- Tiện nghi riêng
- Chính sách hủy phòng riêng

---

## 🏗️ Cấu trúc thư mục

```
src/main/java/hcmute/edu/vn/demo/
├── factory/                          # BƯỚC 1: Factory Method Pattern
│   ├── BaseRoom.java                 # Lớp cơ sở cho tất cả phòng
│   ├── StandardRoom.java             # Phòng Standard
│   ├── DeluxeRoom.java               # Phòng Deluxe
│   ├── SuiteRoom.java                # Phòng Suite
│   └── RoomFactory.java              # Factory tạo phòng
│
├── abstractfactory/                  # BƯỚC 2: Abstract Factory Pattern
│   ├── PricingPolicy.java            # Interface chính sách giá
│   ├── StandardPricingPolicy.java    # Chính sách giá Standard
│   ├── DeluxePricingPolicy.java      # Chính sách giá Deluxe
│   ├── SuitePricingPolicy.java       # Chính sách giá Suite
│   │
│   ├── CancellationPolicy.java       # Interface chính sách hủy
│   ├── StandardCancellationPolicy.java   # Chính sách hủy Standard
│   ├── DeluxeCancellationPolicy.java     # Chính sách hủy Deluxe
│   ├── SuiteCancellationPolicy.java      # Chính sách hủy Suite
│   │
│   ├── RoomBundle.java               # Gói sản phẩm hoàn chỉnh
│   ├── RoomEcosystemFactory.java     # Interface Abstract Factory
│   ├── StandardRoomEcosystemFactory.java  # Factory Standard
│   ├── DeluxeRoomEcosystemFactory.java    # Factory Deluxe
│   └── SuiteRoomEcosystemFactory.java     # Factory Suite
│
└── DesignPatternDemo.java            # File demo chính
```

---

## 🔧 BƯỚC 1: Factory Method Pattern

### Mục đích

Tạo ra các loại **PHÒNG** khác nhau

### Cấu trúc

```
BaseRoom (abstract)
├── StandardRoom
├── DeluxeRoom
└── SuiteRoom

RoomFactory (static factory method)
```

### Ưu điểm

✅ Đơn giản, dễ hiểu, dễ triển khai  
✅ Tập trung vào việc tạo ĐỐI TƯỢNG PHÒNG  
✅ Phù hợp khi chỉ cần tạo một loại đối tượng

### Hạn chế

❌ Chỉ tạo ra PHÒNG, không có chính sách giá và hủy phòng đi kèm  
❌ Logic tính giá phải viết trong từng class phòng  
❌ Nếu cần thêm các đối tượng liên quan, phải tạo thêm factory

### Code ví dụ

```java
// Tạo phòng Standard
BaseRoom room = RoomFactory.createRoom("STANDARD");
room.displayInfo();
BigDecimal price = room.calculatePrice(3); // Tính giá 3 đêm
```

---

## 🏭 BƯỚC 2: Abstract Factory Pattern

### Mục đích

Tạo ra **HỆ SINH THÁI HOÀN CHỈNH** của phòng

### Cấu trúc

```
RoomEcosystemFactory (interface)
├── StandardRoomEcosystemFactory
├── DeluxeRoomEcosystemFactory
└── SuiteRoomEcosystemFactory

Mỗi Factory tạo ra:
├── Room (thông tin phòng)
├── PricingPolicy (chính sách giá)
└── CancellationPolicy (chính sách hủy)

=> RoomBundle (gói sản phẩm hoàn chỉnh)
```

### Ưu điểm

✅ Tạo ra **BỘ SẢN PHẨM HOÀN CHỈNH** (Room + Policies)  
✅ Đảm bảo các sản phẩm trong cùng một "dòng" **TƯƠNG THÍCH** với nhau  
✅ Dễ mở rộng: Thêm dòng sản phẩm mới chỉ cần thêm 1 factory  
✅ Tách biệt logic: Mỗi policy có class riêng, dễ maintain

### Hạn chế

❌ Phức tạp hơn Factory Method  
❌ Cần nhiều class và interface hơn

### Code ví dụ

```java
// Tạo hệ sinh thái Standard
RoomEcosystemFactory factory = new StandardRoomEcosystemFactory();
RoomBundle bundle = factory.createRoomBundle();

// Bundle chứa đầy đủ: Room + PricingPolicy + CancellationPolicy
bundle.displayFullInfo();

// Tính giá với policy riêng
BigDecimal totalPrice = bundle.calculateTotalPrice(3);

// Kiểm tra có thể hủy không
boolean canCancel = bundle.canCancelBooking(5); // hủy trước 5 ngày
double fee = bundle.calculateCancellationFee(5); // phí hủy
```

---

## 🚀 Cách chạy Demo

### Biên dịch

```bash
cd "e:\Learning Documents\Nam 3 HK2 2024-2045\Object-Oriented Software - Ms.Tho\HotelBooking"
mvn clean compile
```

### Chạy Demo

```bash
java -cp target/classes hcmute.edu.vn.demo.DesignPatternDemo
```

---

## 📊 So sánh 2 Pattern

| Tiêu chí          | Factory Method   | Abstract Factory       |
| ----------------- | ---------------- | ---------------------- |
| **Độ phức tạp**   | ⭐⭐ Đơn giản    | ⭐⭐⭐⭐ Phức tạp      |
| **Tạo đối tượng** | 1 loại đối tượng | Bộ sản phẩm liên quan  |
| **Tính đồng bộ**  | ❌ Không đảm bảo | ✅ Đảm bảo tương thích |
| **Mở rộng**       | Khó              | Dễ dàng                |
| **Số class cần**  | Ít               | Nhiều                  |

---

## 🎯 Kết luận

### Sử dụng Factory Method khi:

- Chỉ cần tạo **1 loại đối tượng đơn giản**
- Ưu tiên **sự đơn giản**
- Các đối tượng **độc lập**, không cần đồng bộ

### Sử dụng Abstract Factory khi:

- Cần tạo **BỘ SẢN PHẨM** liên quan
- Cần đảm bảo các sản phẩm **TƯƠNG THÍCH** với nhau
- Hệ thống có nhiều "**dòng sản phẩm**" khác nhau

### Trong hệ thống đặt phòng khách sạn:

**🏆 ABSTRACT FACTORY là lựa chọn TỐT HƠN** vì:

- ✅ Mỗi loại phòng cần có **chính sách giá** và **chính sách hủy** riêng
- ✅ Đảm bảo **tính nhất quán** giữa phòng và chính sách của nó
- ✅ Dễ **mở rộng** thêm loại phòng mới trong tương lai

---

## 📝 Chi tiết chính sách

### Chính sách giá

| Loại phòng   | Giá cơ bản    | Chính sách giảm giá                          |
| ------------ | ------------- | -------------------------------------------- |
| **Standard** | 500,000 VND   | Không giảm giá                               |
| **Deluxe**   | 1,000,000 VND | -5% từ 3 đêm, -10% từ 7 đêm                  |
| **Suite**    | 2,500,000 VND | -10% từ 3 đêm, -15% từ 7 đêm, -20% từ 14 đêm |

### Chính sách hủy phòng

| Loại phòng   | Hủy tối thiểu | Chính sách hoàn tiền                                                   |
| ------------ | ------------- | ---------------------------------------------------------------------- |
| **Standard** | Trước 2 ngày  | 100% trước 7 ngày, 70% từ 2-6 ngày, 0% trong 2 ngày                    |
| **Deluxe**   | Trước 3 ngày  | 100% trước 10 ngày, 80% từ 5-9 ngày, 50% từ 3-4 ngày, 0% trong 3 ngày  |
| **Suite**    | Trước 5 ngày  | 100% trước 14 ngày, 90% từ 7-13 ngày, 70% từ 5-6 ngày, 0% trong 5 ngày |

---

## 👨‍💻 Tác giả

Demo được tạo để minh họa ứng dụng Design Pattern trong Java Spring Boot Project.

**Mục đích học tập:**

- Hiểu rõ sự khác biệt giữa Factory Method và Abstract Factory
- Biết khi nào nên sử dụng pattern nào
- Áp dụng vào dự án thực tế

---

## 📚 Tài liệu tham khảo

- **Gang of Four Design Patterns**
- **Head First Design Patterns**
- **Refactoring Guru - Design Patterns**
