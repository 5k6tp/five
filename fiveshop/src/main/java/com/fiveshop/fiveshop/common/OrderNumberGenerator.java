package com.fiveshop.fiveshop.common;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class OrderNumberGenerator {
    private static final Set<Long> generatedOrderNumbers = new HashSet<>();
    private static final Random random = new Random();

    // 生成唯一的訂單編號
    public static long generateOrderNumber() {
        long orderNumber;
        do {
            // 生成以 5 開頭的 8 位訂單編號
            orderNumber = 50000000 + random.nextInt(10000000); // 產生範圍在 50000000 到 59999999
        } while (generatedOrderNumbers.contains(orderNumber)); // 確保唯一性
        generatedOrderNumbers.add(orderNumber);
        return orderNumber;
    }

    public static void main(String[] args) {
        // 測試生成 10 個訂單編號
        for (int i = 0; i < 10; i++) {
            System.out.println("生成的訂單編號: " + generateOrderNumber());
        }
    }
}
