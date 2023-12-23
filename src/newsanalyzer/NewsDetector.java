package newsanalyzer;

import mocknewsfeed.News;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NewsDetector {

    private static final int PORT = 8081;
    private static final int ANALYSIS_INTERVAL = 10_000;
    private int positiveCount;
    private PriorityQueue<News> positiveNewsItems = new PriorityQueue<>();

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(this::performAnalysis, 0, ANALYSIS_INTERVAL, TimeUnit.MILLISECONDS);

        while (true) {
            Socket socket = serverSocket.accept();
            processNewsFeed(socket);
        }
    }

    private void processNewsFeed(Socket socket) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            News news = News.fromString(line);

            if (isPositive(news)) {
                positiveCount++;
                positiveNewsItems.offer(news);
            }
        }
    }

    private boolean isPositive(News news) {
        String headline = news.getHeadLine().toLowerCase();
        List<String> positiveWords = Arrays.asList("up", "rise", "good", "success", "high");

        long positiveCount = positiveWords.stream()
                .filter(i -> headline.contains(i))
                .count();

        return positiveCount > headline.split("\\s+").length / 2;
    }

    private void performAnalysis() {
        System.out.println("Positive News Count: " + positiveCount);

        int count = 0;
        while (!positiveNewsItems.isEmpty() && count < 3) {
            News news = positiveNewsItems.poll();
            System.out.println("Headline: " + news.getHeadLine());
            count++;
        }
    }
}


