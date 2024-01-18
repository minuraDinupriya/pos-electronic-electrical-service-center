package edu.icet.crm.bo.custom.impl;

import edu.icet.crm.bo.custom.LogInViewBo;
import edu.icet.crm.dao.DaoFactory;
import edu.icet.crm.dao.custom.LogInViewDao;
import edu.icet.crm.dao.util.DaoType;
import edu.icet.crm.dto.UserDto;

import java.util.List;

public class LogInViewBoImpl implements LogInViewBo {
    private LogInViewDao logInViewDao= DaoFactory.getInstance().getDao(DaoType.LOGIN_VIEW_DAO);
    @Override
    public List<UserDto> getUsers() {
        return logInViewDao.getAllUsers();
    }

    @Override
    public boolean updatePassword(String email, String newPassword) {
        return logInViewDao.updatePassword(email,newPassword);
    }
}
