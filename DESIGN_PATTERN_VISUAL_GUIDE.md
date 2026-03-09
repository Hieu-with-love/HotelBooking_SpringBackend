# Design Pattern Visual Comparison

## 📐 FACTORY METHOD PATTERN - Cấu trúc

```
┌─────────────────────────────────────────────────────────────┐
│                    CLIENT CODE                              │
│  BaseRoom room = RoomFactory.createRoom("STANDARD");        │
└─────────────────────────────┬───────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                   RoomFactory                               │
│  + createRoom(type: String): BaseRoom                       │
│    {                                                        │
│      if (type == "STANDARD") return new StandardRoom();     │
│      if (type == "DELUXE") return new DeluxeRoom();         │
│      if (type == "SUITE") return new SuiteRoom();           │
│    }                                                        │
└─────────────────────────────┬───────────────────────────────┘
                              │
                              ▼
                     ┌────────────────┐
                     │   BaseRoom     │
                     │   (abstract)   │
                     └────────┬───────┘
                              │
            ┌─────────────────┼─────────────────┐
            │                 │                 │
            ▼                 ▼                 ▼
    ┌──────────────┐  ┌──────────────┐  ┌──────────────┐
    │ StandardRoom │  │  DeluxeRoom  │  │  SuiteRoom   │
    ├──────────────┤  ├──────────────┤  ├──────────────┤
    │ - 500k VND   │  │ - 1M VND     │  │ - 2.5M VND   │
    │ - 2 người    │  │ - 3 người    │  │ - 4 người    │
    │ - Basic      │  │ - Premium    │  │ - Luxury     │
    └──────────────┘  └──────────────┘  └──────────────┘
```

### ⚠️ Vấn đề:

```
❌ Chỉ tạo được PHÒNG
❌ Không có PricingPolicy
❌ Không có CancellationPolicy
❌ Logic tính giá nằm rải rác trong từng class
```

---

## 🏭 ABSTRACT FACTORY PATTERN - Cấu trúc

```
┌─────────────────────────────────────────────────────────────────────┐
│                          CLIENT CODE                                │
│  RoomEcosystemFactory factory = new StandardRoomEcosystemFactory(); │
│  RoomBundle bundle = factory.createRoomBundle();                    │
│  // bundle chứa: Room + PricingPolicy + CancellationPolicy          │
└──────────────────────────────┬──────────────────────────────────────┘
                               │
                               ▼
┌─────────────────────────────────────────────────────────────────────┐
│              «interface» RoomEcosystemFactory                       │
│  + createRoomBundle(): RoomBundle                                   │
│  + createPricingPolicy(): PricingPolicy                             │
│  + createCancellationPolicy(): CancellationPolicy                   │
└────┬──────────────────────────┬──────────────────────────┬──────────┘
     │                          │                          │
     ▼                          ▼                          ▼
┌──────────────────┐  ┌──────────────────┐  ┌──────────────────┐
│StandardEcosystem │  │ DeluxeEcosystem  │  │  SuiteEcosystem  │
│    Factory       │  │     Factory       │  │     Factory      │
└────┬─────────────┘  └────┬─────────────┘  └────┬─────────────┘
     │                     │                      │
     └─────────┬───────────┴──────────┬───────────┘
               │                      │
               ▼                      ▼
   ┌───────────────────┐    ┌────────────────────┐
   │   CREATES         │    │   CREATES          │
   └───────────────────┘    └────────────────────┘
               │                      │
     ┌─────────┼──────────┐  ┌────────┼────────────┐
     ▼         ▼          ▼  ▼        ▼            ▼
```

### HỆ SINH THÁI STANDARD:

```
╔═══════════════════════════════════════════════════════╗
║         StandardRoomEcosystemFactory                   ║
╠═══════════════════════════════════════════════════════╣
║                                                        ║
║  creates ┌──────────────────────────────┐             ║
║       ┌─►│     ROOM BUNDLE              │◄─┐          ║
║       │  │  (Room + 2 Policies)         │  │          ║
║       │  └──────────────────────────────┘  │          ║
║       │                                     │          ║
║  ┌────┴────────┐   ┌──────────────┐   ┌───┴────────┐ ║
║  │ Standard    │   │  Standard    │   │  Standard  │ ║
║  │   Room      │   │  Pricing     │   │Cancellation│ ║
║  ├─────────────┤   ├──────────────┤   ├────────────┤ ║
║  │- 500k VND   │   │- Giá cố định │   │- Hủy ≥2d   │ ║
║  │- 2 người    │   │- 0% giảm giá │   │- 100% <7d  │ ║
║  │- 4 tiện nghi│   │              │   │- 30% 2-6d  │ ║
║  └─────────────┘   └──────────────┘   └────────────┘ ║
║                                                        ║
╚═══════════════════════════════════════════════════════╝
```

### HỆ SINH THÁI DELUXE:

```
╔═══════════════════════════════════════════════════════╗
║          DeluxeRoomEcosystemFactory                    ║
╠═══════════════════════════════════════════════════════╣
║                                                        ║
║  creates ┌──────────────────────────────┐             ║
║       ┌─►│     ROOM BUNDLE              │◄─┐          ║
║       │  │  (Room + 2 Policies)         │  │          ║
║       │  └──────────────────────────────┘  │          ║
║       │                                     │          ║
║  ┌────┴────────┐   ┌──────────────┐   ┌───┴────────┐ ║
║  │   Deluxe    │   │   Deluxe     │   │   Deluxe   │ ║
║  │    Room     │   │   Pricing    │   │Cancellation│ ║
║  ├─────────────┤   ├──────────────┤   ├────────────┤ ║
║  │- 1M VND     │   │- 5% ≥3 đêm   │   │- Hủy ≥3d   │ ║
║  │- 3 người    │   │- 10% ≥7 đêm  │   │- 100% <10d │ ║
║  │- 7 tiện nghi│   │              │   │- 50% 3-4d  │ ║
║  └─────────────┘   └──────────────┘   └────────────┘ ║
║                                                        ║
╚═══════════════════════════════════════════════════════╝
```

### HỆ SINH THÁI SUITE:

```
╔═══════════════════════════════════════════════════════╗
║           SuiteRoomEcosystemFactory                    ║
╠═══════════════════════════════════════════════════════╣
║                                                        ║
║  creates ┌──────────────────────────────┐             ║
║       ┌─►│     ROOM BUNDLE              │◄─┐          ║
║       │  │  (Room + 2 Policies)         │  │          ║
║       │  └──────────────────────────────┘  │          ║
║       │                                     │          ║
║  ┌────┴────────┐   ┌──────────────┐   ┌───┴────────┐ ║
║  │    Suite    │   │    Suite     │   │    Suite   │ ║
║  │    Room     │   │   Pricing    │   │Cancellation│ ║
║  ├─────────────┤   ├──────────────┤   ├────────────┤ ║
║  │- 2.5M VND   │   │- 10% ≥3 đêm  │   │- Hủy ≥5d   │ ║
║  │- 4 người    │   │- 15% ≥7 đêm  │   │- 100% <14d │ ║
║  │- 11 tiện nghi│  │- 20% ≥14 đêm │   │- 30% 5-6d  │ ║
║  └─────────────┘   └──────────────┘   └────────────┘ ║
║                                                        ║
╚═══════════════════════════════════════════════════════╝
```

---

## 🔄 Quy trình tạo đối tượng

### FACTORY METHOD:

```
Client Request
     │
     ▼
RoomFactory.createRoom("STANDARD")
     │
     ▼
creates StandardRoom
     │
     ▼
return BaseRoom object
     │
     ▼
✅ Có: Room
❌ Không có: PricingPolicy, CancellationPolicy
```

### ABSTRACT FACTORY:

```
Client Request
     │
     ▼
StandardRoomEcosystemFactory.createRoomBundle()
     │
     ├──► createRoom()
     ├──► createPricingPolicy()
     └──► createCancellationPolicy()
     │
     ▼
creates RoomBundle with all 3 components
     │
     ▼
return RoomBundle
     │
     ▼
✅ Có: Room + PricingPolicy + CancellationPolicy
✅ Tất cả tương thích với nhau (cùng "dòng" Standard)
```

---

## 📊 Bảng so sánh chi tiết

### Khả năng tạo đối tượng

| Thành phần             | Factory Method   | Abstract Factory |
| ---------------------- | ---------------- | ---------------- |
| **Room**               | ✅ Có            | ✅ Có            |
| **PricingPolicy**      | ❌ Không         | ✅ Có            |
| **CancellationPolicy** | ❌ Không         | ✅ Có            |
| **Tính đồng bộ**       | ❌ Không đảm bảo | ✅ Đảm bảo       |

### Số lượng class cần thiết

**Factory Method:**

```
BaseRoom.java              (1 abstract class)
StandardRoom.java          (3 concrete classes)
DeluxeRoom.java
SuiteRoom.java
RoomFactory.java           (1 factory class)
─────────────────────────
Tổng: 5 class
```

**Abstract Factory:**

```
PricingPolicy.java                 (2 interfaces)
CancellationPolicy.java
StandardPricingPolicy.java         (6 policy classes)
DeluxePricingPolicy.java
SuitePricingPolicy.java
StandardCancellationPolicy.java
DeluxeCancellationPolicy.java
SuiteCancellationPolicy.java
RoomBundle.java                    (1 bundle class)
RoomEcosystemFactory.java          (1 interface)
StandardRoomEcosystemFactory.java  (3 factory classes)
DeluxeRoomEcosystemFactory.java
SuiteRoomEcosystemFactory.java
─────────────────────────
Tổng: 13 class/interfaces
```

---

## ⚖️ Ưu nhược điểm trực quan

### FACTORY METHOD

```
✅ ƯU ĐIỂM:
┌────────────────────────────────────────┐
│  • Đơn giản, dễ hiểu                   │
│  • Ít class hơn                        │
│  • Phù hợp với bài toán đơn giản       │
│  • Implement nhanh                     │
└────────────────────────────────────────┘

❌ NHƯỢC ĐIỂM:
┌────────────────────────────────────────┐
│  • Chỉ tạo 1 loại đối tượng            │
│  • Không đảm bảo tính đồng bộ          │
│  • Logic rải rác trong các class       │
│  • Khó mở rộng khi có thêm yêu cầu     │
└────────────────────────────────────────┘
```

### ABSTRACT FACTORY

```
✅ ƯU ĐIỂM:
┌────────────────────────────────────────┐
│  • Tạo bộ sản phẩm hoàn chỉnh          │
│  • Đảm bảo tính tương thích            │
│  • Dễ mở rộng (thêm dòng sản phẩm)     │
│  • Tách biệt logic rõ ràng             │
│  • Tuân thủ SOLID principles           │
└────────────────────────────────────────┘

❌ NHƯỢC ĐIỂM:
┌────────────────────────────────────────┐
│  • Phức tạp hơn                        │
│  • Nhiều class/interface               │
│  • Over-engineering cho bài toán đơn   │
│  • Học và maintain mất thời gian       │
└────────────────────────────────────────┘
```

---

## 🎯 Khi nào dùng cái nào?

```
                    START
                      │
                      ▼
        ┌──────────────────────────┐
        │  Chỉ cần tạo 1 loại      │      YES
        │  đối tượng đơn giản?     │──────────► FACTORY METHOD
        └──────────────────────────┘
                      │ NO
                      ▼
        ┌──────────────────────────┐
        │  Cần tạo BỘ sản phẩm     │      YES
        │  liên quan?              │──────────► ABSTRACT FACTORY
        └──────────────────────────┘
                      │ NO
                      ▼
        ┌──────────────────────────┐
        │  Cần đảm bảo các sản     │      YES
        │  phẩm tương thích?       │──────────► ABSTRACT FACTORY
        └──────────────────────────┘
                      │ NO
                      ▼
        ┌──────────────────────────┐
        │  Có nhiều "dòng sản      │      YES
        │  phẩm" khác nhau?        │──────────► ABSTRACT FACTORY
        └──────────────────────────┘
                      │ NO
                      ▼
              FACTORY METHOD
```

---

## 💡 Ví dụ thực tế

### Tình huống: Khách hàng đặt phòng Suite cho 7 đêm

**Factory Method:**

```java
// Chỉ tạo được phòng
BaseRoom room = RoomFactory.createRoom("SUITE");
BigDecimal price = room.calculatePrice(7);
// ❌ Không biết chính sách hủy
// ❌ Logic giảm giá nằm trong SuiteRoom class
```

**Abstract Factory:**

```java
// Tạo hệ sinh thái hoàn chỉnh
RoomEcosystemFactory factory = new SuiteRoomEcosystemFactory();
RoomBundle bundle = factory.createRoomBundle();

// ✅ Có đầy đủ thông tin
BigDecimal price = bundle.calculateTotalPrice(7);        // 14,875,000 VND (-15%)
boolean canCancel = bundle.canCancelBooking(10);         // true
double cancelFee = bundle.calculateCancellationFee(10);  // 10% phí

// ✅ Tất cả policy đồng bộ với Suite room
```

---

## 🏆 Kết luận cho Hotel Booking System

```
╔═══════════════════════════════════════════════════════╗
║         ABSTRACT FACTORY là lựa chọn TỐT HƠN         ║
╠═══════════════════════════════════════════════════════╣
║                                                       ║
║  Lý do:                                               ║
║  ✓ Mỗi loại phòng CẦN có pricing và cancellation     ║
║  ✓ Các policy phải TƯƠNG THÍCH với loại phòng        ║
║  ✓ Dễ THÊM loại phòng mới (VIP, Presidential...)     ║
║  ✓ TÁCH BIỆT logic rõ ràng, dễ test                  ║
║  ✓ Tuân thủ Open/Closed Principle                    ║
║                                                       ║
╚═══════════════════════════════════════════════════════╝
```
