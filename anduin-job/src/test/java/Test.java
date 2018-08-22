import org.apache.commons.lang3.time.StopWatch;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by zongbo.zhang on 8/15/18.
 */
@Slf4j
public class Test {
    public static void main(String[] args) throws InterruptedException {
        StopWatch watch = new StopWatch();
        watch.start();

        log.info("hello");
        Thread.sleep(1000);
        log.info("now: {}",mill2Time(System.currentTimeMillis()));
        watch.stop();
        log.info("total: {}",watch.getTime());
        System.out.println(mill2Time(System.currentTimeMillis()));
    }

    private static String mill2Time(long mill){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        return sdf.format(mill);
    }
}
