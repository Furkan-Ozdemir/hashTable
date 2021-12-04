import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        LinkedList<Long> searchTimes = new LinkedList<>();
        long maxIndexingTime = 0l, minIndexingTime = 105807050300l, averageIndexingTime = 0l;
        int count = 0;

        long indexingTime = System.nanoTime();
        for (File file : new File("C:\\Users\\DELL\\Desktop\\ödev\\bbc\\business").listFiles()) {
            FileHandling.stopWords();

            FileHandling.readFileAndPut("C:\\Users\\DELL\\Desktop\\ödev\\bbc\\business\\"
                    + file.getName());
        }
        for (File file : new File("C:\\Users\\DELL\\Desktop\\ödev\\bbc\\entertainment").listFiles()) {
            FileHandling.stopWords();

            FileHandling.readFileAndPut("C:\\Users\\DELL\\Desktop\\ödev\\bbc\\entertainment\\"
                    + file.getName());
        }
        for (File file : new File("C:\\Users\\DELL\\Desktop\\ödev\\bbc\\politics").listFiles()) {
            FileHandling.stopWords();

            FileHandling.readFileAndPut("C:\\Users\\DELL\\Desktop\\ödev\\bbc\\politics\\"
                    + file.getName());
        }
        for (File file : new File("C:\\Users\\DELL\\Desktop\\ödev\\bbc\\sport").listFiles()) {
            FileHandling.stopWords();

            FileHandling.readFileAndPut("C:\\Users\\DELL\\Desktop\\ödev\\bbc\\sport\\" +
                    file.getName());
        }
        for (File file : new File("C:\\Users\\DELL\\Desktop\\ödev\\bbc\\tech").listFiles()) {
            FileHandling.stopWords();

            FileHandling.readFileAndPut("C:\\Users\\DELL\\Desktop\\ödev\\bbc\\tech\\" +
                    file.getName());
        }

        long indexingTimeEnd = System.nanoTime() - indexingTime;

        File searchFile = new File("C:\\Users\\DELL\\Desktop\\ödev\\search.txt");
        Scanner scanner = new Scanner(searchFile);
        while (scanner.hasNext()) {
            long startTime = System.nanoTime();
            FileHandling.table.get(scanner.next());
            long estimatedTime = System.nanoTime() - startTime;
            searchTimes.add(estimatedTime);
            if (estimatedTime > maxIndexingTime) { // find max value
                maxIndexingTime = estimatedTime;
            }
            if (estimatedTime < minIndexingTime) {
                minIndexingTime = estimatedTime;
            }
        }

        Iterator<Long> iter = searchTimes.iterator();
        while (iter.hasNext()) {
            count++;
            averageIndexingTime += iter.next();
        }
        averageIndexingTime = averageIndexingTime / count;

        scanner.close();

        // System.out.println(FileHandling.table.toString());
        // FileHandling.table.get("Porto");
        // FileHandling.table.get("ahmet");
        // FileHandling.table.remove("Porto");
        // FileHandling.table.get("Porto");

        System.out.println(FileHandling.table.size());

        // System.out.println(HashTableDoubleHashing.getCapacity());
        System.out.println("Indexing Time is: " + indexingTimeEnd);
        System.out.println("MaxIndexing Time is: " + maxIndexingTime);
        System.out.println("MinIndexing Time is: " + minIndexingTime);
        System.out.println("AverageIndexing Time is: " + averageIndexingTime);
        System.out.println("Collision Count is: " + HashTableOpenAddressingBase.getCollisionCount());

    }

}
