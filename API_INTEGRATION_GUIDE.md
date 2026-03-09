# 🚀 Factory Pattern API - Hướng dẫn sử dụng

## ✅ Hoàn thành tích hợp API

Đã tích hợp **Factory Pattern** vào hệ thống API thực tế với:

- ✅ REST API Controller
- ✅ Service Layer với Database integration
- ✅ Frontend HTML/JavaScript
- ✅ Swagger/OpenAPI documentation

---

## 🏗️ Kiến trúc hệ thống

```
┌─────────────────────────────────────────────────────────────┐
│                     FRONTEND (HTML/JS)                      │
│              http://localhost:8080/factory-pattern-demo.html│
└─────────────────┬───────────────────────────────────────────┘
                  │ HTTP POST/GET
                  ▼
┌─────────────────────────────────────────────────────────────┐
│              REST API CONTROLLER                            │
│         /api/v1/factory-demo/*                              │
│  • POST /factory-method                                     │
│  • POST /abstract-factory                                   │
│  • POST /batch                                              │
│  • GET  /compare                                            │
└─────────────────┬───────────────────────────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────────────────────────┐
│              SERVICE LAYER                                  │
│         RoomFactoryServiceImpl                              │
│  ┌──────────────────────┐  ┌─────────────────────┐         │
│  │ Factory Method       │  │ Abstract Factory    │         │
│  │ Pattern              │  │ Pattern             │         │
│  └──────────────────────┘  └─────────────────────┘         │
└─────────────────┬───────────────────────────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────────────────────────┐
│                     DATABASE (MySQL)                        │
│                  Table: rooms                               │
└─────────────────────────────────────────────────────────────┘
```

---

## 🚀 Cách chạy

### Bước 1: Khởi động Spring Boot

```bash
cd "e:\Learning Documents\Nam 3 HK2 2024-2045\Object-Oriented Software - Ms.Tho\HotelBooking"

# Compile
mvn clean compile

# Run Spring Boot
mvn spring-boot:run
```

### Bước 2: Mở Frontend

**Option 1: Trực tiếp**

```
http://localhost:8080/factory-pattern-demo.html
```

**Option 2: Swagger UI**

```
http://localhost:8080/swagger-ui.html
```

---

## 📡 API Endpoints

### 1. 🔧 Factory Method Pattern

**Endpoint:** `POST /api/v1/factory-demo/factory-method`

**Request:**

```json
{
  "roomType": "STANDARD",
  "roomName": "Phòng 101",
  "hotelId": 1,
  "quantity": 1
}
```

**Response:**

```json
{
  "roomId": 123,
  "roomName": "Phòng 101",
  "roomType": "SINGLE",
  "basePrice": 500000,
  "capacity": 2,
  "numberOfBeds": 1,
  "amenities": ["WIFI", "BREAKFAST", "PARKING"],
  "factoryUsed": "FACTORY METHOD PATTERN",
  "message": "✅ Phòng được tạo bằng Factory Method và lưu vào DB"
}
```

**Đặc điểm:**

- ✅ Tạo phòng đơn giản
- ❌ Không có pricing policy
- ❌ Không có cancellation policy

---

### 2. 🏭 Abstract Factory Pattern

**Endpoint:** `POST /api/v1/factory-demo/abstract-factory`

**Request:**

```json
{
  "roomType": "DELUXE",
  "roomName": "Phòng VIP 201",
  "hotelId": 1,
  "quantity": 1
}
```

**Response:**

```json
{
  "roomId": 124,
  "roomName": "Phòng VIP 201",
  "roomType": "DOUBLE",
  "basePrice": 1000000,
  "capacity": 4,
  "numberOfBeds": 2,
  "amenities": ["WIFI", "BREAKFAST", "PARKING"],
  "pricingPolicy": "Giảm 5% từ 3 đêm, 10% từ 7 đêm",
  "cancellationPolicy": "Miễn phí hủy trước 10 ngày...",
  "factoryUsed": "ABSTRACT FACTORY PATTERN",
  "message": "✅ Hệ sinh thái phòng hoàn chỉnh được tạo và lưu vào DB"
}
```

**Đặc điểm:**

- ✅ Tạo hệ sinh thái hoàn chỉnh
- ✅ Có pricing policy đầy đủ
- ✅ Có cancellation policy đầy đủ
- ✅ Đảm bảo tính đồng bộ

---

### 3. 📦 Batch Creation

**Endpoint:** `POST /api/v1/factory-demo/batch`

**Request:**

```json
{
  "roomType": "SUITE",
  "roomName": "Phòng Suite",
  "hotelId": 1,
  "quantity": 5
}
```

**Response:**

```json
{
  "totalCreated": 5,
  "factoryPattern": "ABSTRACT FACTORY PATTERN (BATCH)",
  "rooms": [
    { "roomId": 125, "roomName": "Phòng Suite 1", ... },
    { "roomId": 126, "roomName": "Phòng Suite 2", ... },
    ...
  ],
  "message": "✅ Đã tạo thành công 5 phòng và lưu vào DB"
}
```

---

### 4. 📊 So sánh Patterns

**Endpoint:** `GET /api/v1/factory-demo/compare`

**Response:**

```
╔═══════════════════════════════════════════════════════╗
║       SO SÁNH 2 DESIGN PATTERN                        ║
╠═══════════════════════════════════════════════════════╣
║ FACTORY METHOD:                                       ║
║   ✓ Đơn giản, tạo nhanh                               ║
║   ✗ Chỉ có thông tin cơ bản                           ║
...
```

---

## 🎨 Frontend Demo

File: [factory-pattern-demo.html](src/main/resources/static/factory-pattern-demo.html)

### Features:

- ✅ Form nhập thông tin phòng
- ✅ Chọn loại phòng (Standard/Deluxe/Suite)
- ✅ 4 buttons cho 4 patterns
- ✅ Hiển thị kết quả real-time
- ✅ Loading indicator
- ✅ Error handling
- ✅ Responsive design

### Screenshots:

**Giao diện chính:**

```
┌─────────────────────────────────────────────────────┐
│  🏭 Factory Pattern Demo                            │
│  Demo trực quan Factory Method vs Abstract Factory  │
├─────────────────────────────────────────────────────┤
│  📝 Thông tin phòng    │  🎯 Chọn Design Pattern   │
│                         │                            │
│  Loại phòng: [dropdown]│  [🔧 Factory Method]       │
│  Tên phòng: [input]    │  [🏭 Abstract Factory]     │
│  Hotel ID: [input]     │  [📦 Batch Creation]       │
│  Số lượng: [input]     │  [📊 So sánh 2 Pattern]   │
└─────────────────────────────────────────────────────┘
│  📋 Kết quả                                          │
│  [Response JSON hiển thị ở đây]                     │
└─────────────────────────────────────────────────────┘
```

---

## 📝 Luồng hoạt động

### Factory Method Pattern:

```
1. User chọn "Factory Method" trên Frontend
2. Frontend gửi POST request với roomType
3. Controller nhận request
4. Service gọi RoomFactory.createRoom(type)
5. Factory tạo BaseRoom (Standard/Deluxe/Suite)
6. Service convert BaseRoom → Room entity
7. Repository lưu vào database
8. Return response về Frontend
9. Frontend hiển thị kết quả
```

### Abstract Factory Pattern:

```
1. User chọn "Abstract Factory" trên Frontend
2. Frontend gửi POST request với roomType
3. Controller nhận request
4. Service tạo RoomEcosystemFactory tương ứng
5. Factory tạo RoomBundle (Room + PricingPolicy + CancellationPolicy)
6. Service convert RoomBundle → Room entity (kèm policies trong description)
7. Repository lưu vào database
8. Return response với đầy đủ policies
9. Frontend hiển thị kết quả hoàn chỉnh
```

---

## 🔍 Kiểm tra Database

Sau khi tạo phòng, kiểm tra trong database:

```sql
-- Xem phòng vừa tạo
SELECT * FROM rooms
ORDER BY id DESC
LIMIT 10;

-- Kiểm tra phòng theo loại
SELECT
    id,
    name,
    type,
    price,
    description
FROM rooms
WHERE description LIKE '%Factory%';
```

---

## 📊 So sánh kết quả

### Factory Method tạo phòng:

```sql
id: 123
name: "Phòng 101"
type: SINGLE
price: 500000
description: "Phòng được tạo bằng Factory Method Pattern"
services: ["WIFI", "BREAKFAST", "PARKING"]
```

### Abstract Factory tạo phòng:

```sql
id: 124
name: "Phòng VIP 201"
type: DOUBLE
price: 1000000
description: "Phòng được tạo bằng Abstract Factory Pattern
             Chính sách giá: Giảm 5% từ 3 đêm, 10% từ 7 đêm
             Chính sách hủy: Miễn phí hủy trước 10 ngày..."
services: ["WIFI", "BREAKFAST", "PARKING"]
```

**Khác biệt rõ ràng:**

- Abstract Factory có **description chi tiết hơn**
- Bao gồm **pricing policy**
- Bao gồm **cancellation policy**

---

## 🧪 Test Cases

### Test 1: Tạo phòng Standard

```bash
curl -X POST http://localhost:8080/api/v1/factory-demo/factory-method \
  -H "Content-Type: application/json" \
  -d '{
    "roomType": "STANDARD",
    "roomName": "Test Room 101",
    "hotelId": 1
  }'
```

### Test 2: Tạo phòng Deluxe với Abstract Factory

```bash
curl -X POST http://localhost:8080/api/v1/factory-demo/abstract-factory \
  -H "Content-Type: application/json" \
  -d '{
    "roomType": "DELUXE",
    "roomName": "Deluxe Room 201",
    "hotelId": 1
  }'
```

### Test 3: Batch create 5 phòng Suite

```bash
curl -X POST http://localhost:8080/api/v1/factory-demo/batch \
  -H "Content-Type: application/json" \
  -d '{
    "roomType": "SUITE",
    "roomName": "Suite Room",
    "hotelId": 1,
    "quantity": 5
  }'
```

### Test 4: So sánh patterns

```bash
curl http://localhost:8080/api/v1/factory-demo/compare
```

---

## 📈 Lợi ích của tích hợp này

### 1. Trực quan hơn:

- ❌ Trước: Chỉ có log khô khan trong console
- ✅ Sau: Giao diện web đẹp, dễ thao tác

### 2. Thực tế hơn:

- ❌ Trước: Demo code không lưu gì
- ✅ Sau: Lưu vào database thật, có thể query

### 3. Tương tác hơn:

- ❌ Trước: Phải chạy main() mỗi lần test
- ✅ Sau: Click button để test, thay đổi tham số dễ dàng

### 4. Dễ demo hơn:

- ❌ Trước: Phải share code + hướng dẫn chạy
- ✅ Sau: Share link web, ai cũng dùng được

---

## 🎯 Kết quả đạt được

✅ **API hoàn chỉnh** với 4 endpoints  
✅ **Frontend đẹp** với HTML/CSS/JavaScript  
✅ **Database integration** - lưu data thật  
✅ **Swagger documentation** tự động  
✅ **Response có structure** rõ ràng  
✅ **Error handling** đầy đủ  
✅ **Logging** chi tiết  
✅ **CORS enabled** cho frontend

---

## 🚨 Troubleshooting

### Lỗi: Cannot connect to server

```
Kiểm tra:
- Spring Boot đã chạy chưa?
- Port 8080 có bị chiếm chưa?
- Database đã kết nối chưa?
```

### Lỗi: Hotel not found

```
Giải pháp:
- Tạo hotel trước trong database
- Hoặc để hotelId = null
```

### Frontend không hiển thị

```
Kiểm tra:
- File HTML có trong src/main/resources/static/?
- Spring Boot đã start thành công?
- Browser console có lỗi không?
```

---

## 📚 Files đã tạo

```
1. DTOs:
   - RoomFactoryRequest.java
   - RoomFactoryResponse.java
   - BatchRoomCreationResponse.java

2. Service:
   - RoomFactoryService.java (interface)
   - RoomFactoryServiceImpl.java (implementation)

3. Controller:
   - RoomFactoryController.java

4. Frontend:
   - factory-pattern-demo.html

5. Documentation:
   - API_INTEGRATION_GUIDE.md (file này)

6. Updated:
   - pom.xml (thêm Swagger dependency)
```

---

## 🎉 Hoàn thành!

Bây giờ bạn có một **hệ thống hoàn chỉnh** để demo Factory Pattern với:

- ✅ REST API thực tế
- ✅ Frontend tương tác
- ✅ Database persistence
- ✅ Documentation đầy đủ

**Happy Coding! 🚀**
