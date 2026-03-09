# Factory Pattern API - Postman Test Examples

## Base URL

```
http://localhost:8080/api/v1/factory-demo
```

---

## 1. Factory Method Pattern - Tạo phòng STANDARD

### Endpoint

```
POST /factory-method
```

### Request Body (JSON)

```json
{
  "roomType": "STANDARD",
  "name": "Phòng Standard 101",
  "description": "Phòng tiêu chuẩn với đầy đủ tiện nghi cơ bản",
  "numberOfAdults": 2,
  "numberOfChildren": 0,
  "numberOfBeds": 1,
  "price": 500000,
  "services": ["WIFI", "TV", "AIR_CONDITIONING"],
  "hotelId": 1
}
```

### Request Body - Minimal (chỉ có roomType - sử dụng template mặc định)

```json
{
  "roomType": "STANDARD"
}
```

---

## 2. Factory Method Pattern - Tạo phòng DELUXE

### Endpoint

```
POST /factory-method
```

### Request Body (JSON)

```json
{
  "roomType": "DELUXE",
  "name": "Phòng Deluxe 201",
  "description": "Phòng cao cấp với không gian rộng rãi và tiện nghi hiện đại",
  "numberOfAdults": 3,
  "numberOfChildren": 1,
  "numberOfBeds": 1,
  "price": 1000000,
  "services": ["WIFI", "TV", "AIR_CONDITIONING", "MINIBAR", "BATHTUB"],
  "hotelId": 1
}
```

### Request Body - Minimal

```json
{
  "roomType": "DELUXE",
  "name": "Phòng Deluxe 202"
}
```

---

## 3. Factory Method Pattern - Tạo phòng SUITE

### Endpoint

```
POST /factory-method
```

### Request Body (JSON)

```json
{
  "roomType": "SUITE",
  "name": "Phòng Suite Presidential",
  "description": "Phòng hạng sang với không gian rộng rãi, tiện nghi 5 sao",
  "numberOfAdults": 4,
  "numberOfChildren": 2,
  "numberOfBeds": 2,
  "price": 2500000,
  "services": [
    "WIFI",
    "TV",
    "AIR_CONDITIONING",
    "MINIBAR",
    "BATHTUB",
    "BALCONY",
    "SAFE_BOX",
    "ROOM_SERVICE"
  ],
  "hotelId": 1
}
```

---

## 4. Abstract Factory Pattern - Tạo hệ sinh thái phòng STANDARD

### Endpoint

```
POST /abstract-factory
```

### Request Body (JSON)

```json
{
  "roomType": "STANDARD",
  "name": "Phòng Standard Premium 301",
  "description": "Phòng Standard với chính sách giá và hủy linh hoạt",
  "numberOfAdults": 2,
  "numberOfChildren": 1,
  "numberOfBeds": 1,
  "price": 550000,
  "services": ["WIFI", "TV", "AIR_CONDITIONING", "BREAKFAST"],
  "hotelId": 1
}
```

### Request Body - Minimal

```json
{
  "roomType": "STANDARD",
  "name": "Standard Room A1"
}
```

---

## 5. Abstract Factory Pattern - Tạo hệ sinh thái phòng DELUXE

### Endpoint

```
POST /abstract-factory
```

### Request Body (JSON)

```json
{
  "roomType": "DELUXE",
  "name": "Phòng Deluxe Ocean View",
  "description": "Phòng Deluxe nhìn ra biển với chính sách giảm giá hấp dẫn",
  "numberOfAdults": 3,
  "numberOfChildren": 1,
  "numberOfBeds": 2,
  "price": 1200000,
  "services": [
    "WIFI",
    "TV",
    "AIR_CONDITIONING",
    "MINIBAR",
    "BATHTUB",
    "BALCONY"
  ],
  "hotelId": 1
}
```

---

## 6. Abstract Factory Pattern - Tạo hệ sinh thái phòng SUITE

### Endpoint

```
POST /abstract-factory
```

### Request Body (JSON)

```json
{
  "roomType": "SUITE",
  "name": "Royal Suite",
  "description": "Phòng Suite sang trọng bậc nhất với đầy đủ tiện nghi cao cấp",
  "numberOfAdults": 4,
  "numberOfChildren": 2,
  "numberOfBeds": 2,
  "price": 3000000,
  "services": [
    "WIFI",
    "TV",
    "AIR_CONDITIONING",
    "MINIBAR",
    "BATHTUB",
    "BALCONY",
    "SAFE_BOX",
    "ROOM_SERVICE",
    "SPA",
    "GYM"
  ],
  "hotelId": 1
}
```

---

## 7. Batch Creation - Tạo nhiều phòng cùng lúc

### Endpoint

```
POST /batch
```

### Request Body - Tạo 5 phòng STANDARD

```json
{
  "roomType": "STANDARD",
  "name": "Phòng Standard Tầng 1 - ",
  "description": "Phòng Standard tầng 1 với view vườn",
  "numberOfAdults": 2,
  "numberOfChildren": 0,
  "numberOfBeds": 1,
  "price": 500000,
  "services": ["WIFI", "TV", "AIR_CONDITIONING"],
  "hotelId": 1,
  "quantity": 5
}
```

### Request Body - Tạo 3 phòng DELUXE

```json
{
  "roomType": "DELUXE",
  "name": "Deluxe Room Floor 2 - ",
  "numberOfAdults": 3,
  "numberOfChildren": 1,
  "numberOfBeds": 1,
  "price": 1000000,
  "hotelId": 1,
  "quantity": 3
}
```

### Request Body - Tạo 2 phòng SUITE

```json
{
  "roomType": "SUITE",
  "name": "Suite Penthouse - ",
  "description": "Suite cao cấp trên tầng cao nhất",
  "numberOfAdults": 4,
  "numberOfChildren": 2,
  "numberOfBeds": 2,
  "price": 2500000,
  "hotelId": 1,
  "quantity": 2
}
```

---

## 8. So sánh 2 Design Pattern

### Endpoint

```
GET /compare
```

### Response Example

```
╔═══════════════════════════════════════════════════════╗
║       SO SÁNH 2 DESIGN PATTERN                        ║
╠═══════════════════════════════════════════════════════╣
║ FACTORY METHOD:                                       ║
║   ✓ Đơn giản, tạo nhanh                               ║
║   ✗ Chỉ có thông tin cơ bản                           ║
║   ✗ Không có pricing/cancellation policy              ║
╠═══════════════════════════════════════════════════════╣
║ ABSTRACT FACTORY:                                     ║
║   ✓ Tạo hệ sinh thái hoàn chỉnh                       ║
║   ✓ Có đầy đủ pricing policy                          ║
║   ✓ Có đầy đủ cancellation policy                     ║
║   ✓ Đảm bảo tính đồng bộ                              ║
╚═══════════════════════════════════════════════════════╝
```

---

## Lưu ý quan trọng

### 1. Field `roomType` (BẮT BUỘC)

Giá trị hợp lệ:

- `"STANDARD"` - Phòng tiêu chuẩn
- `"DELUXE"` - Phòng cao cấp
- `"SUITE"` - Phòng hạng sang

### 2. Field `services` (TÙY CHỌN)

Các giá trị hợp lệ từ enum ESERVICE:

- `WIFI` - Wi-Fi miễn phí
- `TV` - Truyền hình
- `AIR_CONDITIONING` - Điều hòa
- `MINIBAR` - Minibar
- `BATHTUB` - Bồn tắm
- `BALCONY` - Ban công
- `SAFE_BOX` - Két an toàn
- `ROOM_SERVICE` - Dịch vụ phòng
- `PARKING` - Bãi đỗ xe
- `POOL` / `SWIMMING_POOL` - Hồ bơi
- `BREAKFAST` - Ăn sáng
- `RESTAURANT` - Nhà hàng
- `GYM` - Phòng tập
- `SPA` - Spa
- `PET` - Chấp nhận thú cưng

### 3. Sử dụng Template mặc định

Nếu chỉ truyền `roomType` và bỏ qua các field khác, hệ thống sẽ tự động sử dụng template mặc định:

- **STANDARD**: 2 người lớn, 1 giường, giá 500.000 VNĐ
- **DELUXE**: 3 người lớn, 1 giường King, giá 1.000.000 VNĐ
- **SUITE**: 4 người lớn, 2 giường, giá 2.500.000 VNĐ

### 4. Khác biệt giữa Factory Method và Abstract Factory

#### Factory Method (`/factory-method`)

- Tạo phòng đơn giản
- Không có pricing policy
- Không có cancellation policy
- Phù hợp cho tạo nhanh

#### Abstract Factory (`/abstract-factory`)

- Tạo hệ sinh thái hoàn chỉnh
- Có pricing policy chi tiết (giảm giá theo số đêm)
- Có cancellation policy (chính sách hủy phòng)
- Description sẽ chứa thông tin về policies
- Phù hợp cho nghiệp vụ phức tạp

### 5. Expected Response

#### Success Response

```json
{
  "roomId": 1,
  "roomName": "Phòng Standard 101",
  "roomType": "DOUBLE",
  "basePrice": 500000,
  "capacity": 2,
  "numberOfBeds": 1,
  "amenities": ["WIFI", "TV", "AIR_CONDITIONING"],
  "pricingPolicy": "Standard Pricing: No special discount",
  "cancellationPolicy": "Standard Cancellation: Free cancellation up to 3 days before arrival",
  "factoryUsed": "ABSTRACT FACTORY PATTERN",
  "message": "✅ Hệ sinh thái phòng hoàn chỉnh được tạo và lưu vào DB"
}
```

#### Batch Success Response

```json
{
  "totalCreated": 5,
  "factoryPattern": "ABSTRACT FACTORY PATTERN (BATCH)",
  "rooms": [
    {
      "roomId": 1,
      "roomName": "Phòng Standard Tầng 1 - 1",
      ...
    },
    ...
  ],
  "message": "✅ Đã tạo thành công 5 phòng và lưu vào DB"
}
```

---

## Testing Flow với Postman

1. **Test Factory Method Pattern**
   - Test với STANDARD room (minimal)
   - Test với DELUXE room (custom data)
   - Test với SUITE room (full data)

2. **Test Abstract Factory Pattern**
   - Test với từng loại phòng
   - So sánh response với Factory Method
   - Kiểm tra pricing và cancellation policy

3. **Test Batch Creation**
   - Tạo nhiều phòng cùng loại
   - Kiểm tra naming convention (Room 1, Room 2, ...)

4. **Compare Patterns**
   - Gọi endpoint `/compare`
   - Xem so sánh giữa 2 pattern

---

## Tips

1. **Không có hotelId?**
   - Có thể bỏ qua field `hotelId` nếu chưa có hotel trong DB
   - Phòng vẫn được tạo nhưng không gán vào hotel nào

2. **Override template?**
   - Chỉ cần truyền field bạn muốn thay đổi
   - Hệ thống sẽ dùng template mặc định cho các field còn lại

3. **Test nhanh?**
   - Dùng request minimal (chỉ có roomType)
   - Phù hợp cho demo và testing

4. **Production ready?**
   - Dùng request đầy đủ với tất cả fields
   - Validation sẽ đảm bảo data chính xác
