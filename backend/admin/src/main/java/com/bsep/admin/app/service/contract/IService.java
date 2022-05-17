package com.bsep.admin.app.service.contract;

import com.bsep.admin.app.dto.CreateUserDto;
import com.bsep.admin.app.dto.UpdateUserDto;
import com.bsep.admin.app.model.User;

import java.util.List;

public interface IService<T> {
    List<T> findAll();
    T findById(Integer id) throws Exception;
    User create(CreateUserDto entity) throws Exception;
    User update(UpdateUserDto entity) throws Exception;
    void delete(Integer id) throws Exception;
}
