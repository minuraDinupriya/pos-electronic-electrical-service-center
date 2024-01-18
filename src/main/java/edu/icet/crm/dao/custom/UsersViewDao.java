package edu.icet.crm.dao.custom;

import edu.icet.crm.dao.SuperDao;
import edu.icet.crm.dto.UserDto;
import java.util.List;

public interface UsersViewDao extends SuperDao {
    List<UserDto> getAllUsers();
    public boolean addUser(UserDto userDto);
    boolean deleteUser(String userId);

}
