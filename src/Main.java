public class Main {
    public static void main(String[] args) {
        HashTableDoubleHashing<String, Integer> table = new HashTableDoubleHashing<>();

        for (int i = 0; i < 100000; i++) {
            table.insert(String.valueOf(i), i);
        }
        System.out.println(table.keys());
        System.out.println(table.getCapacity());
        System.out.println(table.size());
    }

}
