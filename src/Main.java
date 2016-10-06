import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Finderlo on 2016/10/4.
 */
public class Main {
    public static void main(String[] args) {
        ClawlerService clawlerService = new ClawlerService();
        clawlerService.initAll();
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 20; i++) {
            executorService.execute(new ClawlerThread(clawlerService));
        }

    }
}
