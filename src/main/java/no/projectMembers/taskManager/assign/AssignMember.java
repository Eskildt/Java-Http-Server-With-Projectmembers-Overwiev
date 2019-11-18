package no.projectMembers.taskManager.assign;

import java.util.Objects;

public class AssignMember {


    private long id;
    private String project;
    private String member;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssignMember that = (AssignMember) o;
        return id == that.id &&
                Objects.equals(project, that.project) &&
                Objects.equals(member, that.member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, project, member);
    }

    @Override
    public String toString() {
        return "AssignMember{" +
                "id=" + id +
                ", task='" + project + '\'' +
                ", member='" + member + '\'' +
                '}';
    }
}