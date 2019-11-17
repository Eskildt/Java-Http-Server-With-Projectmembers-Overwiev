package no.projectMembers.taskManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class ProjectsDao extends AbstractDao<Projects> {

    public ProjectsDao(DataSource dataSource){
        super(dataSource);
    }

    public void insert(Projects project) throws SQLException{
        insert(project, "INSERT INTO projects (NAME, DESCRIPTION, STATUS) VALUES (?, ?, ?)");
    }

    @Override
    protected void insertObject(Projects project, PreparedStatement statement) throws SQLException{
        statement.setString(1, project.getName());
        statement.setString(2, project.getDescription());
        statement.setString(3, project.getStatus());
    }

    public Projects retrieve(long id) throws SQLException {
        try(Connection connection = dataSource.getConnection()){
            try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM projects WHERE ID = ?")) {
                statement.setLong(1, id);
                try(ResultSet resultSet = statement.executeQuery()) {
                    if(resultSet.next()) {
                        return readObject(resultSet);
                    } else {
                        return null;
                    }
                }
            }
        }
    }

    public List<Projects> listAll() throws SQLException {
        return listAll("SELECT * FROM projects");
    }

    @Override
    protected Projects readObject(ResultSet resultSet) throws SQLException{
        Projects projects = new Projects();
        projects.setId(resultSet.getInt("Id"));
        projects.setName(resultSet.getString("Name"));
        projects.setDescription(resultSet.getString("Description"));
        projects.setStatus(resultSet.getString("Status"));
        return projects;
    }
}
