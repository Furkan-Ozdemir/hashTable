
public class Entry {

    private String fileName;
    private int count;

    public Entry(String fileName, int count) {
        this.fileName = fileName;
        this.count = count;
    }

    public void incrementCount() {
        count = count + 1;
    }

    public int getCount() {
        return count;
    }

    public String getFileName() {
        return fileName;
    }

}
