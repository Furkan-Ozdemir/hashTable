
/**
 * An implementation of a hash-table using open addressing with quadratic
 * probing as a collision resolution method.
 *
 * <p>
 * In this implementation we are using the following probing function: H(k, x) =
 * h(k) + f(x) mod 2^n
 *
 * <p>
 * Where h(k) is the hash for the given key, f(x) = (x + x^2) / 2 and n is a
 * natural number. We are using this probing function because it is guaranteed
 * to find an empty cell (i.e it generates all the numbers in the range [0, 2^n)
 * without repetition for the first 2^n numbers).
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
import java.util.ArrayList;

public class HashTableDoubleHashing<K, V> extends HashTableOpenAddressingBase<K, V> {
    private final int Q = 7;

    public HashTableDoubleHashing() {
        super();
    }

    public HashTableDoubleHashing(int capacity) {
        super(capacity);
    }

    // Designated constructor
    public HashTableDoubleHashing(int capacity, double loadFactor) {
        super(capacity, loadFactor);
    }

    // @Override
    // protected int probe(int prevHash) {
    // // double hashing function (x^2+x)/2
    // // h(k) -> bir önceki hash fonk. gelen değer
    // // d(k) yeni function
    // // k -> key
    // // N table size must be prime
    // // q random bi prime number
    // // int dk = Q -
    // return 0;// (x * x + x) >> 1;
    // }

    protected int doubleHash(int firstHash, int value, int timesHashed) {
        int dk = Q - value % Q;
        int hk = (firstHash + timesHashed * dk) % capacity;
        return hk;
    }

    // Sets the capacity as prime for the double hash function
    @Override
    protected void increaseCapacity() {
        int prime = nextPrime(capacity * 2);
        capacity = prime;
    }

    // Adjust the capacity of the hashtable to be a power of two.
    @Override
    protected void adjustCapacity() {
        // int pow2 = capacity * 2;
        // if (capacity == pow2)
        // return;
        // increaseCapacity();
    }

    private boolean isPrime(int n) {
        // Corner cases
        if (n <= 1)
            return false;
        if (n <= 3)
            return true;

        // This is checked so that we can skip
        // middle five numbers in below loop
        if (n % 2 == 0 || n % 3 == 0)
            return false;

        for (int i = 5; i * i <= n; i = i + 6)
            if (n % i == 0 || n % (i + 2) == 0)
                return false;

        return true;
    }

    // Function to return the smallest
    // prime number greater than N
    private int nextPrime(int N) {

        // Base case
        if (N <= 1)
            return 2;

        int prime = N;
        boolean found = false;

        // Loop continuously until isPrime returns
        // true for a number greater than n
        while (!found) {
            prime++;

            if (isPrime(prime))
                found = true;
        }

        return prime;
    }

    public static <K> int hashCodeSSF(K key) {
        int hash = 0;
        String stringKey = key.toString();
        for (int i = 0; i < stringKey.length(); i++) {
            hash += stringKey.charAt(i);
        }
        return hash;
    }

    public static <K> int hashCodePAF(K key) {
        ArrayList<Character> characters = new ArrayList<>();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";

        for (int i = 0; i < alphabet.length(); i++) {
            characters.add(alphabet.charAt(i));
        }

        final int constant = 31;
        int hash = 0;
        String stringKey = key.toString();

        for (int i = 0; i < stringKey.length(); i++) {
            hash += (characters.indexOf(stringKey.charAt(i)) + 1) * Math.pow(constant, stringKey.length() - 1 - i);
        }

        return hash;

    }
}