package com.bergturing.point.dal;

import com.bergturing.point.dal.annotations.EnableDal;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@EnableDal
@ComponentScan
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PointDalApplicationTests.class)
public class PointDalApplicationTests {
}
