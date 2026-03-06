package hcmute.edu.vn.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class để demonstrate và verify thread-safe behavior của BookingIdGenerator
 * 
 * Test này sẽ chứng minh:
 * 1. Singleton pattern: Chỉ có 1 instance duy nhất
 * 2. Thread-safe ID generation: Không có duplicate IDs khi nhiều threads chạy đồng thời
 * 3. Atomicity: Mỗi thread nhận được một ID duy nhất
 */
public class BookingIdGeneratorTest {

    @BeforeEach
    public void setUp() {
        // Reset counter trước mỗi test
        BookingIdGenerator.getInstance().resetCounter();
    }

    /**
     * Test 1: Verify Singleton Pattern
     * Kiểm tra rằng getInstance() luôn trả về cùng một instance
     */
    @Test
    public void testSingletonPattern() {
        BookingIdGenerator instance1 = BookingIdGenerator.getInstance();
        BookingIdGenerator instance2 = BookingIdGenerator.getInstance();
        
        // Verify cùng một instance
        assertSame(instance1, instance2, 
                "getInstance() phải luôn trả về cùng một instance");
        
        System.out.println("✓ Singleton Pattern: PASSED");
    }

    /**
     * Test 2: Basic ID Generation
     * Kiểm tra format và tính tăng dần của IDs
     */
    @Test
    public void testBasicIdGeneration() {
        BookingIdGenerator generator = BookingIdGenerator.getInstance();
        
        String id1 = generator.generateId();
        String id2 = generator.generateId();
        String id3 = generator.generateId();
        
        assertEquals("BK-000001", id1);
        assertEquals("BK-000002", id2);
        assertEquals("BK-000003", id3);
        
        System.out.println("✓ Basic ID Generation: PASSED");
        System.out.println("  Generated IDs: " + id1 + ", " + id2 + ", " + id3);
    }

    /**
     * Test 3: Thread-Safe với Multiple Threads
     * 
     * Scenario:
     * - Tạo 100 threads
     * - Mỗi thread generate 10 IDs
     * - Tổng cộng: 1000 IDs
     * 
     * Expected:
     * - Tất cả 1000 IDs phải unique (không duplicate)
     * - Counter cuối cùng phải = 1000
     */
    @Test
    public void testThreadSafetyWithMultipleThreads() throws InterruptedException {
        final int NUM_THREADS = 100;
        final int IDS_PER_THREAD = 10;
        final int TOTAL_IDS = NUM_THREADS * IDS_PER_THREAD;
        
        // ConcurrentHashMap để lưu tất cả IDs từ các threads
        Set<String> allGeneratedIds = ConcurrentHashMap.newKeySet();
        
        // CountDownLatch để đồng bộ tất cả threads start cùng lúc
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch completionLatch = new CountDownLatch(NUM_THREADS);
        
        BookingIdGenerator generator = BookingIdGenerator.getInstance();
        
        // Tạo và start 100 threads
        for (int i = 0; i < NUM_THREADS; i++) {
            new Thread(() -> {
                try {
                    // Đợi cho signal start
                    startLatch.await();
                    
                    // Mỗi thread generate 10 IDs
                    for (int j = 0; j < IDS_PER_THREAD; j++) {
                        String id = generator.generateId();
                        allGeneratedIds.add(id);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    completionLatch.countDown();
                }
            }).start();
        }
        
        System.out.println("\n🚀 Starting " + NUM_THREADS + " threads simultaneously...");
        
        // Signal tất cả threads start
        startLatch.countDown();
        
        // Đợi tất cả threads hoàn thành
        completionLatch.await();
        
        System.out.println("✓ All threads completed");
        
        // Verify results
        assertEquals(TOTAL_IDS, allGeneratedIds.size(), 
                "Tất cả IDs phải unique - không có duplicate");
        
        assertEquals(TOTAL_IDS, generator.getCounter(), 
                "Counter phải chính xác = số lượng IDs đã generate");
        
        System.out.println("✓ Thread-Safety Test: PASSED");
        System.out.println("  Total unique IDs: " + allGeneratedIds.size() + " / " + TOTAL_IDS);
        System.out.println("  No duplicates detected!");
        System.out.println("  Final counter: " + generator.getCounter());
    }

    /**
     * Test 4: Stress Test - Extreme Concurrency
     * 
     * Test với số lượng threads và IDs lớn hơn để verify performance
     * và thread-safety trong điều kiện extreme
     */
    @Test
    public void testStressTestWithHighConcurrency() throws InterruptedException {
        final int NUM_THREADS = 500;
        final int IDS_PER_THREAD = 20;
        final int TOTAL_IDS = NUM_THREADS * IDS_PER_THREAD;
        
        Set<String> allGeneratedIds = ConcurrentHashMap.newKeySet();
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch completionLatch = new CountDownLatch(NUM_THREADS);
        
        BookingIdGenerator generator = BookingIdGenerator.getInstance();
        
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < NUM_THREADS; i++) {
            new Thread(() -> {
                try {
                    startLatch.await();
                    for (int j = 0; j < IDS_PER_THREAD; j++) {
                        String id = generator.generateId();
                        allGeneratedIds.add(id);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    completionLatch.countDown();
                }
            }).start();
        }
        
        System.out.println("\n🔥 STRESS TEST: " + NUM_THREADS + " threads generating " + TOTAL_IDS + " IDs...");
        startLatch.countDown();
        completionLatch.await();
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        assertEquals(TOTAL_IDS, allGeneratedIds.size());
        assertEquals(TOTAL_IDS, generator.getCounter());
        
        System.out.println("✓ Stress Test: PASSED");
        System.out.println("  Time taken: " + duration + "ms");
        System.out.println("  IDs per second: " + (TOTAL_IDS * 1000 / duration));
        System.out.println("  All " + TOTAL_IDS + " IDs are unique!");
    }

    /**
     * Test 5: Demonstrate Race Condition (WITHOUT synchronized)
     * 
     * Đây là ví dụ để demonstrate điều gì sẽ xảy ra nếu KHÔNG có thread-safe
     * (Chỉ để học tập, không phải actual test)
     */
    @Test
    public void demonstrateWhyWeNeedThreadSafe() {
        System.out.println("\n📚 DEMONSTRATION: Why we need Thread-Safe?");
        System.out.println("--------------------------------------------");
        System.out.println("Nếu KHÔNG có synchronized và volatile:");
        System.out.println("1. Race Condition: 2 threads cùng đọc counter = 100");
        System.out.println("   - Thread A: đọc 100 → tính 101 → ghi 101");
        System.out.println("   - Thread B: đọc 100 → tính 101 → ghi 101");
        System.out.println("   → KẾT QUẢ: Duplicate ID = BK-101 (BUG!)");
        System.out.println();
        System.out.println("Với synchronized và volatile:");
        System.out.println("   - Thread A: lock → đọc 100 → ghi 101 → unlock");
        System.out.println("   - Thread B: đợi → lock → đọc 101 → ghi 102 → unlock");
        System.out.println("   → KẾT QUẢ: Unique IDs = BK-101, BK-102 ✓");
    }

    /**
     * Test 6: Verify getInstance() thread-safety
     * Đảm bảo chỉ 1 instance được tạo ra ngay cả khi nhiều threads 
     * gọi getInstance() cùng lúc
     */
    @Test
    public void testGetInstanceThreadSafety() throws InterruptedException {
        final int NUM_THREADS = 100;
        Set<BookingIdGenerator> instances = ConcurrentHashMap.newKeySet();
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch completionLatch = new CountDownLatch(NUM_THREADS);
        
        // Tạo nhiều threads cùng gọi getInstance()
        for (int i = 0; i < NUM_THREADS; i++) {
            new Thread(() -> {
                try {
                    startLatch.await();
                    BookingIdGenerator instance = BookingIdGenerator.getInstance();
                    instances.add(instance);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    completionLatch.countDown();
                }
            }).start();
        }
        
        startLatch.countDown();
        completionLatch.await();
        
        // Verify chỉ có 1 instance duy nhất
        assertEquals(1, instances.size(), 
                "Chỉ nên có 1 instance duy nhất dù có " + NUM_THREADS + " threads");
        
        System.out.println("✓ getInstance() Thread-Safety: PASSED");
        System.out.println("  " + NUM_THREADS + " threads → 1 unique instance");
    }

    /**
     * Test 7: Demonstrate Non-Singleton Problem
     * Test này CHỨNG MINH vấn đề khi KHÔNG dùng Singleton
     * Test này SẼ FAIL để demonstrate duplicate IDs
     */
    @Test
    public void testNonSingletonProblem() {
        System.out.println("\n❌ DEMONSTRATION: Non-Singleton Problem");
        System.out.println("--------------------------------------------");
        
        // Tạo 3 instances khác nhau (không phải singleton)
        NonSingletonBookingIdGenerator gen1 = new NonSingletonBookingIdGenerator();
        NonSingletonBookingIdGenerator gen2 = new NonSingletonBookingIdGenerator();
        NonSingletonBookingIdGenerator gen3 = new NonSingletonBookingIdGenerator();
        
        // Mỗi generator tạo IDs
        String id1 = gen1.generateId();
        String id2 = gen2.generateId();
        String id3 = gen3.generateId();
        
        System.out.println("Generator 1: " + id1);
        System.out.println("Generator 2: " + id2);
        System.out.println("Generator 3: " + id3);
        
        // Verify: Tất cả đều trùng nhau (do mỗi instance có counter = 0)
        assertEquals(id1, id2, "IDs từ 2 instances khác nhau SẼ TRÙNG NHAU!");
        assertEquals(id2, id3, "IDs từ 2 instances khác nhau SẼ TRÙNG NHAU!");
        
        System.out.println("❌ Problem detected: Tất cả đều = " + id1);
        System.out.println("💡 Solution: Use Singleton Pattern!");
    }

    /**
     * Test 8: Compare Singleton vs Non-Singleton với Concurrent Threads
     * Demonstrate sự khác biệt rõ ràng
     */
    @Test
    public void testComparisonSingletonVsNonSingleton() throws InterruptedException {
        final int NUM_THREADS = 50;
        final int IDS_PER_THREAD = 10;
        final int TOTAL_IDS = NUM_THREADS * IDS_PER_THREAD;
        
        System.out.println("\n📊 COMPARISON: Singleton vs Non-Singleton");
        System.out.println("═══════════════════════════════════════════════════════");
        
        // Test 1: Non-Singleton
        System.out.println("\n❌ Test với Non-Singleton (mỗi thread tạo instance riêng):");
        Set<String> nonSingletonIds = ConcurrentHashMap.newKeySet();
        CountDownLatch latch1 = new CountDownLatch(NUM_THREADS);
        
        for (int i = 0; i < NUM_THREADS; i++) {
            new Thread(() -> {
                // Mỗi thread tạo instance RIÊNG
                NonSingletonBookingIdGenerator generator = new NonSingletonBookingIdGenerator();
                for (int j = 0; j < IDS_PER_THREAD; j++) {
                    nonSingletonIds.add(generator.generateId());
                }
                latch1.countDown();
            }).start();
        }
        latch1.await();
        
        int nonSingletonDuplicates = TOTAL_IDS - nonSingletonIds.size();
        System.out.println("  Total IDs generated: " + TOTAL_IDS);
        System.out.println("  Unique IDs: " + nonSingletonIds.size());
        System.out.println("  Duplicates: " + nonSingletonDuplicates);
        
        // Test 2: Singleton
        System.out.println("\n✅ Test với Singleton (tất cả threads dùng chung 1 instance):");
        BookingIdGenerator.getInstance().resetCounter();
        Set<String> singletonIds = ConcurrentHashMap.newKeySet();
        CountDownLatch latch2 = new CountDownLatch(NUM_THREADS);
        
        for (int i = 0; i < NUM_THREADS; i++) {
            new Thread(() -> {
                // Tất cả threads dùng CÙNG instance
                BookingIdGenerator generator = BookingIdGenerator.getInstance();
                for (int j = 0; j < IDS_PER_THREAD; j++) {
                    singletonIds.add(generator.generateId());
                }
                latch2.countDown();
            }).start();
        }
        latch2.await();
        
        int singletonDuplicates = TOTAL_IDS - singletonIds.size();
        System.out.println("  Total IDs generated: " + TOTAL_IDS);
        System.out.println("  Unique IDs: " + singletonIds.size());
        System.out.println("  Duplicates: " + singletonDuplicates);
        
        // Assertions
        assertTrue(nonSingletonDuplicates > 0, 
                "Non-Singleton PHẢI có duplicates");
        assertEquals(0, singletonDuplicates, 
                "Singleton KHÔNG được có duplicates");
        
        System.out.println("\n📊 RESULT:");
        System.out.println("  ❌ Non-Singleton: " + nonSingletonDuplicates + " duplicates");
        System.out.println("  ✅ Singleton: " + singletonDuplicates + " duplicates");
        System.out.println("\n💡 Singleton Pattern giải quyết vấn đề duplicate IDs!");
    }
}

