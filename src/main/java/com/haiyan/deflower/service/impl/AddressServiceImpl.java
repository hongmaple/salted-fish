package com.haiyan.deflower.service.impl;

import com.haiyan.deflower.dao.AddressDao;
import com.haiyan.deflower.exception.ExceptionResult;
import com.haiyan.deflower.mapper.AddressMapper;
import com.haiyan.deflower.pojo.Address;
import com.haiyan.deflower.pojo.User;
import com.haiyan.deflower.service.AddressService;
import com.haiyan.deflower.utils.ServletUtils;
import com.haiyan.deflower.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author haiyan
 */
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressDao addressDao;
    private final AddressMapper addressMapper;
    @Autowired
    private UserUtils userUtils;

    public AddressServiceImpl(AddressDao addressDao, AddressMapper addressMapper) {
        this.addressDao = addressDao;
        this.addressMapper = addressMapper;
    }

    @Override
    public Long addAddress(Address address) {
        User user = userUtils.getUser(ServletUtils.getRequest());
        if (Objects.isNull(user)) {
            throw new ExceptionResult("Address","false",null,"请先登陆");
        }
        address.setUid(user.getId());
        if(!addressDao.save(address)) {
            throw new ExceptionResult("Address","false",null,"添加失败");
        }
        return address.getId();
    }

    @Override
    public Boolean updateAddress(Address address) {
        User user = userUtils.getUser(ServletUtils.getRequest());
        if (Objects.isNull(user)) {
            throw new ExceptionResult("Address","false",null,"请先登陆");
        }
        if (!addressDao.updateById(address)) {
            throw new ExceptionResult("Address","false",null,"修改失败");
        }
        return true;
    }

    @Override
    public Boolean deletedAddress(Long id) {
        User user = userUtils.getUser(ServletUtils.getRequest());
        if (Objects.isNull(user)) {
            throw new ExceptionResult("Address","false",null,"请先登陆");
        }
        if (addressMapper.deleteById(id)==0) {
            throw new ExceptionResult("Address","false",null,"删除失败");
        }
        return true;
    }

    @Override
    public List<Address> listAddress() {
        User user = userUtils.getUser(ServletUtils.getRequest());
        if (Objects.isNull(user)) {
            throw new ExceptionResult("Address","false",null,"请先登陆");
        }
        return addressDao.lambdaQuery().eq(Address::getUid, userUtils.getUser(ServletUtils.getRequest()).getId()).list();
    }

    @Override
    public Address getDetail(Long id) {
        User user = userUtils.getUser(ServletUtils.getRequest());
        if (Objects.isNull(user)) {
            throw new ExceptionResult("Address","false",null,"请先登陆");
        }
        return addressDao.lambdaQuery().eq(Address::getId,id).one();
    }
}
