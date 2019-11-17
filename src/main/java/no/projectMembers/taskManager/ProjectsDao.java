package no.projectMembers.taskManager;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class ProjectsDao extends AbstractDao<Projects> {

    public ProjectsDao(DataSource dataSource){
        super(dataSource);
    }

    @Override
    protected void insertObject(Projects projects, PreparedStatement statement) throws SQLException{
        statement.setString(1, projects.getName());
        statement.setString(2, projects.getStatus());
    }


    public void insert(Projects projects) throws SQLException{
        insert(projects, "INSERT INTO projects (NAME, STATUS) VALUES (?, ?)");
    }



    public List<Projects> listAll() throws SQLException {
        return listAll("SELECT * FROM projects");
    }

    @Override
    protected Projects readObject(ResultSet resultSet) throws SQLException{
        Projects projects = new Projects();
        projects.setName(resultSet.getString("Name"));
        projects.setStatus(resultSet.getString("Status"));
        return projects;
    }
}
