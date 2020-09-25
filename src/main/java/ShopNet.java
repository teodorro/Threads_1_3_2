import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

public class ShopNet {
    public static final Integer ARRAY_SIZE = 10;
    private Random rand = new Random(System.currentTimeMillis());

    public void start(){
        ExecutorService es = Executors.newFixedThreadPool(3);
        try {
            LongAdder stat = new LongAdder();
            IntStream.range(0, 3).forEach(x -> es.submit(() -> stat.add(Arrays.stream(generateArray(ARRAY_SIZE)).sum())));
            es.awaitTermination(3, TimeUnit.SECONDS);
            System.out.println("General total = " + stat);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            es.shutdown();
        }
    }

    private int[] generateArray(int size){
        int[] ar = new int[size];
        String str = "";
        for (int i = 0; i < size; i++) {
            ar[i] = rand.nextInt(10);
            str += ar[i] + "\t";
        }
        System.out.println(str + ":: sum = " + Arrays.stream(ar).sum());
        return ar;
    }
}
