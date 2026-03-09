# 🚀 Quick Start Guide - Design Pattern Demo

## ⚡ Chạy nhanh trong 3 bước

### Bước 1: Mở Terminal

```bash
cd "e:\Learning Documents\Nam 3 HK2 2024-2045\Object-Oriented Software - Ms.Tho\HotelBooking"
```

### Bước 2: Compile

```bash
mvn clean compile
```

### Bước 3: Run

```bash
java -cp target/classes hcmute.edu.vn.demo.DesignPatternDemo
```

---

## 📂 Cấu trúc Files đã tạo

```
HotelBooking/
│
├── src/main/java/hcmute/edu/vn/demo/
│   ├── DesignPatternDemo.java ⭐ (FILE DEMO CHÍNH)
│   │
│   ├── factory/ (BƯỚC 1: Factory Method Pattern)
│   │   ├── BaseRoom.java
│   │   ├── StandardRoom.java
│   │   ├── DeluxeRoom.java
│   │   ├── SuiteRoom.java
│   │   └── RoomFactory.java
│   │
│   └── abstractfactory/ (BƯỚC 2: Abstract Factory Pattern)
│       ├── PricingPolicy.java
│       ├── StandardPricingPolicy.java
│       ├── DeluxePricingPolicy.java
│       ├── SuitePricingPolicy.java
│       ├── CancellationPolicy.java
│       ├── StandardCancellationPolicy.java
│       ├── DeluxeCancellationPolicy.java
│       ├── SuiteCancellationPolicy.java
│       ├── RoomBundle.java
│       ├── RoomEcosystemFactory.java
│       ├── StandardRoomEcosystemFactory.java
│       ├── DeluxeRoomEcosystemFactory.java
│       └── SuiteRoomEcosystemFactory.java
│
└── Documentation/
    ├── DESIGN_PATTERN_DEMO_README.md       (Hướng dẫn chi tiết)
    ├── DESIGN_PATTERN_VISUAL_GUIDE.md      (Sơ đồ trực quan)
    ├── DESIGN_PATTERN_SUMMARY.md           (Tổng kết)
    └── QUICK_START.md                      (File này)
```

---

## 🎯 3 Loại phòng

| Loại         | Giá/đêm | Sức chứa | Chính sách giá                      | Chính sách hủy |
| ------------ | ------- | -------- | ----------------------------------- | -------------- |
| **Standard** | 500k    | 2 người  | Không giảm                          | Hủy ≥2 ngày    |
| **Deluxe**   | 1M      | 3 người  | -5% (≥3đ), -10% (≥7đ)               | Hủy ≥3 ngày    |
| **Suite**    | 2.5M    | 4 người  | -10% (≥3đ), -15% (≥7đ), -20% (≥14đ) | Hủy ≥5 ngày    |

---

## 💡 So sánh nhanh

### Factory Method (Đơn giản)

```java
// Chỉ tạo PHÒNG
BaseRoom room = RoomFactory.createRoom("STANDARD");
room.displayInfo();
```

- ✅ Đơn giản, 5 classes
- ❌ Không có pricing/cancellation policy

### Abstract Factory (Hoàn chỉnh)

```java
// Tạo HỆ SINH THÁI hoàn chỉnh
RoomEcosystemFactory factory = new StandardRoomEcosystemFactory();
RoomBundle bundle = factory.createRoomBundle();
bundle.displayFullInfo();

// Có đầy đủ tính năng
BigDecimal price = bundle.calculateTotalPrice(3);
boolean canCancel = bundle.canCancelBooking(5);
double fee = bundle.calculateCancellationFee(5);
```

- ✅ Hoàn chỉnh, có pricing/cancellation
- ✅ Đảm bảo tính đồng bộ
- ❌ Phức tạp hơn, 13 classes

---

## 🔍 Xem kết quả

Demo sẽ hiển thị:

1. **Factory Method Demo**
   - Tạo 3 loại phòng
   - Hiển thị thông tin
   - Tính giá

2. **Abstract Factory Demo**
   - Tạo 3 hệ sinh thái hoàn chỉnh
   - Hiển thị thông tin đầy đủ
   - Test tính giá (nhiều khoảng thời gian)
   - Test chính sách hủy (nhiều tình huống)

3. **So sánh 2 Pattern**
   - Bảng so sánh chi tiết
   - Ưu/nhược điểm
   - Khi nào dùng cái nào

---

## 📖 Đọc thêm

1. **DESIGN_PATTERN_DEMO_README.md**
   → Hướng dẫn chi tiết, giải thích đầy đủ

2. **DESIGN_PATTERN_VISUAL_GUIDE.md**
   → Sơ đồ, biểu đồ, flowchart trực quan

3. **DESIGN_PATTERN_SUMMARY.md**
   → Tổng kết toàn bộ implementation

---

## ⚠️ Lưu ý

- Demo chạy trên Java 17+
- Cần Maven để compile
- Output có thể bị lỗi font nếu terminal không hỗ trợ Unicode
- Nếu gặp lỗi, check Java version: `java -version`

---

## 🎓 Kết luận

**Trong Hotel Booking System → Dùng Abstract Factory**

Lý do:

- ✅ Mỗi phòng CẦN có pricing + cancellation policy
- ✅ Các policy phải TƯƠNG THÍCH với loại phòng
- ✅ Dễ THÊM loại phòng mới (VIP, Presidential...)

---

## 📞 Troubleshooting

### Lỗi "class not found"

```bash
# Compile lại
mvn clean compile
```

### Lỗi Maven

```bash
# Check Maven
mvn -version

# Nếu chưa có, install Maven hoặc dùng mvnw
./mvnw clean compile  # Linux/Mac
.\mvnw.cmd clean compile  # Windows
```

### Output bị lỗi font

```bash
# Windows: Đổi terminal encoding
chcp 65001

# Hoặc dùng IntelliJ/Eclipse console
```

---

## ✨ Happy Coding!

Demo này minh họa:

- ✅ Factory Method Pattern
- ✅ Abstract Factory Pattern
- ✅ SOLID Principles
- ✅ Clean Code
- ✅ Real-world Application

**Chúc bạn học tốt! 🎉**
