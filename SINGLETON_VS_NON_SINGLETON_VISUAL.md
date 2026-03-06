# 📊 Visual Comparison: Singleton vs Non-Singleton

## Scenario: 3 concurrent booking requests

### ❌ WITHOUT Singleton Pattern (NonSingletonBookingIdGenerator)

```
┌─────────────────────────────────────────────────────────────────────┐
│                        TIME FLOW →                                   │
├─────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  Request 1 (Thread 1)                                                │
│  ├─→ new NonSingletonBookingIdGenerator()                            │
│  │   └─→ Instance A created (counter = 0)                            │
│  └─→ generateId()                                                    │
│      └─→ counter++ = 1                                               │
│      └─→ return "BK-000001" ✓                                        │
│                                                                      │
│  Request 2 (Thread 2)                                                │
│  ├─→ new NonSingletonBookingIdGenerator()                            │
│  │   └─→ Instance B created (counter = 0)  ← DIFFERENT INSTANCE!    │
│  └─→ generateId()                                                    │
│      └─→ counter++ = 1                                               │
│      └─→ return "BK-000001" ❌ DUPLICATE!                            │
│                                                                      │
│  Request 3 (Thread 3)                                                │
│  ├─→ new NonSingletonBookingIdGenerator()                            │
│  │   └─→ Instance C created (counter = 0)  ← DIFFERENT INSTANCE!    │
│  └─→ generateId()                                                    │
│      └─→ counter++ = 1                                               │
│      └─→ return "BK-000001" ❌ DUPLICATE!                            │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘

RESULT: 3 bookings → 3 SAME IDs = "BK-000001" 🐛

┌──────────────┬──────────────┬──────────────┐
│  Instance A  │  Instance B  │  Instance C  │
│  counter = 1 │  counter = 1 │  counter = 1 │
└──────────────┴──────────────┴──────────────┘
       ↓              ↓              ↓
   BK-000001      BK-000001      BK-000001
               DUPLICATES! ❌
```

### ✅ WITH Singleton Pattern (BookingIdGenerator)

```
┌─────────────────────────────────────────────────────────────────────┐
│                        TIME FLOW →                                   │
├─────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  Request 1 (Thread 1)                                                │
│  ├─→ BookingIdGenerator.getInstance()                                │
│  │   └─→ First call: Create instance (counter = 0)                   │
│  │   └─→ Return SHARED instance                                      │
│  └─→ synchronized generateId()                                       │
│      ├─→ 🔒 Lock acquired                                            │
│      ├─→ counter++ = 1                                               │
│      ├─→ return "BK-000001" ✓                                        │
│      └─→ 🔓 Lock released                                            │
│                                                                      │
│  Request 2 (Thread 2)                                                │
│  ├─→ BookingIdGenerator.getInstance()                                │
│  │   └─→ Return SAME instance ← SHARED!                              │
│  └─→ synchronized generateId()                                       │
│      ├─→ ⏳ Wait for Thread 1 to release lock...                     │
│      ├─→ 🔒 Lock acquired                                            │
│      ├─→ counter++ = 2                                               │
│      ├─→ return "BK-000002" ✓                                        │
│      └─→ 🔓 Lock released                                            │
│                                                                      │
│  Request 3 (Thread 3)                                                │
│  ├─→ BookingIdGenerator.getInstance()                                │
│  │   └─→ Return SAME instance ← SHARED!                              │
│  └─→ synchronized generateId()                                       │
│      ├─→ ⏳ Wait for Thread 2 to release lock...                     │
│      ├─→ 🔒 Lock acquired                                            │
│      ├─→ counter++ = 3                                               │
│      ├─→ return "BK-000003" ✓                                        │
│      └─→ 🔓 Lock released                                            │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘

RESULT: 3 bookings → 3 UNIQUE IDs ✅

         ┌──────────────────┐
         │  Single Instance │
         │   counter = 3    │
         └──────────────────┘
                  ↓
    ┌─────────────┼─────────────┐
    ↓             ↓             ↓
BK-000001     BK-000002     BK-000003
           ALL UNIQUE! ✅
```

---

## Memory Visualization

### ❌ Non-Singleton: Multiple Instances in Heap

```
┌─────────────────────────────────────────────────────────────┐
│                         HEAP MEMORY                          │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  ┌───────────────────┐  ┌───────────────────┐               │
│  │   Instance A      │  │   Instance B      │               │
│  │   @0x1A2B         │  │   @0x2C3D         │               │
│  ├───────────────────┤  ├───────────────────┤               │
│  │ counter = 1       │  │ counter = 1       │               │
│  └───────────────────┘  └───────────────────┘               │
│            ↑                      ↑                          │
│            │                      │                          │
│       Thread 1               Thread 2                        │
│                                                              │
│       ┌───────────────────┐                                  │
│       │   Instance C      │                                  │
│       │   @0x3E4F         │                                  │
│       ├───────────────────┤                                  │
│       │ counter = 1       │                                  │
│       └───────────────────┘                                  │
│                 ↑                                            │
│                 │                                            │
│            Thread 3                                          │
│                                                              │
│  Problem: 3 separate counters → Duplicate values!           │
└─────────────────────────────────────────────────────────────┘
```

### ✅ Singleton: Single Instance Shared by All Threads

```
┌─────────────────────────────────────────────────────────────┐
│                         HEAP MEMORY                          │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│               ┌─────────────────────────┐                    │
│               │  BookingIdGenerator     │                    │
│               │  Singleton Instance     │                    │
│               │  @0x1A2B                │                    │
│               ├─────────────────────────┤                    │
│               │  counter = 3            │                    │
│               └─────────────────────────┘                    │
│                          ↑                                   │
│                          │                                   │
│         ┌────────────────┼────────────────┐                  │
│         │                │                │                  │
│         │                │                │                  │
│     Thread 1         Thread 2         Thread 3               │
│         ↓                ↓                ↓                  │
│    "BK-000001"      "BK-000002"      "BK-000003"             │
│                                                              │
│  Solution: 1 shared counter → Unique values!                 │
└─────────────────────────────────────────────────────────────┘
```

---

## Code Flow Comparison

### ❌ Non-Singleton Flow

```
BookingService (Thread 1)
    │
    ├─→ new NonSingletonBookingIdGenerator()
    │       │
    │       └─→ Creates NEW instance with counter=0
    │
    └─→ generateId() → "BK-000001"


BookingService (Thread 2)
    │
    ├─→ new NonSingletonBookingIdGenerator()
    │       │
    │       └─→ Creates ANOTHER NEW instance with counter=0  ❌
    │
    └─→ generateId() → "BK-000001"  ❌ DUPLICATE!
```

### ✅ Singleton Flow

```
BookingService (Thread 1)
    │
    ├─→ BookingIdGenerator.getInstance()
    │       │
    │       ├─→ First call: Creates instance
    │       └─→ Returns instance (counter=0)
    │
    └─→ generateId() → "BK-000001" ✓


BookingService (Thread 2)
    │
    ├─→ BookingIdGenerator.getInstance()
    │       │
    │       └─→ Returns SAME instance (counter=1)  ✅
    │
    └─→ generateId() → "BK-000002" ✅ UNIQUE!
```

---

## Database Impact

### ❌ Without Singleton

```sql
-- Thread 1
INSERT INTO bookings (booking_code, ...) VALUES ('BK-000001', ...);
✓ Success

-- Thread 2
INSERT INTO bookings (booking_code, ...) VALUES ('BK-000001', ...);
❌ ERROR: Duplicate key violation!
-- OR worse: Success if no unique constraint → Data corruption!

-- Thread 3
INSERT INTO bookings (booking_code, ...) VALUES ('BK-000001', ...);
❌ ERROR: Duplicate key violation!
```

### ✅ With Singleton

```sql
-- Thread 1
INSERT INTO bookings (booking_code, ...) VALUES ('BK-000001', ...);
✓ Success

-- Thread 2
INSERT INTO bookings (booking_code, ...) VALUES ('BK-000002', ...);
✓ Success

-- Thread 3
INSERT INTO bookings (booking_code, ...) VALUES ('BK-000003', ...);
✓ Success

-- All insertions succeed! No duplicates!
```

---

## Performance Comparison

### Metrics from Demo

```
┌──────────────────────┬─────────────────┬─────────────────┐
│      Metric          │  Non-Singleton  │    Singleton    │
├──────────────────────┼─────────────────┼─────────────────┤
│ Total IDs Generated  │       500       │       500       │
│ Unique IDs           │      ~50        │       500       │
│ Duplicate IDs        │      ~450       │        0        │
│ Duplicate Rate       │      90%        │        0%       │
│ DB Insert Failures   │      ~450       │        0        │
│ Success Rate         │      10%        │       100%      │
└──────────────────────┴─────────────────┴─────────────────┘

❌ Non-Singleton: 90% failure rate!
✅ Singleton: 100% success rate!
```

---

## Summary

| Aspect                | Non-Singleton ❌            | Singleton ✅    |
| --------------------- | --------------------------- | --------------- |
| **Instances**         | Multiple (1 per thread)     | Single (shared) |
| **Counter**           | Multiple (separate)         | Single (shared) |
| **Thread-Safe**       | No (even with synchronized) | Yes             |
| **Duplicates**        | Many (~90%)                 | None (0%)       |
| **DB Errors**         | High                        | None            |
| **Use in Production** | NEVER!                      | Always!         |

---

## Key Insight

```
The problem is NOT about thread-safety of the generateId() method.
The method IS synchronized.

The problem is about having MULTIPLE INSTANCES,
each with its OWN counter starting from 0.

Solution: SINGLETON ensures only ONE instance with ONE counter.
```

---

**Moral of the Story:**

- 🔴 Multiple instances = Multiple counters = Disaster
- 🟢 Single instance (Singleton) = Single counter = Success
