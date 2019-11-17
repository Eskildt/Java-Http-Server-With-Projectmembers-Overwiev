package no.projectMembers.taskManager.assign;

import java.util.Objects;

public class AssignMember {


    private long id;
    private String task;
    private String member;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProjects() {
        return task;
    }

    public void setProjects(String task) {
        this.task = task;
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
                Objects.equals(task, that.task) &&
                Objects.equals(member, that.member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, task, member);
    }

    @Override
    public String toString() {
        return "AssignMember{" +
                "id=" + id +
                ", task='" + task + '\'' +
                ", member='" + member + '\'' +
                '}';
    }
}