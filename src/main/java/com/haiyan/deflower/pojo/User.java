package com.haiyan.deflower.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author haiyan
 */
@Data
@TableName("tb_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    @Length(min = 4, max = 30, message = "用户名只能在4~30位之间")
    private String username;

    @Length(min = 12, max = 12, message = "学号只能是12位")
    private String studentNumber;

    private String grade;

    @Length(min = 4, max = 30, message = "密码只能在4~30位之间")
    private String password;

    @Pattern(regexp = "^1[356789]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    private String role;
    /**
     * 头像地址
     */
    private String avatarImage;

    private Date createTime;
}