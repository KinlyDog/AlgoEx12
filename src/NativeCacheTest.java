import org.junit.jupiter.api.BeforeEach;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class NativeCacheTest {
    NativeCache<String> nc = new NativeCache(10, String.class);

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 10; i++) {
            nc.put(String.valueOf(i), String.valueOf(i));
        }
    }

    @org.junit.jupiter.api.Test
    void put() {
        nc.put("123", "123");
        assertTrue(nc.find("123") == 0);
    }

    @org.junit.jupiter.api.Test
    void get() {
        int[] hits = new int[10];

        Random rand = new Random();

        for (int i = 0; i < 100; i++) {
            int r = rand.nextInt(10);

            nc.get(String.valueOf(r));

            r++;
            if (r == 10) r = 0;
            hits[r]++;
        }

        int min = hits[0];
        int minInd = 0;

        for (int i = 1; i < 10; i++) {
            if (hits[i] < min) {
                min = hits[i];
                minInd = i;
            }
        }

        nc.put("123", "123");

        assertTrue(nc.find("123") == minInd);

    }


}