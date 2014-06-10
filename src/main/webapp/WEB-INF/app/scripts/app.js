
'use strict';

var app = angular.module('mhtApp',["ui.router","ui.bootstrap"]);

app.config(function($stateProvider, $urlRouterProvider){
	$urlRouterProvider.otherwise("/");

	$stateProvider
    .state('home',{
        url:"/index",
        templateUrl:"views/main.html",
        controller:function($scope){

        }
    })

    .state('login', {
	    url: '/login',
	    templateUrl: "views/user/login.html",
        controller: function($scope,myFactory) {
            $scope.login=function(){
                myFactory.login($scope.user);
            }
        }
	})

    .state('register',{
        url:"/register",
        templateUrl:"views/user/register.html",
        controller:function($scope){
            
        }
    })
    ;
});
app.factory('myFactory', function($http,$state) {
    var service={};
    service.login=function(user){
        $http.post(resource.login,user).success(function(res){
            if(res&&res.success) {
                $state.go('home');
            }
            return res;
        })
    }
    return service;
});