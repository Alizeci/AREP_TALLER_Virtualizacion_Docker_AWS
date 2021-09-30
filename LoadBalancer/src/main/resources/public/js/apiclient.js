var Module = (function () {

    var url = "http://localhost:42000"
    function getLog(string){
        axios.post(url+"/addlog?string="+string)
        .then(response=> Module.showInfo(response.data))
        .catch(error=> console.log(error));
    }
    function showInfo() {
        $("#table > tbody").empty();
        axios.get(url+"/recentlog")
        .then(res=>{
            console.log(res.data);
            res.data.map( string => {
                $("#table > tbody").append(
                    "<tr>" +
                    "<td>" + string.date + "</td> " +
                    "<td>" + string.string + "</td>" +
                    "</tr>"
                );
            });
        }).catch(error => {
            console.log(error);
        });
    }
    return {
        getLog:getLog,
        showInfo:showInfo
    };
})();