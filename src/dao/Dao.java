package dao;

import java.sql.SQLException;
import java.util.Collection;

/**
 * <pre>
 * Taken from: https://www.javaworld.com/article/2074052/design-patterns/write-once--persist-anywhere.html
 * </pre>
 * @author 
 */
public interface Dao {
   public void create(final Object object) throws SQLException;

   public Collection retrieve(final String queryString) throws SQLException;

   public void update(final Object object) throws SQLException;

   public void delete(final Object object) throws SQLException;

   public void close() throws SQLException;
}
