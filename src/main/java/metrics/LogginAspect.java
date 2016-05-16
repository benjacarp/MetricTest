package metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.Timer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.joda.time.DateTime;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Created by benjamin.salas on 16/05/2016.
 */
@Aspect
public class LogginAspect {

    private static final Logger LOGGER = Logger.getLogger(LogginAspect.class.getName());
    MetricRegistry metricRegistry = new MetricRegistry();

    @Around("execution(* processor.TestProcessor.process(..))")
    public void logSpringBeanMethod(ProceedingJoinPoint joinPoint) throws Throwable {

        //start reporter
        Slf4jReporter reporter = Slf4jReporter.forRegistry(metricRegistry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);

        //start timing
        Timer processorTimer = metricRegistry.timer(MetricRegistry.name("TestProcessor", "-process"));;
        DateTime start = DateTime.now();
        Timer.Context processorContext = processorTimer.time();

        //process itself
        joinPoint.proceed();

        //stop timing
        processorContext.close();
        LOGGER.info("process execution time: " + (DateTime.now().getMillis() - start.getMillis()) + " milliseconds");

        //close reporter
        reporter.stop();
    }

}
