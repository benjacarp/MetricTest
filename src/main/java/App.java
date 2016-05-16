import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import processor.TestProcessor;

public class App {

    public static void main(String args[]) {

        ApplicationContext appContext = new ClassPathXmlApplicationContext("Spring-Context.xml");

        TestProcessor processor = (TestProcessor) appContext.getBean("testProcessor");

        try {
            processor.process();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
