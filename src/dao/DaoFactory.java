package dao;

import java.sql.SQLException;

public abstract class DaoFactory {
   
   //todo: getINstance()
      
   public abstract Dao createDao() throws SQLException;
}
