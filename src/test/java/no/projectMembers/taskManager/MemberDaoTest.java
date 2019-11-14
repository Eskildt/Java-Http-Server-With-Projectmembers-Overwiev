package no.projectMembers.taskManager;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberDaoTest {

    private static Random random = new Random();
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
    public void shouldRetrieveSavedMember() throws SQLException{
        Member member = sampleMember();
        dao.insert(member);
        assertThat(member).hasNoNullFieldsOrProperties();
        assertThat(dao.retrieve(member.getId())).isEqualToComparingFieldByField(member);
    }

    @Test
    static Member sampleMember(){
        Member member = new Member();

        member.setName(pickOneName(new String[] {"Bjørg", "Bjarne", "Bjarte", "Brage", "Britt", "Børge", "Borgar", "Bjørnar"}));
        member.setEmail(pickOneEmail(new String[] {"Bjørg@strikkeklubben.no", "Bjarne@esso.no", "Bjarte@radioresepsjonen.no","Brage@drageklubben.com", "Britt@heimkunnskap.no", "Børge@hodejegerne.no", "Borgar@SopraSteria.no", "Bjørnar@bjørneparken.no"}));
        return member;
    }


    static String pickOneName(String[] alternatives) {

        return alternatives[random.nextInt(alternatives.length)];
    }

    static String pickOneEmail(String[] alternatives) {

        return alternatives[random.nextInt(alternatives.length)];
    }
}
