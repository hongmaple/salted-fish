package com.haiyan.deflower.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haiyan.deflower.dao.AddressDao;
import com.haiyan.deflower.mapper.AddressMapper;
import com.haiyan.deflower.pojo.Address;
import org.springframework.stereotype.Repository;

/**
 * @author haiyan
 */
@Repository
public class AddressDaoImpl extends ServiceImpl<AddressMapper, Address> implements AddressDao {
}
