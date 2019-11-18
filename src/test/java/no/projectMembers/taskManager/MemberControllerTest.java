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
        MemberDao memberDao = new MemberDao(MemberDaoTest.createDataSource());
        Member member = MemberDaoTest.sampleMember();
        memberDao.insert(member);

        MemberController controller = new MemberController(memberDao);
        assertThat(controller.getBody())
                .contains("<option id='0'>" + member.getName() + "</option>");
    }

}