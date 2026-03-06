# 🔴 Demo: Vấn đề khi KHÔNG dùng Singleton Pattern

## 📋 Mục đích

Demo này chứng minh **vấn đề nghiêm trọng** xảy ra khi KHÔNG sử dụng Singleton Pattern cho BookingIdGenerator, dẫn đến việc tạo ra **duplicate booking IDs**.

---

## 🚀 Cách chạy Demo

### Option 1: Chạy ThreadSafeDemo

```bash
# Right-click on ThreadSafeDemo.java → Run 'ThreadSafeDemo.main()'
```

Hoặc từ command line:

```bash
cd "e:\Learning Documents\Nam 3 HK2 2024-2045\Object-Oriented Software - Ms.Tho\HotelBooking"
mvn compile exec:java -Dexec.mainClass="hcmute.edu.vn.utils.ThreadSafeDemo"
```

### Option 2: Chạy Unit Tests

```bash
# Chạy test so sánh Singleton vs Non-Singleton
mvn test -Dtest=BookingIdGeneratorTest#testComparisonSingletonVsNonSingleton

# Chạy test demonstrate Non-Singleton problem
mvn test -Dtest=BookingIdGeneratorTest#testNonSingletonProblem
```

---

## 🐛 Vấn đề được Demo

### ❌ Khi KHÔNG dùng Singleton (NonSingletonBookingIdGenerator)

```java
// Service 1 tạo instance riêng
NonSingletonBookingIdGenerator gen1 = new NonSingletonBookingIdGenerator();
String id1 = gen1.generateId();  // "BK-000001" (counter của gen1 = 0)

// Service 2 tạo instance riêng
NonSingletonBookingIdGenerator gen2 = new NonSingletonBookingIdGenerator();
String id2 = gen2.generateId();  // "BK-000001" (counter của gen2 = 0)

// DUPLICATE! 🐛
```

### ✅ Khi DÙNG Singleton (BookingIdGenerator)

```java
// Service 1 lấy singleton instance
BookingIdGenerator gen1 = BookingIdGenerator.getInstance();
String id1 = gen1.generateId();  // "BK-000001"

// Service 2 lấy CÙNG singleton instance
BookingIdGenerator gen2 = BookingIdGenerator.getInstance();
String id2 = gen2.generateId();  // "BK-000002"

// UNIQUE! ✅
```

---

## 📊 Kết quả Demo

### Demo 0: Vấn đề Non-Singleton

Output sẽ hiển thị:

```
═══════════════════════════════════════════════════════════
❌ DEMO 0: VẤN ĐỀ KHI KHÔNG DÙNG SINGLETON PATTERN
═══════════════════════════════════════════════════════════

Service 1 - Booking 1: BK-000001 (counter=1)
Service 2 - Booking 1: BK-000001 (counter=1)
Service 3 - Booking 1: BK-000001 (counter=1)

⚠️  CẢNH BÁO: 3 BOOKINGS CÓ CÙNG ID = BK-000001
⚠️  DUPLICATE DETECTED! Đây là BUG NGHIÊM TRỌNG!

🔴 CONCURRENT SCENARIO - Nhiều threads với Non-Singleton
KẾT QUẢ:
  - Tổng IDs đã tạo: 50
  - IDs duy nhất (unique): 5-10
  - IDs bị trùng (duplicates): 40-45

❌ FAILED: Có 40+ duplicate IDs!
💡 GIẢI PHÁP: Dùng SINGLETON PATTERN!
```

### Test Comparison Results

```
📊 COMPARISON: Singleton vs Non-Singleton
═══════════════════════════════════════════════════════

❌ Test với Non-Singleton:
  Total IDs generated: 500
  Unique IDs: ~50-100
  Duplicates: ~400-450

✅ Test với Singleton:
  Total IDs generated: 500
  Unique IDs: 500
  Duplicates: 0

📊 RESULT:
  ❌ Non-Singleton: 400+ duplicates
  ✅ Singleton: 0 duplicates

💡 Singleton Pattern giải quyết vấn đề duplicate IDs!
```

---

## 🔍 Phân tích Chi tiết

### Tại sao xảy ra Duplicate?

#### Non-Singleton:

```
Thread 1: new NonSingletonBookingIdGenerator() → instance A (counter=0)
Thread 2: new NonSingletonBookingIdGenerator() → instance B (counter=0)
Thread 3: new NonSingletonBookingIdGenerator() → instance C (counter=0)

Thread 1: instanceA.generateId() → counter++ → "BK-000001"
Thread 2: instanceB.generateId() → counter++ → "BK-000001"  ← DUPLICATE!
Thread 3: instanceC.generateId() → counter++ → "BK-000001"  ← DUPLICATE!
```

**Vấn đề:**

- Mỗi instance có **counter riêng**
- Tất cả counters đều bắt đầu = 0
- Kết quả: **Duplicate IDs**

#### Singleton:

```
Thread 1: BookingIdGenerator.getInstance() → instance (counter=0)
Thread 2: BookingIdGenerator.getInstance() → SAME instance (counter=0)
Thread 3: BookingIdGenerator.getInstance() → SAME instance (counter=0)

Thread 1: instance.generateId() → counter++ → "BK-000001" (counter=1)
Thread 2: instance.generateId() → counter++ → "BK-000002" (counter=2)
Thread 3: instance.generateId() → counter++ → "BK-000003" (counter=3)
```

**Giải pháp:**

- Chỉ có **1 instance duy nhất**
- Chỉ có **1 counter duy nhất**
- Kết quả: **Unique IDs**

---

## 🎯 Key Takeaways

### 1. Vấn đề trong Real Application

Trong web application với Spring Boot:

```
User 1 → Request → Thread 1 → BookingService → new Generator() → counter=0
User 2 → Request → Thread 2 → BookingService → new Generator() → counter=0
User 3 → Request → Thread 3 → BookingService → new Generator() → counter=0

Result: 3 bookings với CÙNG booking code! 🐛
```

### 2. Impact trên Database

```sql
-- Có thể xảy ra:
INSERT INTO bookings (booking_code, ...) VALUES ('BK-000001', ...);  ← OK
INSERT INTO bookings (booking_code, ...) VALUES ('BK-000001', ...);  ← ERROR!

-- Nếu không có UNIQUE constraint
-- → Database sẽ chứa duplicate booking codes
-- → Business logic bị sai
-- → Khách hàng có thể truy cập booking của người khác!
```

### 3. Giải pháp: Singleton Pattern

```java
✅ Chỉ 1 instance duy nhất
✅ 1 counter duy nhất
✅ Thread-safe với synchronized
✅ Unique IDs được đảm bảo
✅ No duplicates
```

---

## 📁 Files liên quan

- **NonSingletonBookingIdGenerator.java** - Class để demonstrate vấn đề (❌ KHÔNG dùng trong production!)
- **BookingIdGenerator.java** - Correct implementation với Singleton (✅ Dùng class này!)
- **ThreadSafeDemo.java** - Demo visual với output
- **BookingIdGeneratorTest.java** - Unit tests so sánh 2 approaches

---

## ⚠️ Warning

**KHÔNG BAO GIỜ** sử dụng `NonSingletonBookingIdGenerator` trong production code!

Class này chỉ được tạo ra cho mục đích **educational demonstration** để chứng minh vấn đề.

Luôn luôn dùng `BookingIdGenerator` với Singleton Pattern!

---

## 🧪 Exercise

### Bài tập 1: Observe the Problem

1. Chạy `ThreadSafeDemo`
2. Quan sát Demo 0 về Non-Singleton problem
3. Đếm số lượng duplicates được tạo ra

### Bài tập 2: Compare Results

1. Chạy unit test `testComparisonSingletonVsNonSingleton`
2. So sánh số lượng duplicates giữa 2 approaches
3. Giải thích tại sao Singleton không có duplicates

### Bài tập 3: Fix the Code

1. Xem code trong `BookingServiceImpl`
2. Verify rằng code đang dùng Singleton pattern đúng cách
3. Thử implement tương tự cho `OrderIdGenerator` hoặc `InvoiceIdGenerator`

---

**Remember:**

- Singleton = 1 instance = 1 counter = Unique IDs ✅
- Non-Singleton = Many instances = Many counters = Duplicate IDs ❌
