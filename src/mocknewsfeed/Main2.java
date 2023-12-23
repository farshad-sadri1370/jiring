package mocknewsfeed;

public class Main2 {

    public static void main(String[] args) {
        NewsGenerator newsGenerator = new NewsGeneratorImplement();
        Service service = new ServiceImplement(newsGenerator, 5000);
        service.sendNews();
    }
}
