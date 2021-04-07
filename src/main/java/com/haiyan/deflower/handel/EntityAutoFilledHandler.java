package com.haiyan.deflower.handel;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * @author haiyan
 */
@Slf4j
public class EntityAutoFilledHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("自动填充创建时间和更新时间");
        Date now = new Date();
        this.setFieldValByName("createdTime", now, metaObject);
        this.setFieldValByName("updatedTime", now, metaObject);
        this.setFieldValByName("isDeleted", false, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("自动填充更新时间");
        Date now = new Date();
        this.setFieldValByName("updatedTime", now, metaObject);
    }
}
