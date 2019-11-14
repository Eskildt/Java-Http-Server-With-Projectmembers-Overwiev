package no.projectMembers.taskManager;

import org.assertj.core.api.Assertions;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberDaoTest {

    private static Random random = new Random();
    JdbcDataSource dataSource = TestDatabase.testDataSource();
    private MemberDao dao;

    @BeforeEach
    void setUp(){
        dataSource = createDataSource();
        dao = new MemberDao(dataSource);
    }

    static JdbcDataSource createDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:memberTest;DB_CLOSE_DELAY=-1");
        Flyway.configure().dataSource(dataSource).load().migrate();
        return dataSource;
    }

    @Test
    void shouldListSavedMember() throws SQLException{
        Member member = sampleMember();
        dao.insert(member);
        assertThat(dao.listAll())
                .extracting(Member::getName)
                .contains(member.getName());
    }


    @Test
    public void shouldListInsertedMembers() throws SQLException {
        MemberDao memberDao = new MemberDao(dataSource);
        Member member = sampleMember();
        memberDao.insert(member);
        Assertions.assertThat(memberDao.listAll()).contains(member);
    }




    static Member sampleMember(){
        Member member = new Member();
        member.setName(pickOne(new String[]{"Bjørg", "Bjarne", "Bjarte", "Brage"}));
        return member;
    }


    private static String pickOne(String[] alternatives){return alternatives[random.nextInt(alternatives.length)];}
}
