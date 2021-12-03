import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        // HashTableDoubleHashing<String, Integer> table = new
        // HashTableDoubleHashing<>();

        // for (int i = 0; i < 100000; i++) {
        // table.insert(String.valueOf(i), i);
        // }
        // System.out.println(table.keys());
        // System.out.println(HashTableOpenAddressingBase.getCapacity());
        // System.out.println(table.size());

        long startTime = System.nanoTime();
        for (File file : new File("C:\\Users\\DELL\\Desktop\\ödev\\bbc\\business").listFiles()) {
            FileHandling.stopWords();

            FileHandling.readFileAndPut("C:\\Users\\DELL\\Desktop\\ödev\\bbc\\business\\" + file.getName());
        }
        long estimatedTime = System.nanoTime() - startTime;
        // System.out.println(FileHandling.table.keys());

        System.out.println(FileHandling.table.toString());

        System.out.println(FileHandling.table.size());

        System.out.println(HashTableDoubleHashing.getCapacity());
        System.out.println("Estimated running time is: " + estimatedTime);

    }

}
