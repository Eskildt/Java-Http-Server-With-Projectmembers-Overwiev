package no.projectMembers.taskManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MemberDao extends AbstractDao<Member> {


    public MemberDao(DataSource dataSource) {
        super(dataSource);
    }

    public long insert(Member member) throws SQLException {
        return insert(member, "INSERT INTO members (NAME, EMAIL, PROJECT) VALUES (?, ?, ?)");
    }

    public long alter(Member member) throws SQLException{
        return alter(member, "ALTER members (NAME, EMAIL, PROJECT) VALUES (?, ?, ?)");
    }

    @Override
    protected void insertObject(Member member, PreparedStatement statement) throws SQLException {
        statement.setString(1, member.getName());
        statement.setString(2, member.getEmail());
        statement.setString(3, member.getProject());
    }

    @Override
    protected Member readObject(ResultSet resultSet) throws SQLException {
        Member member = new Member();
        member.setId(resultSet.getInt("id"));
        member.setName(resultSet.getString("name"));
        member.setEmail(resultSet.getString("email"));
        member.setProject(resultSet.getString("project"));

        return member;
    }

    public Member retrieve(long id) throws SQLException {
        try(Connection connection = dataSource.getConnection()){
            try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM MEMBERS WHERE ID = ?")) {
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

    public List<Member> listAll() throws SQLException {
        return listAll("SELECT * FROM members");
    }
}
