package edu.icet.crm.dao.custom.impl;

import edu.icet.crm.dao.custom.UsersViewDao;
import edu.icet.crm.dto.UserDto;

import java.util.List;

public class UsersViewDaoImpl implements UsersViewDao {
    @Override
    public List<UserDto> getAllUsers() {
        return null;
    }

    @Override
    public boolean addUser(UserDto userDto) {
        return false;
    }

    @Override
    public boolean deleteUser(String userId) {
        return false;
    }
}
