# Hotel Booking RESTful API with Spring Boot & MySQL

## 🌐 Tổng quan kiến trúc dự án

Dự án sử dụng kiến trúc **RESTful API** trên nền tảng **Spring Boot**, kết hợp với cơ sở dữ liệu **MySQL**. Toàn bộ hệ thống được thiết kế theo mô hình **Layered Architecture** (Controller – Service – Repository), tách biệt rõ ràng giữa các tầng để tăng tính mở rộng và bảo trì.

Các công nghệ chính:
- Java 17+
- Spring Boot 3+
- Spring Data JPA
- MySQL
- Lombok
- Swagger (OpenAPI)
- Maven

## 🔄 Phát triển theo Interactive and Incremental Framework

Tuân theo 4 giai đoạn chính:
1. Inception: Khởi tạo dự án, xác định yêu cầu và lên kế hoạch phát triển.
2. Elaboration: Phân tích yêu cầu, thiết kế kiến trúc và xây dựng mô hình dữ liệu.
3. Construction: Phát triển các tính năng chính, kiểm thử và tối ưu hóa.
4. Transition: Triển khai hệ thống, thu thập phản hồi và cải tiến.

Quy trình phát triển được chia thành nhiều vòng lặp nhỏ (iterations) bao gồm các bước sau:

1. **Lập kế hoạch vòng lặp**
    - Xác định mục tiêu cho vòng lặp hiện tại (VD: Xây dựng module đặt phòng).
2. **Phân tích & thiết kế**
    - Xây dựng mô hình Use Case, Class Diagram và Sequence Diagram cho tính năng sắp phát triển.
3. **Cài đặt từng bước (Incremental)**
    - Phát triển tính năng theo từng phần nhỏ có thể kiểm thử được.
4. **Kiểm thử và đánh giá**
    - Viết Unit Test / Integration Test cho từng module.
5. **Phản hồi & cải tiến**
    - Thu thập phản hồi, cải tiến kiến trúc hoặc quy trình nếu cần.

## 📊 Áp dụng UML: Use Case, Class, Sequence Diagram

1. **Use Case Diagram**: Mô hình hóa các chức năng chính như Đăng ký, Đăng nhập, Tìm kiếm phòng, Đặt phòng, Quản lý đặt phòng.
2. **Class Diagram**: Thiết kế các thực thể như `User`, `Room`, `Booking`, `Hotel`, `...` để ánh xạ với các bảng trong MySQL.
3. **Sequence Diagram**: Mô phỏng dòng chảy dữ liệu giữa client – controller – service – repository – database trong quá trình đặt phòng.

Việc mô hình hóa giúp xác định rõ luồng xử lý và mối quan hệ giữa các đối tượng, giảm thiểu lỗi logic khi triển khai.

## 🏨 Nội dung chính của dự án

Dự án xây dựng một hệ thống **đặt phòng khách sạn trực tuyến**, nơi người dùng có thể:
- Đăng ký và đăng nhập tài khoản
- Tìm kiếm phòng theo ngày, loại phòng, giá
- Đặt phòng và theo dõi tình trạng
- Quản lý các lượt đặt của mình

## 🤝 Đóng góp vào dự án

1. **Fork** repository
2. **Clone** về máy cục bộ:
   ```bash
   git clone https://github.com/your-username/hotel-booking-api.git
3. Tạo nhan mới: `git checkout -b feature/ten-tinh-nang`
4. Thực hiện thay đổi và commit:
   ```bash
   git add .
   git commit -m "Thêm tính năng X"
   ```
# 📘 Bài học & kinh nghiệm rút ra
- Việc mô hình hóa rõ ràng trước khi code giúp tiết kiệm thời gian debug và tái cấu trúc.
- Xác định phương pháp phát triển phần mềm phù hợp giúp tăng hiệu suất làm việc.
  - Nếu với dự án vừa và nhỏ, có thể áp dụng mô hình Waterfall.
  - Với dự án lớn hơn nhưng chưa xác định yêu cầu rõ ràng, cần nhìn thấy sản phẩm nhanh và cần sự linh hoạt trong phát triển có thể áp dụng mô hình Agile.
  - Đối với dự án của nhóm, có thể áp dụng mô hình Interactive and Incremental Framework để bắt kịp tiến độ và có thể điều chỉnh theo phản hồi của khách hàng.
- Spring Boot kết hợp JPA giảm thiểu nhiều thao tác cấu hình, giúp tập trung vào logic nghiệp vụ.
- Việc quản lý mã nguồn với Git và Pull Request giúp dự án cộng tác hiệu quả hơn.


