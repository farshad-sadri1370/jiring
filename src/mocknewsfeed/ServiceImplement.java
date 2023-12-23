package mocknewsfeed;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ServiceImplement implements Service {

    private static final String HOST = "localhost";
    private static final int PORT = 8081;
    private int frequency;
    private NewsGenerator newsGenerator;

    public ServiceImplement(NewsGenerator newsGenerator, int frequency) {
        this.newsGenerator = newsGenerator;
        this.frequency = frequency;
    }

    @Override
    public void sendNews() {

        try (Socket socket = new Socket(HOST, PORT);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

            while (true) {
                News news = newsGenerator.getNews();
                writer.println(news.toString());
                Thread.sleep(frequency);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

