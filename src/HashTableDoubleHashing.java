
import java.util.ArrayList;

public class HashTableDoubleHashing<K, V> extends HashTableOpenAddressingBase<K, V> {
    private final int Q = 7;

    public HashTableDoubleHashing() {
        super();
    }

    public HashTableDoubleHashing(int capacity) {
        super(capacity);
    }

    public HashTableDoubleHashing(int capacity, double loadFactor) {
        super(capacity, loadFactor);
    }

    protected int doubleHash(int firstHash, int value, int timesHashed) {
        int dk = Q - value % Q;
        int hk = (firstHash + timesHashed * dk) % capacity;
        return hk;
    }

    // not important here
    protected int linearProbe(int index) {
        return 0;
    }

    // Sets the capacity as prime for the double hash function
    @Override
    protected void increaseCapacity() {
        int prime = nextPrime(capacity * 2);
        capacity = prime;
    }

    private boolean isPrime(int n) {

        if (n <= 1)
            return false;
        if (n <= 3)
            return true;

        if (n % 2 == 0 || n % 3 == 0)
            return false;

        for (int i = 5; i * i <= n; i = i + 6)
            if (n % i == 0 || n % (i + 2) == 0)
                return false;

        return true;
    }

    private int nextPrime(int N) {

        if (N <= 1)
            return 2;

        int prime = N;
        boolean found = false;

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