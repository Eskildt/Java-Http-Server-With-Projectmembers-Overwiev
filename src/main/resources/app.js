Vue.component("member.table", {
    props: ["members"],
    template: `
    <table>
        <h3>Members:</h3>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Email</th>
        </tr>
        <tr v-for="member in members" @click=""window.console.log(member.id)">
        <td>{{member.id}}</td>
        <td>{{member.name}}</td>
        <td>{{member.email}}</td>
        </tr>
      </table>
  `
});

new Vue({
    el: "#app",
    data() {
        return {
            currentMain:"project",
            members: [],
            project: [],
            name: "",
            email: "",
            output: ""
        };
    },
    mounted() {
        let membersResult;
        axios
            .get("http://localhost:8080/membersapi")
            .then(response => (this.members = response.data));
        axios
            .get("http://localhost:8080/tasksapi")
            .then(response => (this.tasks = response.data));
    }
});