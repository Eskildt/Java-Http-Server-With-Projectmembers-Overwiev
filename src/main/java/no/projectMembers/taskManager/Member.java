package no.projectMembers.taskManager;

import java.util.Objects;

public class Member {

    public String email;
    private String name;
    public int id;


    @Override
    public String toString() {
        return "{" +
                "id=" + id + ", name='" + name + '\'' + ", email='" + email + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return id == member.id &&
                Objects.equals(name, member.name) &&
                Objects.equals(email, member.email);
    }

    @Override
    public int hashCode(){
        return Objects.hash(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
