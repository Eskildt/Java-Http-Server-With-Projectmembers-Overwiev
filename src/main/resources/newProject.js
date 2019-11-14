
Vue.component("project-table", {
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
      <tr v-for="project in projects">
        <td contenteditable='true'>{{project.id}}</td>
        <td contenteditable='true'>{{project.name}}</td>
        <td contenteditable='true'>{{project.description}}</td>
        <td contenteditable='true'>{{project.status}}</td>
      </tr>
    </table> 
  `
});
//Project Table
new Vue({
    el: "#app",
    data() {
        return {
            currentMain: "projects",
            projects: "",
            tasks: [],
            name: "",
            description: "",
            status: "",
            output: ""
        };
    },
    methods: {
        getData: function() {
            
        },
        formSubmit(e) {
            e.preventDefault();
            console.log(this.description);
            let body = `name=${this.name}&description=${this.description}&status=${this.status}`;
            body = encodeURI(body);
            axios({
                method: "post",
                url: "/api/projects",
                data: body
            })
                .then(function(response) {
                    currentObj.output = response.data;
                })
                .catch(function(error) {
                    currentObj.output = error;
                });
            this.name = "";
            this.description ="";
            this.status ="";
            this.getData();
        }
    },
    mounted() {
        this.getData();
    }
});