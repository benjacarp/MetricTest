import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.Timer;

import java.util.concurrent.TimeUnit;

import static com.codahale.metrics.MetricRegistry.name;

public class App {
    static final MetricRegistry METRIC_REGISTRY = new MetricRegistry();


    public static void main(String args[]) {
        startReport();

        Timer timer = METRIC_REGISTRY.timer(name(App.class, "Example-Timer"));
        Timer.Context context = timer.time();

        for (int i = 0; i < 10; i++) {
            wait1Second();
        }

        context.stop();

        /*Meter requests = METRIC_REGISTRY.meter("requests");
        requests.mark();*/

//        wait1Second();
    }

    static void startReport() {
        Slf4jReporter reporter = Slf4jReporter.forRegistry(METRIC_REGISTRY)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);
    }

    static void wait1Second() {
        try {
            Thread.sleep(1000);
        }
        catch(InterruptedException e) {}
    }
}
