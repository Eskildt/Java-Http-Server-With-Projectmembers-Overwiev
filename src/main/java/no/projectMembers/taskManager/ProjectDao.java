package no.projectMembers.taskManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class ProjectDao extends AbstractDao<Project> {

    public ProjectDao(DataSource dataSource){
        super(dataSource);
    }

    public void insert(Project project) throws SQLException{
        insert(project, "INSERT INTO projects (NAME, DESCRIPTION, STATUS) VALUES (?, ?, ?)");
    }

    @Override
    protected void insertObject(Project project, PreparedStatement statement) throws SQLException{
        statement.setString(1, project.getName());
        statement.setString(2, project.getDescription());
        statement.setString(3, project.getStatus());
    }

    public Project retrieve(long id) throws SQLException {
        try(Connection connection = dataSource.getConnection()){
            try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM members WHERE ID = ?")) {
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

    public List<Project> listAll() throws SQLException {
        return listAll("SELECT * FROM projects");
    }

    @Override
    protected Project readObject(ResultSet resultSet) throws SQLException{
        Project project = new Project();
        project.setId(resultSet.getInt("Id"));
        project.setName(resultSet.getString("Name"));
        project.setDescription(resultSet.getString("Description"));
        project.setStatus(resultSet.getString("Status"));
        return project;
    }
}
