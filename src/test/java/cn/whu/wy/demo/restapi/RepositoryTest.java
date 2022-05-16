package cn.whu.wy.demo.restapi;


import cn.whu.wy.demo.restapi.entity.User;
import cn.whu.wy.demo.restapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;


/**
 * @Author WangYong
 * @Date 2022/05/07
 * @Time 19:18
 */

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class RepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Order(1)
    @Test
    public void init() {
        log.info("init");
        jdbcTemplate.execute("truncate user");
    }

    @Order(2)
    @Test
    public void testAdd() {
        log.info("testAdd");
        for (int i = 1; i <= 1000; i++) {
            userRepository.add(
                    User.builder()
                            .name("test-" + i % 100)
                            .email("test-" + i + "@example.com")
                            .address("上海市亭林镇御寒街" + i + "号")
                            .build()
            );
        }
        Integer count = userRepository.count();
        assert count == 1000;
    }

    @Order(3)
    @Test
    public void testFind() {
        log.info("testFind");
        User u1 = userRepository.findByEmail("test-125@example.com");
        User u2 = userRepository.findById(125);
        assert u1.equals(u2);

        List<User> userList = userRepository.findByName("test-99");
        assert userList.size() == 10;

    }

    @Order(4)
    @Test
    public void testDelete() {
        log.info("testDelete");
        int deleted = 0;
        for (int i = 1; i <= 1000; i++) {
            if (i % 47 == 0) {
                userRepository.delete(i);
                deleted++;
            }
        }
        Integer count = userRepository.count();
        assert count == 1000 - deleted;
    }

    @Order(5)
    @Test
    public void testPage() {
        log.info("testPage");
        List<User> userList = userRepository.get(20, 17);
        log.info("userList={}", userList);
        assert userList.size() == 20;
    }

}
