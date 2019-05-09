app.controller('loginController' ,function($scope, $http){

    $scope.login = function(){

        var user = {
            username : $scope.username,
            password : $scope.password
        }


        $http.post('/login/doLogin', user).success(function(data){
            if(data){
                //登陆成功则跳转到首页,失败则回到login.html
                location.href = '/index.html';



            }
        });
    }


})