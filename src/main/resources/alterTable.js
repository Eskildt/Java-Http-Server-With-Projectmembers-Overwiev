Vue.component("member-table", {
    props: ["members"],
    template: `
<table> 
      <h3>Members:</h3>
      <tr> 
        <th>Id</th>
        <th>Name</th>
        <th>Email</th>      
      </tr>
      
      <tr v-for="member in members">
        <td>{{member.id}}</td>
        <td contenteditable="true" class="form-control" v-model="name">{{member.name}}</td>
        <td contenteditable="true">{{member.email}}</td>
      </tr>
    <button>SubmitEdit</button>
    </table> 
  `
});
//Members Table
new Vue({
    el: "#app",
    data() {
        return {
            currentMain: "members",
            members: "",
            tasks: [],
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
        },
        formSubmit(e) {
            e.preventDefault();
            console.log(this.email);
            let body = `name=${this.name}&email=${this.email}`;
            body = encodeURI(body);
            axios({
                method: "alterpost",
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
            this.getData();
        }
    },
    mounted() {
        this.getData();
    }


});