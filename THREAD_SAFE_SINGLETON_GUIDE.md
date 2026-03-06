# Thread-Safe Singleton Pattern - BookingIdGenerator

## 📚 Tổng quan

Tài liệu này giải thích chi tiết về **Thread-Safe Singleton Pattern** được implement trong `BookingIdGenerator` class, bao gồm các cơ chế thread-safe và lý do tại sao chúng cần thiết.

---

## 🎯 Mục tiêu học tập

Sau khi học xong, bạn sẽ hiểu:

1. **Singleton Pattern** là gì và tại sao dùng nó
2. **Thread-Safe** nghĩa là gì
3. Các cơ chế thread-safe trong Java:
   - `volatile` keyword
   - `synchronized` keyword
   - Double-Checked Locking
4. **Race Condition** và cách tránh
5. Trade-off giữa Performance và Correctness

---

## 1️⃣ Singleton Pattern

### Định nghĩa

- **Chỉ có 1 instance duy nhất** của class trong toàn bộ ứng dụng
- Instance này được **share** giữa tất cả các phần của code

### Tại sao cần Singleton?

```java
// ❌ KHÔNG dùng Singleton - Tạo nhiều generators
BookingIdGenerator gen1 = new BookingIdGenerator();  // counter = 0
BookingIdGenerator gen2 = new BookingIdGenerator();  // counter = 0

gen1.generateId();  // "BK-000001"
gen2.generateId();  // "BK-000001"  ← DUPLICATE! 🐛

// ✅ Dùng Singleton - Chỉ 1 generator
BookingIdGenerator gen1 = BookingIdGenerator.getInstance();
BookingIdGenerator gen2 = BookingIdGenerator.getInstance();
// gen1 và gen2 là CÙNG 1 object

gen1.generateId();  // "BK-000001"
gen2.generateId();  // "BK-000002"  ← UNIQUE! ✓
```

### Cách implement

```java
public class BookingIdGenerator {
    private static BookingIdGenerator instance;  // Single instance

    private BookingIdGenerator() {}  // Private constructor

    public static BookingIdGenerator getInstance() {
        if (instance == null) {
            instance = new BookingIdGenerator();
        }
        return instance;
    }
}
```

**⚠️ Nhưng code trên KHÔNG thread-safe!**

---

## 2️⃣ Thread và Concurrency

### Thread là gì?

- Một **luồng thực thi** trong chương trình
- Có thể có **nhiều threads chạy đồng thời** (concurrent)

### Trong Web Application

```
Browser 1 → Request 1 → Thread 1 → createBooking()
Browser 2 → Request 2 → Thread 2 → createBooking()
Browser 3 → Request 3 → Thread 3 → createBooking()
```

**Tất cả 3 threads có thể chạy CÙNG LÚC!**

---

## 3️⃣ Race Condition - Vấn đề chính

### Scenario: Không có Thread-Safe

```
Initial state: counter = 100

Timeline:
┌─────────────────────────────────────────────────────────────┐
│ Time │ Thread 1              │ Thread 2                     │
├──────┼───────────────────────┼──────────────────────────────┤
│  t1  │ read counter = 100    │                              │
│  t2  │                       │ read counter = 100           │
│  t3  │ counter++ = 101       │                              │
│  t4  │                       │ counter++ = 101              │
│  t5  │ write counter = 101   │                              │
│  t6  │                       │ write counter = 101          │
│  t7  │ return "BK-000101"    │                              │
│  t8  │                       │ return "BK-000101"           │
└──────┴───────────────────────┴──────────────────────────────┘

❌ KẾT QUẢ: 2 bookings có cùng ID = "BK-000101"
```

### Tại sao xảy ra?

1. Thread 1 đọc counter = 100
2. **TRƯỚC KHI** Thread 1 ghi lại, Thread 2 cũng đọc counter = 100
3. Cả 2 threads đều tính ra 101
4. **Duplicate ID!**

Đây gọi là **Race Condition** - 2 threads "đua nhau" truy cập cùng 1 biến.

---

## 4️⃣ Giải pháp: Thread-Safe Mechanisms

### 4.1 `synchronized` keyword

#### Cách hoạt động

```java
public synchronized String generateId() {
    counter++;
    return "BK-" + counter;
}
```

- **Chỉ 1 thread** được vào method tại một thời điểm
- Các threads khác phải **đợi** (wait)
- Đảm bảo **atomic operation** (không bị gián đoạn)

#### Timeline với synchronized:

```
┌─────────────────────────────────────────────────────────────┐
│ Time │ Thread 1              │ Thread 2                     │
├──────┼───────────────────────┼──────────────────────────────┤
│  t1  │ 🔒 lock acquired      │                              │
│  t2  │ read counter = 100    │ ⏳ waiting for lock          │
│  t3  │ counter++ = 101       │ ⏳ waiting for lock          │
│  t4  │ write counter = 101   │ ⏳ waiting for lock          │
│  t5  │ return "BK-000101"    │ ⏳ waiting for lock          │
│  t6  │ 🔓 lock released      │ 🔒 lock acquired             │
│  t7  │                       │ read counter = 101           │
│  t8  │                       │ counter++ = 102              │
│  t9  │                       │ write counter = 102          │
│ t10  │                       │ return "BK-000102"           │
│ t11  │                       │ 🔓 lock released             │
└──────┴───────────────────────┴──────────────────────────────┘

✅ KẾT QUẢ: Thread 1 = "BK-000101", Thread 2 = "BK-000102"
```

### 4.2 `volatile` keyword

#### Vấn đề: CPU Cache

```
         Thread 1              Thread 2
            ↓                     ↓
      CPU 1 Cache           CPU 2 Cache
     instance = X          instance = ???
            ↓                     ↓
         ─────────────────────────────
              Main Memory
           instance = X
```

- Mỗi CPU có **cache riêng**
- Thread 1 ghi vào cache của CPU 1
- Thread 2 đọc từ cache của CPU 2 (chưa được update!)
- Thread 2 có thể thấy **giá trị cũ**

#### Giải pháp: volatile

```java
private static volatile BookingIdGenerator instance;
```

**Volatile đảm bảo:**

- Mọi thread đều đọc/ghi **trực tiếp từ Main Memory**
- Không cache giá trị trong CPU cache
- **Visibility**: Thread nào ghi, threads khác thấy NGAY

```
         Thread 1              Thread 2
            ↓                     ↓
            ↓                     ↓
         ─────────────────────────────
         Main Memory (volatile)
           instance = X
              ↑                     ↑
         Thread 1              Thread 2
         writes here           reads latest value
```

### 4.3 Double-Checked Locking

#### Vấn đề với synchronized đơn giản

```java
public static synchronized BookingIdGenerator getInstance() {
    if (instance == null) {
        instance = new BookingIdGenerator();
    }
    return instance;
}
```

**Nhược điểm:**

- Mỗi lần gọi `getInstance()` đều phải lock
- **Slow!** synchronized có overhead cao
- Chỉ cần lock khi **lần đầu tiên** tạo instance

#### Giải pháp: Double-Checked Locking

```java
public static BookingIdGenerator getInstance() {
    if (instance == null) {           // ① First check - KHÔNG synchronized
        synchronized (BookingIdGenerator.class) {  // ② Lock
            if (instance == null) {   // ③ Second check - TRONG synchronized
                instance = new BookingIdGenerator();
            }
        }
    }
    return instance;
}
```

**Tại sao 2 lần check?**

```
Scenario: Thread 1 và Thread 2 cùng thấy instance == null

Timeline:
┌─────────────────────────────────────────────────────────────┐
│ Time │ Thread 1              │ Thread 2                     │
├──────┼───────────────────────┼──────────────────────────────┤
│  t1  │ ① if (instance==null) │                              │
│  t2  │    → true             │                              │
│  t3  │ ② synchronized block  │ ① if (instance==null)        │
│  t4  │    lock acquired      │    → true                    │
│  t5  │ ③ if (instance==null) │ ② synchronized block         │
│  t6  │    → true             │    ⏳ waiting...              │
│  t7  │    create instance    │    ⏳ waiting...              │
│  t8  │    lock released      │    lock acquired             │
│  t9  │                       │ ③ if (instance==null)        │
│ t10  │                       │    → FALSE! (đã có rồi)      │
│ t11  │                       │    KHÔNG tạo instance mới    │
│ t12  │                       │    lock released             │
└──────┴───────────────────────┴──────────────────────────────┘
```

**Lợi ích:**

- ✅ **Fast**: Chỉ lock khi instance == null (lần đầu)
- ✅ **Safe**: Đảm bảo chỉ 1 instance được tạo
- ✅ **Efficient**: Các lần sau không cần lock

---

## 5️⃣ Code Analysis - BookingIdGenerator

### Full Implementation

```java
public class BookingIdGenerator {
    // [1] volatile: Visibility across threads
    private static volatile BookingIdGenerator instance;

    // [2] Counter for ID generation
    private int counter = 0;

    // [3] Private constructor: Singleton pattern
    private BookingIdGenerator() {}

    // [4] Double-Checked Locking
    public static BookingIdGenerator getInstance() {
        if (instance == null) {                    // First check
            synchronized (BookingIdGenerator.class) { // Lock
                if (instance == null) {            // Second check
                    instance = new BookingIdGenerator();
                }
            }
        }
        return instance;
    }

    // [5] synchronized method: Thread-safe ID generation
    public synchronized String generateId() {
        counter++;
        return "BK-" + String.format("%06d", counter);
    }
}
```

### Giải thích từng phần:

#### [1] volatile instance

```java
private static volatile BookingIdGenerator instance;
```

- **static**: Shared giữa tất cả objects
- **volatile**: Mọi thread thấy giá trị mới nhất
- Ngăn instruction reordering

#### [2] counter

```java
private int counter = 0;
```

- Không cần volatile vì được protect bởi **synchronized method**

#### [3] Private constructor

```java
private BookingIdGenerator() {}
```

- Ngăn không cho code bên ngoài tạo instance
- Chỉ có thể tạo instance qua `getInstance()`

#### [4] getInstance() - Double-Checked Locking

```java
public static BookingIdGenerator getInstance() {
    if (instance == null) {                    // Fast path
        synchronized (BookingIdGenerator.class) { // Slow path (first time only)
            if (instance == null) {
                instance = new BookingIdGenerator();
            }
        }
    }
    return instance;
}
```

#### [5] synchronized generateId()

```java
public synchronized String generateId() {
    counter++;
    return "BK-" + String.format("%06d", counter);
}
```

- **synchronized**: Chỉ 1 thread vào tại một thời điểm
- Đảm bảo counter++ là atomic
- Format: BK-000001, BK-000002, ...

---

## 6️⃣ How to Use

### Trong Service Class

```java
@Service
public class BookingServiceImpl implements BookingService {

    @Override
    public Booking createBooking(BookingRequest request) {
        // Get singleton instance
        BookingIdGenerator idGenerator = BookingIdGenerator.getInstance();

        // Generate unique booking code
        String bookingCode = idGenerator.generateId();

        // Create booking
        Booking booking = new Booking();
        booking.setBookingCode(bookingCode);  // BK-000001, BK-000002, ...
        // ... set other fields

        return bookingRepository.save(booking);
    }
}
```

### Kết quả

```
Request 1 (Thread 1) → booking.bookingCode = "BK-000001"
Request 2 (Thread 2) → booking.bookingCode = "BK-000002"
Request 3 (Thread 3) → booking.bookingCode = "BK-000003"
...
Request 1000 (Thread 10) → booking.bookingCode = "BK-001000"
```

**✅ Mỗi booking có ID duy nhất, không duplicate!**

---

## 7️⃣ Testing

### Run Unit Tests

```bash
# Run all tests
mvn test -Dtest=BookingIdGeneratorTest

# Run specific test
mvn test -Dtest=BookingIdGeneratorTest#testThreadSafetyWithMultipleThreads
```

### Run Demo

```bash
# Right-click on ThreadSafeDemo.java → Run 'ThreadSafeDemo.main()'
```

Output sẽ hiển thị:

- ✅ Singleton pattern verification
- ✅ ID generation format
- ✅ Thread-safety với 50+ concurrent threads
- ✅ Performance metrics

---

## 8️⃣ Trade-offs

### Performance Impact

| Approach               | Speed            | Safety             | Use Case             |
| ---------------------- | ---------------- | ------------------ | -------------------- |
| No synchronization     | ⚡⚡⚡ Very Fast | ❌ Not thread-safe | Single-threaded only |
| Full synchronization   | 🐌 Slow          | ✅ Thread-safe     | Simple but slow      |
| Double-Checked Locking | ⚡⚡ Fast        | ✅ Thread-safe     | **Best practice**    |

### synchronized overhead:

- Lock/unlock có cost
- Threads phải đợi nhau
- **Trade-off: Correctness > Speed**

### Khi nào cần Thread-Safe?

✅ **CẦN:**

- Multi-threaded environment (Web servers, Spring Boot)
- Shared state (counter, caches, singletons)
- Concurrent writes

❌ **KHÔNG CẦN:**

- Single-threaded applications
- Immutable objects
- Thread-local variables

---

## 9️⃣ Common Mistakes

### ❌ Quên volatile

```java
private static BookingIdGenerator instance;  // ❌ Không volatile

public static BookingIdGenerator getInstance() {
    if (instance == null) {
        synchronized (BookingIdGenerator.class) {
            if (instance == null) {
                instance = new BookingIdGenerator();
            }
        }
    }
    return instance;
}
```

**Vấn đề:** Có thể xảy ra instruction reordering, thread khác thấy partially-constructed object!

### ❌ Quên synchronized trong generateId()

```java
public String generateId() {  // ❌ Không synchronized
    counter++;
    return "BK-" + counter;
}
```

**Vấn đề:** Race condition → duplicate IDs!

### ❌ Chỉ có 1 lần check

```java
public static BookingIdGenerator getInstance() {
    if (instance == null) {  // ❌ Chỉ 1 lần check
        instance = new BookingIdGenerator();
    }
    return instance;
}
```

**Vấn đề:** Nhiều threads có thể tạo multiple instances!

---

## 🔟 Summary

### Key Takeaways

1. **Singleton Pattern**: 1 class = 1 instance duy nhất
2. **Thread-Safe**: Hoạt động đúng khi nhiều threads truy cập đồng thời
3. **volatile**: Đảm bảo visibility across threads
4. **synchronized**: Đảm bảo mutual exclusion (chỉ 1 thread tại một thời điểm)
5. **Double-Checked Locking**: Tối ưu performance + đảm bảo thread-safety
6. **Race Condition**: Bug xảy ra khi threads "đua nhau" truy cập share state

### Implementation Checklist

- ✅ `volatile` for instance variable
- ✅ Private constructor
- ✅ Double-checked locking in `getInstance()`
- ✅ `synchronized` for state-modifying methods
- ✅ Unit tests to verify thread-safety

---

## 📖 Further Reading

### Java Concurrency Topics

1. **Atomic Classes**: `AtomicInteger`, `AtomicLong`
2. **Concurrent Collections**: `ConcurrentHashMap`
3. **Locks**: `ReentrantLock`, `ReadWriteLock`
4. **Thread Pools**: `ExecutorService`

### Alternative Singleton Approaches

```java
// Enum Singleton (Joshua Bloch's recommendation)
public enum BookingIdGenerator {
    INSTANCE;

    private int counter = 0;

    public synchronized String generateId() {
        counter++;
        return "BK-" + String.format("%06d", counter);
    }
}

// Usage: BookingIdGenerator.INSTANCE.generateId();
```

**Lợi ích:**

- Thread-safe by default (JVM guarantee)
- Serialization-safe
- More concise

---

## 🎓 Exercises

### Exercise 1: Verify Thread-Safety

Chạy `ThreadSafeDemo` và quan sát output. Explain tại sao không có duplicates.

### Exercise 2: Break It

Tạm thời remove `synchronized` từ `generateId()`. Run tests và quan sát failures.

### Exercise 3: Implement Your Own

Implement một `OrderIdGenerator` class với thread-safe singleton pattern.

### Exercise 4: Performance Test

So sánh performance giữa:

1. Full synchronization
2. Double-checked locking
3. No synchronization (observe failures)

---

**Author:** AI Assistant  
**Date:** 2026-03-02  
**Version:** 1.0
