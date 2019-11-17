package no.projectMembers.taskManager;

import no.projectMembers.taskManager.member.Member;
import no.projectMembers.taskManager.member.MemberDao;
import no.projectMembers.taskManager.member.MemberController;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;


class MemberControllerTest {

    @Test
    void shouldReturnAllMembers() throws SQLException {

        MemberDao dao = new MemberDao(MemberDaoTest.createDataSource());
        Member member1 = MemberDaoTest.sampleMember();
        Member member2 = MemberDaoTest.sampleMember();
        dao.insert(member1);
        dao.insert(member2);

        MemberController membersHttpController = new MemberController(dao);
        assertThat(membersHttpController.getBody())
                .contains(String.format("<option id='%s'>%s</option>", member1.getId(), member1.getName()))
                .contains(String.format("<option id='%s'>%s</option>", member2.getId(), member2.getName()));
    }
}