/**
 * An implementation of a hash-table using open addressing with linear probing
 * as a collision resolution method.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */

public class HashTableLinearProbing<K, V> extends HashTableOpenAddressingBase<K, V> {
    // TODO Linear probing için linear probe functionu yap

    // This is the linear constant used in the linear probing, it can be
    // any positive number. The table capacity will be adjusted so that
    // the GCD(capacity, LINEAR_CONSTANT) = 1 so that all buckets can be probed.
    private static final int LINEAR_CONSTANT = 17;

    public HashTableLinearProbing() {
        super();
    }

    public HashTableLinearProbing(int capacity) {
        super(capacity);
    }

    public HashTableLinearProbing(int capacity, double loadFactor) {
        super(capacity, loadFactor);
    }

    // no need to implement it
    @Override
    protected int doubleHash(int firstHash, int value, int timesHashed) {
        return 0;
    }
    // @Override
    // protected int probe(int x) {
    // // return LINEAR_CONSTANT * x;
    // return 0;
    // }

    // Adjust the capacity so that the linear constant and
    // the table capacity are relatively prime.
    @Override
    protected void adjustCapacity() {
        while (gcd(LINEAR_CONSTANT, capacity) != 1) {
            capacity++;
        }
    }
}