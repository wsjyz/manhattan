'use strict';

var app = angular.module('mhtApp',["ui.router"]);
app.controller('MainCtrl', function($scope) {
});
app.config(function($stateProvider, $urlRouterProvider){
	$urlRouterProvider.otherwise("/index");
	$stateProvider.state('login', {
	    url: "/login",
	    templateUrl: "views/user/login.html",
        controller: function($scope,myFactory) {
            $scope.login=function(){
                var user=$scope.user;
                var result=myFactory.login(user);
                if(result&&!result.success) {
                    return result;
                }
            }
        }
	});
});
app.factory('myFactory', function($http) {
    var service={};
    service.login=function(user){
        $http.post('/mhd/login',user).success(function(res){
            return res;
        })
    }
    return service;
});