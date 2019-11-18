package no.projectMembers.taskManager.member;

import no.projectMembers.taskManager.AbstractDao;

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

    public void insert(Member member) throws SQLException {
        long id = insert(member, "INSERT INTO members (name) VALUES (?)");
        member.setId(id);
    }



    @Override
    protected void insertObject(Member member, PreparedStatement statement) throws SQLException {
        statement.setString(1, member.getName());
    }

    @Override
    protected Member readObject(ResultSet resultSet) throws SQLException {
        Member member = new Member();
        member.setName(resultSet.getString("name"));

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
