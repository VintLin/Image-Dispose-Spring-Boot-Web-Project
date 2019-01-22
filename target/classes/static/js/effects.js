Vue.component('pagination', {
    template: '#pagination',
    props: {
        maxVisibleButtons: {
            type: Number,
            required: false,
            default: 3 },

        totalPages: {
            type: Number,
            required: true },

        total: {
            type: Number,
            required: true },

        currentPage: {
            type: Number,
            required: true } },
    computed: {
        isInFirstPage: function isInFirstPage() {
            return this.currentPage === 1;
        },
        isInLastPage: function isInLastPage() {
            return this.currentPage === this.totalPages;
        },
        startPage: function startPage() {
            if (this.currentPage === 1) {
                return 1;
            }
            if (this.currentPage === this.totalPages) {
                return this.totalPages - this.maxVisibleButtons + 1;
            }
            return this.currentPage - 1;
        },
        endPage: function endPage() {
            return Math.min(this.startPage + this.maxVisibleButtons - 1, this.totalPages);
        },
        pages: function pages() {
            var range = [];
            for (var i = this.startPage; i <= this.endPage; i += 1) {
                range.push({
                    name: i,
                    isDisabled: i === this.currentPage });
            }
            return range;
        } },

    methods: {
        onClickFirstPage: function onClickFirstPage() {
            this.$emit('pagechanged', 1);
        },
        onClickPreviousPage: function onClickPreviousPage() {
            this.$emit('pagechanged', this.currentPage - 1);
        },
        onClickPage: function onClickPage(page) {
            this.$emit('pagechanged', page);
        },
        onClickNextPage: function onClickNextPage() {
            this.$emit('pagechanged', this.currentPage + 1);
        },
        onClickLastPage: function onClickLastPage() {
            this.$emit('pagechanged', this.totalPages);
        },
        isPageActive: function isPageActive(page) {
            return this.currentPage === page;
        } } });

var effects = new Vue({
    el: '#effects',
    data: function data() {
        return {
            currentPage: 1,
            totalPages: 1,
            total: 1,
            list:[]
        };
    },
    mounted: function(){
        var _this = this;
        axios.get("/api/effects?pageIndex=1&pageSize=8"
        ).then(function(res){
            _this.currentPage = 1;
            _this.totalPages = res.data.Data.pages;
            _this.total = res.data.Data.total;
            // _this.list = res.data.Data.list;
            for(var i in res.data.Data.list) {
                _this.list[i] = {};
                for(var key in res.data.Data.list[i]) {
                    _this.list[i][key] = res.data.Data.list[i][key];
                }
            }
        }).catch(function() {
            console.log("GET ERROR");
        });
    },
    methods: {
        onPageChange: function onPageChange(page) {
            var _this = this;
            axios.get("/api/effects?pageIndex=" + page + "&pageSize=8"
            ).then(function(res){
                _this.list = res.data.Data.list;
            }).catch(function() {
                console.log("GET ERROR");
            });
            this.currentPage = page;
        }
    }
});