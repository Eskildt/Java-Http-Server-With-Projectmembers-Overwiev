package no.projectMembers.taskManager;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;


class MemberHttpControllerTest {

    @Test
    void shouldReturnAllProducts() throws SQLException {
        MemberDao memberDao = new MemberDao(MemberDaoTest.createDataSource());
        Member member = MemberDaoTest.sampleMember();
        memberDao.insert(member);

        MemberHttpController controller = new MemberHttpController(memberDao);
        assertThat(controller.getBody())
                .contains("<option value='" + member.getId() + "'>" + member.getName() + "</option>");
    }
}