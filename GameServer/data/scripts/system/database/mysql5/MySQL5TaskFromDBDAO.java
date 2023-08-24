package mysql5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.aionemu.commons.database.DatabaseFactory;
import gameserver.dao.TaskFromDBDAO;
import gameserver.model.tasks.TaskFromDB;

/**
 * Tasks from DB
 * 
 * @author flegelDEV and resurrected
 */
public class MySQL5TaskFromDBDAO extends TaskFromDBDAO
{
	/**
	 * Logger for this class.
	 */
	private static final Logger	log					= Logger.getLogger(MySQL5TaskFromDBDAO.class);
	
	private static final String SELECT_ALL_QUERY	= "SELECT * FROM tasks ORDER BY id";
	private static final String UPDATE_QUERY		= "UPDATE tasks SET last_activation = ? WHERE id = ?";
	
	@Override
	public ArrayList<TaskFromDB> getAllTasks()
	{
		final ArrayList<TaskFromDB> result = new ArrayList<TaskFromDB>();
		
		Connection con = null;
		
		try
		{
			con = DatabaseFactory.getConnection();
			PreparedStatement stmt = con.prepareStatement(SELECT_ALL_QUERY);
			
			ResultSet rset = stmt.executeQuery();
			
			while(rset.next())
				result.add(new TaskFromDB(
					rset.getInt("id"), 
					rset.getString("task"), 
					rset.getString("type"), 
					rset.getTimestamp("last_activation"), 
					rset.getString("startTime"), 
					rset.getInt("delay"), 
					rset.getString("param")
				));

			rset.close();
			stmt.close();
		}
		catch(SQLException e)
		{
			log.error(e);
		}
		finally
		{
			DatabaseFactory.close(con);
		}
				
		return result;
	}
	
	@Override
	public void setLastActivation(final int id)
	{
		Connection con = null;
		
		try
		{
			con = DatabaseFactory.getConnection();
			PreparedStatement stmt = con.prepareStatement(UPDATE_QUERY);
			
			stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			stmt.setInt(2, id);
			stmt.execute();
		}
		catch(SQLException e)
		{
			log.error(e);
		}
		finally
		{
			DatabaseFactory.close(con);
		}
	}

	@Override
	public boolean supports(String s, int i, int i1)
	{
		return MySQL5DAOUtils.supports(s, i, i1);
	}
}