package cn.whu.wy.demo.restapi.controller;

import cn.whu.wy.demo.restapi.dto.UserDto;
import cn.whu.wy.demo.restapi.dto.response.ResponseCode;
import cn.whu.wy.demo.restapi.dto.response.ResponseEntity;
import cn.whu.wy.demo.restapi.entity.User;
import cn.whu.wy.demo.restapi.exception.UserNotFundException;
import cn.whu.wy.demo.restapi.service.UserService;
import cn.whu.wy.demo.restapi.util.Helper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author WangYong
 * @Date 2022/05/05
 * @Time 14:11
 */
@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/users")
    public ResponseEntity register(@RequestBody UserDto userDto) {
        User newUser = new User();
        BeanUtils.copyProperties(userDto, newUser);
        User user = userService.register(newUser);
        return new ResponseEntity<>(ResponseCode.SUCCESS, user);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity findById(@PathVariable int id) {
        User user = userService.findById(id);
        return new ResponseEntity<>(ResponseCode.SUCCESS, user);
    }

    @GetMapping("/users")
    public ResponseEntity get(@RequestParam(required = false) String email,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false, defaultValue = "0") int pageSize,
                              @RequestParam(required = false, defaultValue = "0") int page) {
        if (StringUtils.hasText(email)) {
            User user = userService.findByEmail(email);
            return new ResponseEntity<>(ResponseCode.SUCCESS, user);
        } else if (StringUtils.hasText(name)) {
            List<User> users = userService.findByName(name);
            return new ResponseEntity<>(ResponseCode.SUCCESS, users);
        } else if (pageSize > 0 && page > 0) {
            List<User> users = userService.get(pageSize, page);
            return new ResponseEntity<>(ResponseCode.SUCCESS, users);
        } else {
            Integer count = userService.count();
            return new ResponseEntity<>(ResponseCode.SUCCESS, count);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        userService.delete(id);
        return new ResponseEntity(ResponseCode.SUCCESS);
    }


    @PutMapping("/users/{id}")
    public ResponseEntity update(@RequestBody UserDto newUser, @PathVariable int id) {
        User oldUser = userService.findById(id);
        if (oldUser == null) {
            throw new UserNotFundException("id=" + id);
        } else {
            Helper.copyProperties(newUser, oldUser, true);
            oldUser.setUpdatedTime(LocalDateTime.now());
            final User updatedUser = userService.update(oldUser);
            return new ResponseEntity<>(ResponseCode.SUCCESS, updatedUser);
        }
    }


}
