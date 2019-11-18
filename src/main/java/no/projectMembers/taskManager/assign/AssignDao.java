package no.projectMembers.taskManager.assign;

import no.projectMembers.taskManager.AbstractDao;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AssignDao extends AbstractDao<AssignMember> {

    public AssignDao(DataSource dataSource) {
        super(dataSource);
    }


    public void insert(AssignMember assignMember) throws SQLException {
        long id = insert(assignMember, "INSERT INTO assignprojectmember (taskid, memberid) VALUES (?, ?)");
        assignMember.setId(id);
    }


    @Override
    protected void insertObject(AssignMember assignMember, PreparedStatement statement) throws SQLException {

        statement.setString(1, assignMember.getProject());
        statement.setString(2, assignMember.getMember());
    }

    public List<AssignMember> listAll() throws SQLException {
        return listAll("SELECT * FROM assignprojectmember");
    }

    @Override
    protected AssignMember readObject(ResultSet resultSet) throws SQLException {
        AssignMember assignMember = new AssignMember();
        assignMember.setId(resultSet.getLong("id"));
        assignMember.setProject(resultSet.getString("projectid"));
        assignMember.setMember(resultSet.getString("memberid"));

        return assignMember;
    }

}