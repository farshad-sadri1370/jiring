package mocknewsfeed;


import java.util.Comparator;
import java.util.List;

public class News implements Comparable {

    private String headLine;
    private int priority;
    public News() {
    }

    public News(String headLine, int priority) {
        this.headLine = headLine;
        this.priority = priority;
    }

    public static News fromString(String input) {
        String[] parts = input.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid input format for News: " + input);
        }

        String headline = parts[0];
        int priority;
        priority = Integer.parseInt(parts[1].trim());

        return new News(headline, priority);
    }

    public String getHeadLine() {
        return headLine;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return  headLine + '\'' +
                "," + priority;

    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
