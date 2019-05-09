app.controller('userController' ,function($scope, $http){
    $scope.getUserPermissionTree = function(){
        $http.post('/user/getUserPermissionTree').success(function(data){
            $scope.permissionList = data;
            console.log('$scope.permissionList', $scope.permissionList)
        });
    };


    $scope.logout = function () {
        $http.post('/login/logout').success(function (data) {
            if(data){
                location.href = '/login.html';
                location.reload(true);
            }
        })
    }

})