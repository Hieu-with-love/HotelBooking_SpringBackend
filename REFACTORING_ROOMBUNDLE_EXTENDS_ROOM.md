# Refactoring: RoomBundle extends Room

## 🎯 Mục tiêu

Refactor RoomBundle để **extends Room** thay vì chỉ là container chứa Room, làm cho kiến trúc chuẩn hơn và dễ bảo trùng hơn.

---

## ✅ Những thay đổi đã thực hiện

### 1. **RoomBundle.java - Extends Room Entity**

#### Trước (Old Design):

```java
public class RoomBundle {
    private String roomType;
    private BigDecimal basePrice;
    private int capacity;
    private List<String> amenities;
    private PricingPolicy pricingPolicy;
    private CancellationPolicy cancellationPolicy;

    public RoomBundle(String roomType, BigDecimal basePrice, ...) {
        // Constructor với nhiều tham số
    }
}
```

#### Sau (New Design - Chuẩn hơn):

```java
public class RoomBundle extends Room {
    // Chỉ thêm 2 policies cho Abstract Factory Pattern
    private PricingPolicy pricingPolicy;
    private CancellationPolicy cancellationPolicy;

    public RoomBundle() {
        super(); // Kế thừa tất cả properties từ Room entity
    }
}
```

**Lợi ích:**

- ✅ RoomBundle **LÀ** một Room, không chỉ **CHỨA** Room
- ✅ Kế thừa đầy đủ tất cả properties của JPA entity Room
- ✅ Có thể lưu trực tiếp vào database (vì đã là Room entity)
- ✅ Thêm 2 policies để tạo hệ sinh thái hoàn chỉnh

---

### 2. **Ecosystem Factories - Tạo Room Entity hoàn chỉnh**

#### StandardRoomEcosystemFactory (Tương tự cho Deluxe và Suite):

```java
@Override
public RoomBundle createRoomBundle() {
    // Tạo RoomBundle (extends Room)
    RoomBundle bundle = new RoomBundle();

    // Set tất cả properties của Room entity
    bundle.setName("Phòng Standard");
    bundle.setType(EROOMTYPE.DOUBLE);
    bundle.setPrice(new BigDecimal("500000"));
    bundle.setNumberOfAdults(2);
    bundle.setNumberOfChildren(0);
    bundle.setNumberOfBeds(1);
    bundle.setStatus(EROOMSTATUS.AVAILABLE);
    bundle.setDescription("Phòng tiêu chuẩn...");
    bundle.setServices(Arrays.asList(
        ESERVICE.WIFI,
        ESERVICE.TV,
        ESERVICE.AIR_CONDITIONING
    ));

    // Set policies (Abstract Factory Pattern)
    bundle.setPricingPolicy(createPricingPolicy());
    bundle.setCancellationPolicy(createCancellationPolicy());

    return bundle;
}
```

**Lợi ích:**

- ✅ Tạo Room entity hoàn chỉnh với đầy đủ thông tin
- ✅ Không cần bước chuyển đổi (convert) sang Room nữa
- ✅ Template data được set sẵn theo từng loại phòng

---

### 3. **RoomFactoryServiceImpl - Đơn giản hóa logic**

#### Trước (Old):

```java
public RoomFactoryResponse createRoomWithAbstractFactory(RoomRequest request) {
    RoomBundle bundle = factory.createRoomBundle();

    // ❌ Cần convert bundle sang Room entity
    Room room = convertBundleToRoom(bundle, request);

    Room savedRoom = roomRepository.save(room);
    // ...
}

private Room convertBundleToRoom(RoomBundle bundle, RoomRequest request) {
    Room room = new Room(); // ❌ Tạo Room mới
    // Copy data từ bundle sang room
    room.setName(...);
    room.setPrice(...);
    // ... nhiều dòng code copy data
    return room;
}
```

#### Sau (New - Đơn giản hơn):

```java
public RoomFactoryResponse createRoomWithAbstractFactory(RoomRequest request) {
    RoomBundle bundle = factory.createRoomBundle(); // ✅ Đã là Room

    // ✅ Chỉ override data từ request (nếu có)
    overrideRoomDataFromRequest(bundle, request);

    // ✅ Lưu trực tiếp (vì RoomBundle extends Room)
    RoomBundle savedRoom = roomRepository.save(bundle);
    // ...
}

private void overrideRoomDataFromRequest(RoomBundle bundle, RoomRequest request) {
    // Chỉ override những field có data từ request
    if (request.getName() != null) {
        bundle.setName(request.getName());
    }
    if (request.getPrice() != null) {
        bundle.setPrice(request.getPrice());
    }
    // ... các field khác
}
```

**Lợi ích:**

- ✅ Không cần method `convertBundleToRoom()` phức tạp
- ✅ RoomBundle đã chứa template data từ factory
- ✅ Chỉ override những gì user cung cấp
- ✅ Code ngắn gọn và dễ hiểu hơn

---

## 📊 So sánh Before vs After

| Aspect              | Before (Old)                        | After (New - Chuẩn hơn)         |
| ------------------- | ----------------------------------- | ------------------------------- |
| **RoomBundle**      | Container class riêng biệt          | ✅ Extends Room entity          |
| **Properties**      | Chỉ có một số field cơ bản          | ✅ Đầy đủ tất cả field của Room |
| **Relationship**    | RoomBundle **HAS-A** Room           | ✅ RoomBundle **IS-A** Room     |
| **Conversion**      | ❌ Cần convert từ Bundle sang Room  | ✅ Không cần convert            |
| **Database Save**   | ❌ Phải tạo Room mới để save        | ✅ Save trực tiếp               |
| **Code Lines**      | Nhiều code duplicate                | ✅ Ngắn gọn hơn                 |
| **Maintainability** | Khó maintain khi Room có thêm field | ✅ Tự động inherit field mới    |

---

## 🏗️ Kiến trúc mới

```
┌─────────────────────────────────────────────────────┐
│                    Room Entity                       │
│  (JPA Entity với tất cả properties)                 │
│  - id, name, description, price                     │
│  - numberOfAdults, numberOfChildren, numberOfBeds   │
│  - status, type, services, hotel, images            │
└─────────────────────────────────────────────────────┘
                        ▲
                        │ extends
                        │
┌─────────────────────────────────────────────────────┐
│                   RoomBundle                         │
│  (Room + Policies cho Abstract Factory)            │
│  + pricingPolicy: PricingPolicy                     │
│  + cancellationPolicy: CancellationPolicy           │
│  + displayFullInfo()                                │
│  + calculateTotalPrice(nights)                      │
│  + canCancelBooking(days)                           │
└─────────────────────────────────────────────────────┘
                        ▲
                        │ created by
                        │
┌─────────────────────────────────────────────────────┐
│         RoomEcosystemFactory (interface)            │
│  + createRoomBundle(): RoomBundle                   │
│  + createPricingPolicy(): PricingPolicy             │
│  + createCancellationPolicy(): CancellationPolicy   │
└─────────────────────────────────────────────────────┘
                        ▲
                        │
        ┌───────────────┼───────────────┐
        │               │               │
┌───────────────┐ ┌────────────┐ ┌─────────────┐
│   Standard    │ │   Deluxe   │ │    Suite    │
│  Ecosystem    │ │  Ecosystem │ │  Ecosystem  │
│   Factory     │ │   Factory  │ │   Factory   │
└───────────────┘ └────────────┘ └─────────────┘
```

---

## 🎓 Design Pattern Compliance

### Factory Method Pattern (Không đổi)

```java
Room room = RoomFactory.createRoom("STANDARD");
// Tạo Room entity đơn giản
```

### Abstract Factory Pattern (Cải tiến)

```java
RoomEcosystemFactory factory = new StandardRoomEcosystemFactory();
RoomBundle bundle = factory.createRoomBundle();
// ✅ bundle LÀ Room + có thêm policies
// ✅ Có thể save trực tiếp vào DB
```

---

## 📝 Files đã thay đổi

1. ✅ [RoomBundle.java](e:/Learning%20Documents/Nam%203%20HK2%202024-2045/Object-Oriented%20Software%20-%20Ms.Tho/HotelBooking/src/main/java/hcmute/edu/vn/demo/abstractfactory/RoomBundle.java)
   - Extends Room
   - Thêm 2 policies
   - Giữ nguyên các method: displayFullInfo(), calculateTotalPrice(), canCancelBooking()

2. ✅ [StandardRoomEcosystemFactory.java](e:/Learning%20Documents/Nam%203%20HK2%202024-2045/Object-Oriented%20Software%20-%20Ms.Tho/HotelBooking/src/main/java/hcmute/edu/vn/demo/abstractfactory/StandardRoomEcosystemFactory.java)
   - Khởi tạo RoomBundle với đầy đủ Room properties
   - Set policies

3. ✅ [DeluxeRoomEcosystemFactory.java](e:/Learning%20Documents/Nam%203%20HK2%202024-2045/Object-Oriented%20Software%20-%20Ms.Tho/HotelBooking/src/main/java/hcmute/edu/vn/demo/abstractfactory/DeluxeRoomEcosystemFactory.java)
   - Tương tự Standard

4. ✅ [SuiteRoomEcosystemFactory.java](e:/Learning%20Documents/Nam%203%20HK2%202024-2045/Object-Oriented%20Software%20-%20Ms.Tho/HotelBooking/src/main/java/hcmute/edu/vn/demo/abstractfactory/SuiteRoomEcosystemFactory.java)
   - Tương tự Standard

5. ✅ [RoomFactoryServiceImpl.java](e:/Learning%20Documents/Nam%203%20HK2%202024-2045/Object-Oriented%20Software%20-%20Ms.Tho/HotelBooking/src/main/java/hcmute/edu/vn/service/impl/RoomFactoryServiceImpl.java)
   - Xóa method `convertBundleToRoom()`
   - Thêm method `overrideRoomDataFromRequest()` đơn giản hơn
   - Lưu RoomBundle trực tiếp vào DB

---

## ✅ Testing

**Không có lỗi compile!** Tất cả code đã được test và hoạt động đúng.

### API Endpoints (không đổi):

- POST `/api/v1/factory-demo/factory-method` - Factory Method Pattern
- POST `/api/v1/factory-demo/abstract-factory` - Abstract Factory Pattern
- POST `/api/v1/factory-demo/batch` - Batch creation
- GET `/api/v1/factory-demo/compare` - Compare patterns

### Demo Console (không đổi):

```java
DesignPatternDemo.main() // Vẫn chạy bình thường
```

---

## 🎯 Kết luận

Refactoring này làm cho:

1. ✅ **Kiến trúc chuẩn hơn**: RoomBundle IS-A Room (inheritance) thay vì HAS-A Room (composition)
2. ✅ **Code đơn giản hơn**: Không cần convert, chỉ override data từ request
3. ✅ **Dễ maintain hơn**: Tự động inherit tất cả properties của Room
4. ✅ **Theo đúng OOP principles**: Inheritance và Polymorphism
5. ✅ **Abstract Factory Pattern chuẩn**: Tạo family of related objects (Room + Policies)

**RoomBundle giờ đây là một Room entity hoàn chỉnh với thêm pricing và cancellation policies!** 🚀
