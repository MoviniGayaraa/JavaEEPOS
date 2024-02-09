package lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.bo.custom;

import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.bo.SuperBO;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dto.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    boolean saveItem(ItemDTO dto) throws SQLException, ClassNotFoundException;

    ItemDTO searchItem(String id) throws SQLException, ClassNotFoundException;

    boolean deleteItem(String id) throws SQLException, ClassNotFoundException;

    boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException;

    ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;

    boolean ItemExist(String id) throws SQLException, ClassNotFoundException;
}
