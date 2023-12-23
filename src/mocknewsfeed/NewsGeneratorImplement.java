package mocknewsfeed;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class NewsGeneratorImplement implements NewsGenerator{

    private static final List<String> HEADLINES = Arrays.asList("up", "down", "rise", "fall", "good", "bad", "success", "failure", "high", "low");

    int[] weights = {2, 2, 2, 2, 2, 1, 1, 1, 1, 1};

    @Override
    public News getNews() {

        Random random = new Random();
        int wordCount = random.nextInt(3) + 3;

        String headline = random.ints(0, HEADLINES.size())
                .distinct()
                .limit(wordCount)
                .mapToObj(index -> HEADLINES.get(index))
                .collect(Collectors.joining(" "));

        int randomNumber = weightedRandom(random, weights);

        News news = new News();
        news.setHeadLine(headline);
        news.setPriority(randomNumber);

        return news;
    }


    private static int weightedRandom(Random random, int[] weights) {
        int totalWeight = 0;
        for (int weight : weights) {
            totalWeight += weight;
        }

        int randomNumber = random.nextInt(totalWeight);

        for (int i = 0; i < weights.length; i++) {
            if (randomNumber < weights[i]) {
                return i;
            }
            randomNumber -= weights[i];
        }

        return weights.length - 1;
    }

}
