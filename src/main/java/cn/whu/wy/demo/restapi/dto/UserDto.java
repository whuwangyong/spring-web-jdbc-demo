package cn.whu.wy.demo.restapi.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 用户表
 *
 * @Author WangYong
 * @Date 2022/05/05
 * @Time 14:13
 */
@Data
@Builder
public class UserDto {
    private String name;
    private String email;
    private String address;
}
