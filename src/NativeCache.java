import java.lang.reflect.Array;

public class NativeCache<T> {
    public int size;
    public String[] slots;
    public T[] values;
    public int[] hits;

    public NativeCache(int size, Class clazz) {
        this.size = size;
        slots = new String[this.size];
        values = (T[]) Array.newInstance(clazz, this.size);
        hits = new int[this.size];
    }

    public int hashFun(String key) {
        int len = key.getBytes().length;
        return len % size;
    }

    public int seekSlot(String key) {
        int ind = hashFun(key);

        if (find(key) != -1) {
            return find(key);
        }

        for (int i = 0; i < size; i++, ind++) {
            if (ind >= size) {
                ind -= size;
            }

            if (slots[ind] == null) {
                return ind;
            }
        }

        return -1;
    }

    public int find(String key) {
        int ind = hashFun(key);

        for (int i = 0; i < size; i++, ind++) {
            if (ind >= size) {
                ind -= size;
            }

            if (slots[ind] == null) {
                continue;
            }

            if (slots[ind].equals(key)) {
                return ind;
            }
        }

        return -1;
    }

    public boolean isKey(String key) {
        return find(key) != -1;
    }

    public void put(String key, T value) {
        int ind = seekSlot(key);

        if (ind == -1) {
            ind = oldDel();
        }

        slots[ind] = key;
        values[ind] = value;
        hits[ind] = 0;
    }

    public int oldDel() {
        int minInd = 0;
        int min = hits[0];

        for (int i = 1; i < size; i++) {
            if (hits[i] < min) {
                min = hits[i];
                minInd = i;
            }
        }

        return minInd;
    }

    public T get(String key) {
        int ind = find(key);

        if (ind == -1) return null;

        hits[ind]++;

        return values[ind];
    }
}




