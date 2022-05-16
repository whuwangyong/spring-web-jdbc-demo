package cn.whu.wy.demo.restapi;


import cn.whu.wy.demo.restapi.dto.UserDto;
import cn.whu.wy.demo.restapi.dto.response.ResponseEntity;
import cn.whu.wy.demo.restapi.entity.User;
import cn.whu.wy.demo.restapi.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;


/**
 * @Author WangYong
 * @Date 2022/05/09
 * @Time 09:39
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class ControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private UserDto userDto = UserDto.builder().name("test").email("test@example.com").address("shenzhen").build();


    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Order(2)
    @Test
    public void testPost() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        log.info("post result={}", result);
    }

    @Order(3)
    @Test
    public void testGet() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
                        .param("email", "test@example.com")
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        String asString = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ResponseEntity<User> responseEntity = objectMapper.readValue(asString, new TypeReference<>() {
        });
        log.info("responseEntity={}", responseEntity);

        User user = responseEntity.getData();
        Assertions.assertEquals(user.getAddress(), userDto.getAddress());
        Assertions.assertEquals(user.getName(), userDto.getName());
        Assertions.assertEquals(user.getEmail(), userDto.getEmail());
    }

    @Order(4)
    @Test
    public void testDelete() throws Exception {
        User user = userRepository.findByEmail(userDto.getEmail());
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/{id}", user.getId())
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print());
    }

}
