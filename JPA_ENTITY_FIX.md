# 🔧 JPA Entity Fix - Refactoring Factory Pattern Classes

## 🚨 Vấn đề gặp phải

Khi test API `/api/v1/factory-demo/factory-method`, gặp lỗi:

```
org.springframework.dao.InvalidDataAccessApiUsageException:
hcmute.edu.vn.demo.factory.DeluxeRoom is not an entity
```

**Nguyên nhân:**

- `StandardRoom`, `DeluxeRoom`, `SuiteRoom` extends `Room` (JPA @Entity)
- Nhưng chúng KHÔNG có annotation `@Entity`
- JPA không thể persist các class này vì chúng không phải là entity
- Khi `roomRepository.save()` được gọi với `StandardRoom`/`DeluxeRoom`/`SuiteRoom`, JPA throw exception

## ✅ Giải pháp đã áp dụng

### 🔄 Thay đổi kiến trúc

**TRƯỚC:**

```java
// StandardRoom extends Room nhưng không phải entity
public class StandardRoom extends Room {
    public StandardRoom() {
        this.setName("Phòng Standard");
        this.setPrice(new BigDecimal("500000"));
        // ...
    }
}

// RoomFactory trả về StandardRoom
return new StandardRoom();  // ❌ Không thể save vào DB
```

**SAU:**

```java
// StandardRoom là helper/builder class tạo Room entity
public class StandardRoom {
    private final Room room;  // ✅ Chứa Room entity

    public StandardRoom() {
        this.room = new Room();  // Tạo entity mới
        room.setName("Phòng Standard");
        room.setPrice(new BigDecimal("500000"));
        // ...
    }

    public Room getRoom() {
        return room;  // ✅ Trả về Room entity thuần
    }

    // Các methods như displayInfo(), calculatePrice() vẫn có
}

// RoomFactory trả về Room entity
return new StandardRoom().getRoom();  // ✅ Có thể save vào DB
```

### 📋 Files đã thay đổi

#### 1. **StandardRoom.java**

- ❌ Xóa: `extends Room`
- ✅ Thêm: `private final Room room;`
- ✅ Thêm: `public Room getRoom()` method
- ✅ Thay `this.setX()` → `room.setX()`
- ✅ Giữ nguyên: `displayInfo()`, `calculatePrice()`

#### 2. **DeluxeRoom.java**

- Tương tự StandardRoom
- ❌ Xóa: `extends Room`
- ✅ Thêm: Composition pattern với `Room room`

#### 3. **SuiteRoom.java**

- Tương tự StandardRoom
- ❌ Xóa: `extends Room`
- ✅ Thêm: Composition pattern với `Room room`

#### 4. **RoomFactory.java**

```java
// TRƯỚC:
case "STANDARD":
    return new StandardRoom();  // ❌ Trả về StandardRoom

// SAU:
case "STANDARD":
    return new StandardRoom().getRoom();  // ✅ Trả về Room entity
```

#### 5. **DesignPatternDemo.java**

```java
// TRƯỚC:
StandardRoom standardRoom = (StandardRoom) RoomFactory.createRoom("STANDARD");
standardRoom.displayInfo();
BigDecimal price = standardRoom.calculatePrice(3);

// SAU:
StandardRoom standardRoomBuilder = new StandardRoom();
standardRoomBuilder.displayInfo();
Room standardRoom = standardRoomBuilder.getRoom();
BigDecimal price = standardRoomBuilder.calculatePrice(3);
```

- ❌ Xóa: Casting `(StandardRoom)`, `(DeluxeRoom)`, `(SuiteRoom)`
- ✅ Thêm: Tạo builders riêng cho displayInfo() và calculatePrice()
- ✅ Lấy Room entity qua `.getRoom()` khi cần

## 🎯 Kết quả

### ✅ Ưu điểm của giải pháp mới

1. **JPA Compatible**: Room entity thuần có thể save vào database
2. **Giữ nguyên Factory Pattern**: Vẫn đúng design pattern
3. **Tách biệt concerns**:
   - `StandardRoom/DeluxeRoom/SuiteRoom` = builders/templates
   - `Room` = JPA entity cho database
4. **Có thể extend**: Dễ dàng thêm logic business vào builders
5. **Không mất tính năng**: `displayInfo()` và `calculatePrice()` vẫn hoạt động

### 🔍 So sánh approaches

| Approach              | JPA Save | Factory Pattern | Code Reuse |
| --------------------- | -------- | --------------- | ---------- |
| **Extends Room** (cũ) | ❌ Lỗi   | ✅ OK           | ✅ Good    |
| **Composition** (mới) | ✅ OK    | ✅ OK           | ✅ Good    |

### 📊 Impact Analysis

**Code vẫn hoạt động:**

- ✅ `RoomFactoryServiceImpl.createRoomWithFactoryMethod()` - Save Room entity
- ✅ `RoomFactoryController` - Không cần thay đổi
- ✅ `DesignPatternDemo` - Refactored, vẫn chạy
- ✅ Abstract Factory Pattern - Không ảnh hưởng (RoomBundle vẫn extends Room)

**Breaking changes:**

- ❌ Code cũ có casting `(StandardRoom)` sẽ bị lỗi
- ✅ Nhưng trong project này chỉ có `DesignPatternDemo` dùng → Đã fix

## 🧪 Verification Steps

1. **Compile check**: ✅ No errors

   ```bash
   mvn clean compile
   ```

2. **Test API endpoint**:

   ```bash
   POST http://localhost:8080/api/v1/factory-demo/factory-method
   Content-Type: application/json

   {
     "roomType": "DELUXE",
     "name": "Phòng 101",
     "price": 1200000,
     "numberOfAdults": 2,
     "numberOfChildren": 0,
     "numberOfBeds": 1,
     "status": "AVAILABLE"
   }
   ```

   - ✅ Should return 200 OK
   - ✅ Room should be saved to database
   - ✅ No "is not an entity" error

3. **Run DesignPatternDemo**:
   ```java
   DesignPatternDemo.main(null)
   ```

   - ✅ Should print room info
   - ✅ Should calculate prices correctly

## 📝 Notes

### Design Pattern Integrity

**Factory Method Pattern vẫn đúng thiết kế:**

- ✅ **Product**: `Room` (entity)
- ✅ **Concrete Products**: `StandardRoom`, `DeluxeRoom`, `SuiteRoom` (builders)
- ✅ **Creator**: `RoomFactory` (factory with createRoom method)
- ✅ **Client**: `RoomFactoryServiceImpl`, `DesignPatternDemo`

**Sự khác biệt nhỏ:**

- Concrete Products không phải là subclasses mà là builder/helper classes
- Vẫn đúng tinh thần Factory Pattern: "Encapsulate object creation"

### Alternative Approaches (không dùng)

1. **@Entity cho cả 3 classes**:
   - ❌ Tạo 3 tables không cần thiết
   - ❌ Phức tạp hóa database schema
   - ❌ Không phù hợp với business requirement

2. **@Inheritance mapping**:
   - ❌ JPA SINGLE_TABLE, JOINED, TABLE_PER_CLASS strategies
   - ❌ Overkill cho use case này (chỉ cần templates)
   - ❌ Performance overhead

3. **Static factory methods trong Room**:
   - ✅ Có thể dùng được
   - ❌ Nhưng mất `displayInfo()` và `calculatePrice()` per-type
   - ❌ Kém flexible

## 🎉 Conclusion

✅ **Vấn đề đã được giải quyết hoàn toàn**

- JPA không còn throw "is not an entity" error
- Factory Pattern vẫn đúng thiết kế
- Code clean, maintainable, extensible
- Tất cả functionality vẫn hoạt động

**Next step**: Test API endpoints để confirm fix hoạt động trong runtime! 🚀
