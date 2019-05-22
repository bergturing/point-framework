package io.github.bergturing.point.dal;

import io.github.bergturing.point.dal.annotations.EnableDal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import static io.github.bergturing.point.utils.LoggerUtils.info;

@EnableDal
@ComponentScan
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PointDalApplicationTests.class)
public class PointDalApplicationTests {
    /**
     * 日志打印对象
     */
    private static final Logger logger = LoggerFactory.getLogger(PointDalApplicationTests.class);

    @Test
    public void test() {
        info(logger, "start dal test");
    }
}
