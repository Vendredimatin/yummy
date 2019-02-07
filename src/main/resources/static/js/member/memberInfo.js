
window.onload = function (ev) {
    var vm1 = new Vue({
        el:"#profile-panelcontent",
        data:{
            name:"",
            email:"",
            phone:"",
            memberLevel:"",
            name_disabled: true,
            email_disabled: true,
            phone_disabled: true,
        },
        mounted () {
            console.log('开始加载会员信息');
            axios.post("/member/info").then(function (data) {
                console.log(data);
            })
        },
        methods:{
            modify: function(){
                this.name_disabled = !this.name_disabled;
                this.email_disabled = !this.email_disabled;
                this.phone_disabled = !this.phone_disabled;
            },

            submit:function () {
                axios.post("/member/modifyInfo",{
                    name:this.name,
                    email:this.email,
                    phone:this.phone
                }).then(function (data) {
                    alert("success");
                    console.log(data);
                    console.log(data['data']);
                }).catch(function (reason) {
                    alert(reason);
                });
            },

        }


    });
};
