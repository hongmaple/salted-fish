package com.haiyan.deflower.service;

import com.haiyan.deflower.pojo.Address;

import java.util.List;

/**
 * @author maple
 */
public interface AddressService {

    /**
     * 新增收货地址
     * @param address 地址
     * @return 地址id
     */
    Long addAddress(Address address);

    /**
     * 修改收货地址
     * @param address 地址
     * @return 结果
     */
    Boolean updateAddress(Address address);

    /**
     * 删除收货地址
     * @param id 地址id
     * @return 结果
     */
    Boolean deletedAddress(Long id);

    /**
     * 加载所有收货地址
     * @return
     */
    List<Address> listAddress();

    /**
     * 获取地址详情
     * @param id 地址id
     * @return 详情
     */
    Address getDetail(Long id);

}
