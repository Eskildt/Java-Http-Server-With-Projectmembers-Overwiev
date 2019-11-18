package no.projectMembers.taskManager;

import no.projectMembers.taskManager.member.Member;
import no.projectMembers.taskManager.member.MemberDao;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberDaoTest {

    private static Random random = new Random();
    private JdbcDataSource dataSource;
    private MemberDao dao;

    @BeforeEach
    void setup(){
        JdbcDataSource dataSource = createDataSource();
        dao = new MemberDao(dataSource);
    }

    static JdbcDataSource createDataSource(){
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
    static Member sampleMember(){
        Member member = new Member();

        member.setName(pickOneName(new String[] {"Bjørg", "Bjarne", "Bjarte", "Brage", "Britt", "Børge", "Borgar", "Bjørnar"}));
        return member;
    }


    static String pickOneName(String[] alternatives) {

        return alternatives[random.nextInt(alternatives.length)];
    }
}
