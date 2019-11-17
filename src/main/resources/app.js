Vue.component("member-table", {
    props: ["members"],
    template: `
<table> 
      <h3>Members:</h3>
      <tr> 
        <th>Id</th>
        <th>Name</th>
        <th>Email</th> 
        <th>Project</th>
      </tr>
      <tr v-for="member in members" @click="window.console.log(member.id)">
        <td>{{member.id}}</td>
        <td>{{member.name}}</td>
        <td>{{member.email}}</td>
        <td>{{member.project}}</td>
      </tr>
    </table> 
  `
});Vue.component("projects-table", {
    props: ["projects"],
    template: `
<table> 
      <h3>Projects:</h3>
      <tr> 
        <th>Id</th>
        <th>Name</th>
        <th>Description</th> 
        <th>Status</th>
      </tr>
      <tr v-for="project in projects" @click="window.console.log(project.id)">
        <td>{{projects.id}}</td>
        <td>{{projects.name}}</td>
        <td>{{projects.email}}</td>
        <td>{{projects.project}}</td>
      </tr>
    </table> 
  `
});

//Members Table
new Vue({
    el: "#app",
    data() {
        return {
            currentMain: "projects",
            members: [],
            project: [],
            name: "",
            email: "",
            output: ""
        };
    },
    methods: {
        getData: function() {
            axios
                .get("http://localhost:8080/api/members")
                .then(response => (this.members = response.data));
            axios
                .get("http://localhost:8080/api/projects")
                .then(response => (this.projects = response.data));
            },
        formSubmit(e) {
            e.preventDefault();
            console.log(e.target.name);
            console.log(this.email);
            console.log(this.project);
            let body = `name=${this.name}&email=${this.email}&project=${this.project}`;
            body = encodeURI(body);
            axios({
                method: "put",
                url: "/api/members",
                data: body
            })
                .then(function(response) {
                    currentObj.output = response.data;
                })
                .catch(function(error) {
                    currentObj.output = error;
                });
            this.name = "";
            this.email = "";
            this.project = "";
            this.getData();
        }
    },
    mounted() {
        this.getData();
    }
});