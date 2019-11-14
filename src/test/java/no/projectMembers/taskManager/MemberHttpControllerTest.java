package no.projectMembers.taskManager;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;


class MemberHttpControllerTest {

    @Test
    void shouldReturnAllProducts() throws SQLException {
        MemberDao dao = new MemberDao(MemberDaoTest.createDataSource());
        Member member1 = MemberDaoTest.sampleMember();
        dao.insert(member1);


        MemberHttpController controller = new MemberHttpController(dao);
        assertThat(controller.getBody())
                .contains(String.format("<option value='%s'>%s</option>", member1.getId(), member1.getName()));

    }
}