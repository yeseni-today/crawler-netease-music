import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Finderlo on 2016/10/4.
 */
public class Main {
    public static final  int MAX_THREAD_COUNT = 40;
    public static void main(String[] args) {
        ClawlerService clawlerService = new ClawlerService();
        clawlerService.initAll();
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREAD_COUNT);
        for (int i = 0; i < MAX_THREAD_COUNT; i++) {
            executorService.execute(new ClawlerThread(clawlerService));
        }

    }
}
