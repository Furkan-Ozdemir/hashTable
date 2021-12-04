
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

@SuppressWarnings("unchecked")
public abstract class HashTableOpenAddressingBase<K, V> {

    protected double loadFactor;
    protected int threshold, modificationCount;
    protected static int capacity, collisionCount = 0;

    // 'usedBuckets' counts the total number of used buckets inside the
    // hash-table 'keyCount' tracks the number of unique keys currently inside the
    // hash-table.
    protected int usedBuckets, keyCount;

    // These arrays store the key-value pairs.
    protected K[] keys;
    protected LinkedList<Entry>[] values;

    // Special marker token used to indicate the deletion of a key-value pair
    // Equivalent of "Available" in classroom presentations
    protected final K TOMBSTONE = (K) (new Object());

    private static final int DEFAULT_CAPACITY = 7;

    // Edit this to be .5 and .8 for search.txt
    private static final double DEFAULT_LOAD_FACTOR = 0.8;

    protected HashTableOpenAddressingBase() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    protected HashTableOpenAddressingBase(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    protected HashTableOpenAddressingBase(int capacity, double loadFactor) {
        if (capacity <= 0)
            throw new IllegalArgumentException("Illegal capacity: " + capacity);

        if (loadFactor <= 0 || Double.isNaN(loadFactor) || Double.isInfinite(loadFactor))
            throw new IllegalArgumentException("Illegal loadFactor: " + loadFactor);

        this.loadFactor = loadFactor;
        HashTableOpenAddressingBase.capacity = Math.max(DEFAULT_CAPACITY, capacity);
        // adjustCapacity();
        threshold = (int) (HashTableOpenAddressingBase.capacity * loadFactor);

        keys = (K[]) new Object[HashTableOpenAddressingBase.capacity];
        values = new LinkedList[HashTableOpenAddressingBase.capacity];
    }

    protected abstract int doubleHash(int firstHash, int value, int timesHashed);

    protected abstract int linearProbe(int index);

    // Increases the capacity of the hash table.
    protected void increaseCapacity() {
        capacity = (2 * capacity) + 1;
    }

    public static int getCollisionCount() {
        return collisionCount;
    }

    // Returns the number of keys currently inside the hash-table
    public int size() {
        return keyCount;
    }

    // Returns the capacity of the hashtable
    public static int getCapacity() {
        return capacity;
    }

    // Returns true/false depending on whether the hash-table is empty
    public boolean isEmpty() {
        return keyCount == 0;
    }

    // Returns a list of keys found in the hash table
    public List<K> keys() {
        List<K> hashtableKeys = new ArrayList<>(size());
        for (int i = 0; i < capacity; i++)
            if (keys[i] != null && keys[i] != TOMBSTONE)
                hashtableKeys.add(keys[i]);
        return hashtableKeys;
    }

    // Returns a list of non-unique values found in the hash table
    public List<Entry> values() {
        List<Entry> hashtableValues = new ArrayList<>(size());
        for (int i = 0; i < capacity; i++)
            if (keys[i] != null && keys[i] != TOMBSTONE)
                hashtableValues.add(values[i].get(i));
        return hashtableValues;
    }

    // Double the size of the hash-table
    protected void resizeTable() {
        increaseCapacity();

        threshold = (int) (capacity * loadFactor);

        K[] oldKeyTable = (K[]) new Object[capacity];
        LinkedList<Entry>[] oldValueTable = new LinkedList[capacity];

        // Swappings for keys
        K[] keyTableTmp = keys;
        keys = oldKeyTable;
        oldKeyTable = keyTableTmp;

        // Swappings for values
        LinkedList<Entry>[] valueTableTmp = values;
        values = oldValueTable;
        oldValueTable = valueTableTmp;

        // reset key count so it won't double, since we are going to re-insert the
        // elements
        keyCount = usedBuckets = 0;

        for (int i = 0; i < oldKeyTable.length; i++) {
            if (oldKeyTable[i] != null && oldKeyTable[i] != TOMBSTONE) {
                Iterator<Entry> iter = oldValueTable[i].iterator();
                while (iter.hasNext()) {
                    insert(oldKeyTable[i], iter.next());
                }

            }
            oldValueTable[i] = null;
            oldKeyTable[i] = null;
        }
    }

    // [0, capacity)
    protected final int normalizeIndex(int keyHash) {
        return Math.abs(keyHash % capacity);
    }

    /*
     * Aynı key varsa: fileName'leri karşılaştır, fileNameler aynı ise sadece
     * count'ı arttır. FileName farklı ise yeniden ekle. Şu anda aynı fileları
     * tekrar ekliyor, farklı name olsa bile count arttırılmış oluyor.
     */
    public Entry insert(K key, Entry val) {
        if (key == null)
            throw new IllegalArgumentException("Null key");
        if (usedBuckets >= threshold)
            resizeTable();

        final int offset = normalizeIndex(HashTableDoubleHashing.hashCodeSSF(key));

        for (int i = offset, j = -1, timesHashed = 1;; i = normalizeIndex(
                offset + doubleHash(offset, HashTableDoubleHashing.hashCodeSSF(key), timesHashed++))) {

            // The current slot was previously deleted
            if (keys[i] == TOMBSTONE) {
                if (j == -1)
                    j = i;

                // The current cell already contains a key

            } else if (keys[i] != null) {
                // The key we're trying to insert already exists in the hash-table,
                // keyler eşit ise valueların fileName'lerini karşılaştır
                // fileName farklı ise ekle, aynı ise countu arttır.
                if (keys[i].equals(key)) {

                    for (Entry entry : values[i]) {
                        if (entry.getFileName().equals(val.getFileName())) {
                            entry.incrementCount();
                            return entry;
                        }
                    }
                    if (j == -1) {
                        values[i].add(val);
                        keys[i] = key;
                        keyCount++;
                    } else {
                        keys[i] = TOMBSTONE;
                        values[i] = null;
                        keys[j] = key;
                        values[j].add(val);
                        keyCount++;

                    }
                    modificationCount++;
                    return values[i].getFirst(); // burası yanlış ama etkilemiyor.

                }

                // Current cell is null so an insertion/update can occur
            } else {
                // No previously encountered deleted buckets
                if (j == -1) {
                    usedBuckets++;
                    keyCount++;
                    keys[i] = key;
                    if (values[i] == null) {
                        values[i] = new LinkedList<Entry>();
                    }
                    values[i].add(val);

                    // Previously seen deleted bucket. Instead of inserting
                    // the new element at i where the null element is insert
                    // it where the deleted token was found.
                } else {
                    keyCount++;
                    keys[j] = key;
                    values[j].add(val);
                }

                modificationCount++;
                return null;
            }
            collisionCount += timesHashed;
        }
    }

    // Get the value associated with the input key.
    public void get(K key) {
        if (key == null)
            throw new IllegalArgumentException("Null key");

        final int offset = normalizeIndex(HashTableDoubleHashing.hashCodeSSF(key));

        // Start at the original hash value and probe until we find a spot where our key
        // is
        for (int i = offset, j = -1, timesHashed = 1;; i = normalizeIndex(
                offset + doubleHash(offset, HashTableDoubleHashing.hashCodeSSF(key), timesHashed++))) {

            if (keys[i] == TOMBSTONE) {

                if (j == -1)
                    j = i;

            } else if (keys[i] != null) {

                // The key we want is in the hash-table!
                if (keys[i].equals(key)) {

                    if (j != -1) {

                        keys[j] = keys[i];
                        values[j] = values[i];
                        keys[i] = TOMBSTONE;
                        values[i] = null;

                        for (Entry entry : values[j]) {
                            System.out.println(key + "->" + entry.getFileName() + " " + entry.getCount());
                        }
                        break;

                    } else {

                        for (Entry entry : values[i]) {
                            System.out.println(key + "->" + entry.getFileName() + " " + entry.getCount());
                        }
                        break;

                    }
                }

                // Element was not found in the hash-table :/
            } else {
                System.out.println(key + " is not found!");
                break;
            }
        }
    }

    // Removes a key from the map and returns the value.
    // returns null if the value is null AND also returns
    // null if the key does not exists.
    public K remove(K key) {
        if (key == null)
            throw new IllegalArgumentException("Null key");

        final int offset = normalizeIndex(HashTableDoubleHashing.hashCodeSSF(key));

        for (int i = offset, timesHashed = 1;; i = normalizeIndex(
                offset + doubleHash(offset, HashTableDoubleHashing.hashCodeSSF(key), timesHashed++))) {

            // Ignore deleted cells
            if (keys[i] == TOMBSTONE)
                continue;

            // Key was not found in hash-table.
            if (keys[i] == null)
                return null;

            // The key we want to remove is in the hash-table!
            if (keys[i].equals(key)) {
                keyCount--;
                modificationCount++;
                // Entry oldValue = values[i];
                K deletedKey = keys[i];
                values[i] = null;
                keys[i] = TOMBSTONE;
                values[i] = null;
                return deletedKey;
            }
        }
    }

    // Return a String view of this hash-table.
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        StringBuilder valuesString;

        sb.append("{");
        for (int i = 0; i < capacity; i++)
            if (keys[i] != null && keys[i] != TOMBSTONE) {
                valuesString = new StringBuilder();

                for (Entry value : values[i]) {

                    valuesString.append(value.getFileName() + " " + value.getCount() + " , ");
                }
                sb.append(keys[i] + " => " + valuesString.toString() + ", ");
                valuesString = null;
            }
        sb.append("}");

        return sb.toString();
    }

}