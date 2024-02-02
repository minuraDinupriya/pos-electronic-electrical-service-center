package edu.icet.crm.dao.custom;

import edu.icet.crm.dao.SuperDao;
import edu.icet.crm.dto.PartDto;
import edu.icet.crm.entity.PartEntity;

public interface PartDao extends SuperDao {
    boolean savePart(PartDto partDto);
}
