package processor;

/**
 * Created by benjamin.salas on 16/05/2016.
 */
public class TestProcessorImpl implements TestProcessor {

    public void process() throws InterruptedException {

        System.out.println("Process is running...");

        for (int i = 0; i < 6; i++) {
            Thread.sleep(1000);
        }

        System.out.println("Process finish...");

    }

}
