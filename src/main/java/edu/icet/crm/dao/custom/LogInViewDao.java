package edu.icet.crm.dao.custom;

import edu.icet.crm.dao.SuperDao;
import edu.icet.crm.dto.UserDto;

import java.util.List;

public interface LogInViewDao extends SuperDao {
    boolean updatePassword(String email, String newPassword);
    List<UserDto> getAllUsers();
}
