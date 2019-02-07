var vm = new Vue({
    el: "#login-panel",
    data: {
        account: "",
        password: ""
    },
    methods: {
        submit: function () {
            axios.post("/login", {
                email: this.account,
                password: this.password
            }).then(function (data) {
                alert("success");
                console.log(data);
                console.log(data['data']);

                window.location.href = "memberInfo.html";
            }).catch(function (reason) {
                alert(reason);
            });
        }
    }
})