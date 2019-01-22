var files = new Vue({
    el: '#files',
    data: function data() {
        return {
            prefix: "",
            list: []
        };
    },
    mounted: function(){
        var url = location.search; //获取url中"?"符后的字串 
        var theRequest = new Object(); 
        if (url.indexOf("?") != -1) {
            var str = url.substr(1); 
            strs = str.split("&"); 
            for(var i = 0; i < strs.length; i ++) {
                theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]); 
            } 
        } 
        this.prefix = theRequest["path"] == null? "": theRequest["path"];
        var _this = this;
        axios.get("/file/path?path="+ this.prefix
        ).then(function(res){
            _this.list = res.data.Data.files;
        }).catch(function() {
            console.log("GET ERROR");
        });
    },
    methods: {
        onPathChange: function onPathChange(path) {
            this.prefix +=  "/" + path;
            var _this = this;
            axios.get("/file/path?path=" + this.prefix
            ).then(function(res){
                _this.list = res.data.Data.files;
            }).catch(function() {
                console.log("GET ERROR");
            });
        },
        goBackdDrectory: function goBackdDrectory() {
            var index = this.prefix.lastIndexOf("/");
            if(index == 0) {
                this.prefix = ""
            } else { 
                this.prefix = this.prefix.substring(0, index);
            }
            var _this = this;
            axios.get("/file/path?path=" + this.prefix
            ).then(function(res){
                _this.list = res.data.Data.files;
            }).catch(function() {
                console.log("GET ERROR");
            });
        }
    }
});