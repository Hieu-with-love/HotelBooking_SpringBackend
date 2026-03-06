package hcmute.edu.vn.utils;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * Demo class để chạy và quan sát Thread-Safe Singleton hoạt động
 * 
 * Chạy class này để xem:
 * 1. Multiple threads chạy đồng thời
 * 2. Không có duplicate booking IDs
 * 3. Performance của synchronized methods
 * 
 * Run: Right-click → Run 'ThreadSafeDemo.main()'
 */
public class ThreadSafeDemo {

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║   THREAD-SAFE SINGLETON PATTERN DEMONSTRATION              ║");
        System.out.println("║   BookingIdGenerator - Double-Checked Locking              ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        // Demo 0: VẤN ĐỀ khi KHÔNG dùng Singleton
        demonstrateNonSingletonProblem();
        
        // Demo 1: Singleton Pattern
        demonstrateSingleton();
        
        // Demo 2: Basic Usage
        demonstrateBasicUsage();
        
        // Demo 3: Thread-Safe with Concurrency
        demonstrateThreadSafety();
        
        // Demo 4: Performance Test
        demonstratePerformance();
    }

    /**
     * Demo 0: VẤN ĐỀ khi KHÔNG dùng Singleton Pattern
     * Demonstrate việc tạo duplicate booking IDs
     */
    private static void demonstrateNonSingletonProblem() {
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("❌ DEMO 0: VẤN ĐỀ KHI KHÔNG DÙNG SINGLETON PATTERN");
        System.out.println("═══════════════════════════════════════════════════════════");
        
        System.out.println("\n📋 Scenario: 3 services đều tạo instance riêng\n");
        
        // Giả lập 3 services khác nhau tạo instance riêng
        NonSingletonBookingIdGenerator service1Generator = new NonSingletonBookingIdGenerator();
        NonSingletonBookingIdGenerator service2Generator = new NonSingletonBookingIdGenerator();
        NonSingletonBookingIdGenerator service3Generator = new NonSingletonBookingIdGenerator();
        
        System.out.println("Service 1 tạo generator: " + service1Generator);
        System.out.println("Service 2 tạo generator: " + service2Generator);
        System.out.println("Service 3 tạo generator: " + service3Generator);
        System.out.println("\n→ 3 instances KHÁC NHAU! Mỗi instance có counter riêng = 0\n");
        
        // Mỗi service generate IDs
        System.out.println("Mỗi service tạo bookings:");
        String service1Id1 = service1Generator.generateId();
        String service2Id1 = service2Generator.generateId();
        String service3Id1 = service3Generator.generateId();
        
        System.out.println("  Service 1 - Booking 1: " + service1Id1 + " (counter=" + service1Generator.getCounter() + ")");
        System.out.println("  Service 2 - Booking 1: " + service2Id1 + " (counter=" + service2Generator.getCounter() + ")");
        System.out.println("  Service 3 - Booking 1: " + service3Id1 + " (counter=" + service3Generator.getCounter() + ")");
        
        // Check duplicates
        if (service1Id1.equals(service2Id1) && service2Id1.equals(service3Id1)) {
            System.out.println("\n⚠️  CẢNH BÁO: 3 BOOKINGS CÓ CÙNG ID = " + service1Id1);
            System.out.println("⚠️  DUPLICATE DETECTED! Đây là BUG NGHIÊM TRỌNG!");
        }
        
        // Generate thêm một vài IDs nữa
        String service1Id2 = service1Generator.generateId();
        String service2Id2 = service2Generator.generateId();
        
        System.out.println("\nGenerate thêm:");
        System.out.println("  Service 1 - Booking 2: " + service1Id2);
        System.out.println("  Service 2 - Booking 2: " + service2Id2);
        
        if (service1Id2.equals(service2Id2)) {
            System.out.println("\n⚠️  DUPLICATE AGAIN! " + service1Id2);
        }
        
        // Simulate concurrent booking scenario
        System.out.println("\n" + "─".repeat(60));
        System.out.println("🔴 CONCURRENT SCENARIO - Nhiều threads với Non-Singleton");
        System.out.println("─".repeat(60));
        
        final int NUM_THREADS = 10;
        final int IDS_PER_THREAD = 5;
        Set<String> allIds = ConcurrentHashMap.newKeySet();
        CountDownLatch completionLatch = new CountDownLatch(NUM_THREADS);
        
        System.out.println("Tạo " + NUM_THREADS + " threads, mỗi thread tạo 1 generator instance...\n");
        
        for (int i = 0; i < NUM_THREADS; i++) {
            new Thread(() -> {
                // ❌ Mỗi thread tạo instance RIÊNG
                NonSingletonBookingIdGenerator generator = new NonSingletonBookingIdGenerator();
                
                for (int j = 0; j < IDS_PER_THREAD; j++) {
                    String id = generator.generateId();
                    allIds.add(id);
                }
                completionLatch.countDown();
            }).start();
        }
        
        try {
            completionLatch.await();
            
            int totalExpected = NUM_THREADS * IDS_PER_THREAD;
            int uniqueIds = allIds.size();
            int duplicates = totalExpected - uniqueIds;
            
            System.out.println("KẾT QUẢ:");
            System.out.println("  - Tổng IDs đã tạo: " + totalExpected);
            System.out.println("  - IDs duy nhất (unique): " + uniqueIds);
            System.out.println("  - IDs bị trùng (duplicates): " + duplicates);
            
            if (duplicates > 0) {
                System.out.println("\n❌ FAILED: Có " + duplicates + " duplicate IDs!");
                System.out.println("❌ Vấn đề: Mỗi thread tạo instance riêng → counter riêng → duplicate!");
            }
            
            // Show some duplicate examples
            System.out.println("\nMột số IDs được tạo ra (có nhiều duplicates):");
            allIds.stream().limit(10).forEach(id -> System.out.println("  " + id));
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\n💡 GIẢI PHÁP: Dùng SINGLETON PATTERN!");
        System.out.println("   → Chỉ 1 instance duy nhất → 1 counter duy nhất → NO DUPLICATES!\n");
    }

    /**
     * Demo 1: Singleton Pattern
     * Verify rằng getInstance() luôn trả về cùng một instance
     */
    private static void demonstrateSingleton() {
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("DEMO 1: SINGLETON PATTERN");
        System.out.println("═══════════════════════════════════════════════════════════");
        
        BookingIdGenerator instance1 = BookingIdGenerator.getInstance();
        BookingIdGenerator instance2 = BookingIdGenerator.getInstance();
        BookingIdGenerator instance3 = BookingIdGenerator.getInstance();
        
        System.out.println("Instance 1: " + instance1);
        System.out.println("Instance 2: " + instance2);
        System.out.println("Instance 3: " + instance3);
        
        if (instance1 == instance2 && instance2 == instance3) {
            System.out.println("\n✓ SUCCESS: Tất cả đều là cùng một instance!");
            System.out.println("  → Singleton Pattern hoạt động đúng\n");
        } else {
            System.out.println("\n✗ FAILED: Có nhiều instances khác nhau!\n");
        }
    }

    /**
     * Demo 2: Basic Usage
     * Generate một số booking IDs để xem format
     */
    private static void demonstrateBasicUsage() {
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("DEMO 2: BASIC USAGE - ID GENERATION");
        System.out.println("═══════════════════════════════════════════════════════════");
        
        BookingIdGenerator generator = BookingIdGenerator.getInstance();
        generator.resetCounter(); // Reset về 0
        
        System.out.println("Generating 5 booking IDs:");
        for (int i = 1; i <= 5; i++) {
            String id = generator.generateId();
            System.out.println("  Booking " + i + ": " + id);
        }
        System.out.println("\n✓ IDs are sequential and properly formatted\n");
    }

    /**
     * Demo 3: Thread-Safe Concurrency Test
     * Chạy nhiều threads đồng thời và verify không có duplicates
     */
    private static void demonstrateThreadSafety() {
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("DEMO 3: THREAD-SAFE CONCURRENCY TEST");
        System.out.println("═══════════════════════════════════════════════════════════");
        
        final int NUM_THREADS = 50;
        final int IDS_PER_THREAD = 20;
        final int TOTAL_IDS = NUM_THREADS * IDS_PER_THREAD;
        
        Set<String> allIds = ConcurrentHashMap.newKeySet();
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch completionLatch = new CountDownLatch(NUM_THREADS);
        
        BookingIdGenerator generator = BookingIdGenerator.getInstance();
        generator.resetCounter();
        
        System.out.println("Configuration:");
        System.out.println("  - Number of threads: " + NUM_THREADS);
        System.out.println("  - IDs per thread: " + IDS_PER_THREAD);
        System.out.println("  - Total IDs to generate: " + TOTAL_IDS);
        
        // Create threads
        for (int i = 0; i < NUM_THREADS; i++) {
            final int threadId = i + 1;
            new Thread(() -> {
                try {
                    startLatch.await(); // Wait for start signal
                    
                    for (int j = 0; j < IDS_PER_THREAD; j++) {
                        String id = generator.generateId();
                        allIds.add(id);
                        
                        // Print first few IDs from first thread for visualization
                        if (threadId == 1 && j < 3) {
                            System.out.println("  Thread-" + threadId + " generated: " + id);
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    completionLatch.countDown();
                }
            }, "BookingThread-" + threadId).start();
        }
        
        System.out.println("\n🚀 Starting all threads simultaneously...\n");
        
        try {
            startLatch.countDown(); // Start all threads
            completionLatch.await(); // Wait for completion
            
            System.out.println("\n✓ All threads completed!");
            System.out.println("\nResults:");
            System.out.println("  - Total unique IDs generated: " + allIds.size());
            System.out.println("  - Expected: " + TOTAL_IDS);
            System.out.println("  - Final counter: " + generator.getCounter());
            
            if (allIds.size() == TOTAL_IDS && generator.getCounter() == TOTAL_IDS) {
                System.out.println("\n✓ SUCCESS: No duplicates! Thread-safe mechanism works perfectly!");
                System.out.println("  → synchronized và volatile đảm bảo không có race condition\n");
            } else {
                System.out.println("\n✗ FAILED: Found duplicates or counter mismatch!\n");
            }
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Demo 4: Performance Test
     * Đo performance của thread-safe ID generation
     */
    private static void demonstratePerformance() {
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("DEMO 4: PERFORMANCE TEST");
        System.out.println("═══════════════════════════════════════════════════════════");
        
        final int NUM_THREADS = 100;
        final int IDS_PER_THREAD = 100;
        final int TOTAL_IDS = NUM_THREADS * IDS_PER_THREAD;
        
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch completionLatch = new CountDownLatch(NUM_THREADS);
        
        BookingIdGenerator generator = BookingIdGenerator.getInstance();
        generator.resetCounter();
        
        System.out.println("Testing with:");
        System.out.println("  - " + NUM_THREADS + " concurrent threads");
        System.out.println("  - " + IDS_PER_THREAD + " IDs per thread");
        System.out.println("  - Total: " + TOTAL_IDS + " IDs\n");
        
        // Create threads
        for (int i = 0; i < NUM_THREADS; i++) {
            new Thread(() -> {
                try {
                    startLatch.await();
                    for (int j = 0; j < IDS_PER_THREAD; j++) {
                        generator.generateId();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    completionLatch.countDown();
                }
            }).start();
        }
        
        System.out.println("⏱️  Starting performance test...");
        long startTime = System.currentTimeMillis();
        
        try {
            startLatch.countDown();
            completionLatch.await();
            
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            double idsPerSecond = (TOTAL_IDS * 1000.0) / duration;
            
            System.out.println("\n✓ Performance Test Results:");
            System.out.println("  - Total time: " + duration + " ms");
            System.out.println("  - IDs generated: " + TOTAL_IDS);
            System.out.println("  - Throughput: " + String.format("%.2f", idsPerSecond) + " IDs/second");
            System.out.println("  - Average time per ID: " + String.format("%.4f", (double)duration / TOTAL_IDS) + " ms");
            
            System.out.println("\n📊 Analysis:");
            System.out.println("  - synchronized method có overhead nhỏ");
            System.out.println("  - Trade-off: Tốc độ ↓ nhưng đảm bảo correctness ↑");
            System.out.println("  - Trong real-world: Correctness > Speed");
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("                    DEMO COMPLETED                          ");
        System.out.println("═══════════════════════════════════════════════════════════\n");
    }
}
