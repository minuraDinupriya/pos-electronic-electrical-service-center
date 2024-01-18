package edu.icet.crm.bo.custom.impl;

import edu.icet.crm.bo.custom.UserViewBo;
import edu.icet.crm.dao.DaoFactory;
import edu.icet.crm.dao.custom.UsersViewDao;
import edu.icet.crm.dao.util.DaoType;
import edu.icet.crm.dto.UserDto;

import java.util.List;

public class UserViewBoImpl implements UserViewBo {

    UsersViewDao usersViewDao = DaoFactory.getInstance().getDao(DaoType.USERS_VIEW_DAO);

    @Override
    public List<UserDto> getUsers() {
        return usersViewDao.getAllUsers();
    }

    @Override
    public boolean addUser(UserDto userDto) {
        return usersViewDao.addUser(userDto);
    }

    @Override
    public boolean deleteUser(String userId) {
        return usersViewDao.deleteUser(userId);
    }

    @Override
    public int getLastUserId() {
        if (usersViewDao.getLastUserId() == null) {
            return 0;
        } else {
            return Integer.parseInt(usersViewDao.getLastUserId().split("[r]")[1]);
        }
    }
}
