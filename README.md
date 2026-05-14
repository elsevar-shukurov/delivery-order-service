# Delivery Order Service

Order Service – delivery sisteminin əsas komponenti. Sifarişlərin yaradılması, statusunun idarə edilməsi, kuryer təyin edilməsi (sinxron Feign ilə) və hadisələrin yayımlanmasından (RabbitMQ) məsuldur.

## 📋 Tələblər (Specification)

- Sifariş yarat (`POST /orders`)
  - Pickup və delivery ünvanlarını saxla
  - Qiyməti hesabla
  - Mövcud kuryer tap (Courier Service-ə sorğu)
  - Kuryer varsa: status = ASSIGNED + courierId
  - Kuryer yoxdursa: status = REJECTED
- Sifariş statusunu yenilə (`PATCH /orders/{id}/deliver`)
  - Yalnız ASSIGNED statusunda olan sifariş DELIVERED edilə bilər
- Sifariş məlumatlarını gör (`GET /orders/{id}`)
- Hadisələr (Events):
  - ORDER_CREATED
  - ORDER_ASSIGNED
  - ORDER_DELIVERED

## 🚀 Hazırda implementasiya olunub (Current PR)

Bu Pull Request-də aşağıdakılar tamamlanıb:

### ✅ Entity & Database
- `Order` entity (JPA + Lombok)
- Liquibase changelog ilə `orders` cədvəli
- PostgreSQL əlaqəsi

### ✅ DTO və Mapper
- `OrderCreateRequest` (pickupAddress, deliveryAddress)
- `OrderResponse` (id, status, price, courierId, createdAt, updatedAt)
- `OrderMapper` (toEntity, toResponse)

### ✅ Repository & Service
- CRUD əməliyyatları
- `createOrder` – qiymət hesablanması (sabit 10.00)
- `getOrderById` – order tapılmadıqda `OrderNotFoundException`
- `updateStatusToDelivered` – status keçid validasiyası

### ✅ Exception Handling
- `OrderNotFoundException` → 404
- `InvalidOrderStatusException` → 400
- `GlobalExceptionHandler` (`@RestControllerAdvice`)

### ✅ Controller
- `POST /orders` – sifariş yarat
- `GET /orders/{id}` – sifariş məlumatları
- `GET /orders` – bütün sifarişlər (filter olmadan)
- `PATCH /orders/{id}/deliver` – statusu DELIVERED et

## ⚠️ Hələ implementasiya olunmayıb (Next Steps)

- [ ] Courier Service ilə sinxron əlaqə (Feign Client) – real courier assign
- [ ] RabbitMQ event-ləri (ORDER_CREATED, ORDER_ASSIGNED, ORDER_DELIVERED)
- [ ] `calculatePrice` – real məntiq (məsafə, tarif)
- [ ] Unit və integration testlər

## 🛠️ Texnologiyalar

- Java 21
- Spring Boot 4.0.6
- Spring Data JPA
- PostgreSQL
- Liquibase
- Lombok

## 🐳 Docker ilə işə salmaq

```bash
# PostgreSQL konteyneri
docker run -d --name postgres-order \
  -e POSTGRES_DB=order_db \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=1234 \
  -p 5432:5432 postgres:15

# Tətbiqi işə salmaq
./gradlew bootRun
