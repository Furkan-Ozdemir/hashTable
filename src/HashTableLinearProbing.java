import java.util.ArrayList;

public class HashTableLinearProbing<K, V> extends HashTableOpenAddressingBase<K, V> {

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

    // TODO bunu yap
    @Override
    protected int linearProbe(int index) {
        if (index == capacity) {
            index = 0;
            return index;
        }
        return ++index;
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