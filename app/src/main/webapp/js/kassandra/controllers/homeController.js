(function () {
    'use strict';

    angular.module('app')
        .controller('HomeController', ['$rootScope', '$scope', '$location', '$localStorage', 'Auth', 'Facebook',
            function ($rootScope, $scope, $location, $localStorage, Auth, Facebook) {
                function successAuth(res) {
                    $localStorage.token = res.token;
                    $localStorage.userId = res.data; //store userId along with token
                    window.location = "#/";
                    $rootScope.token = res.token;
                }

                $scope.signin = function () {
                    var formData = {
                        email: $scope.email,
                        password: $scope.password
                    };

                    Auth.signin(formData, successAuth, function () {
                        $rootScope.error = 'Invalid credentials.';
                    })
                };

                $scope.facebook = function () {
                    FB.login(function(response) { 
                        FB.getLoginStatus(function(response){
                        console.log(response);
                        if(response.status === "connected"){
                            Auth.facebook(response.authResponse.userID, function(res){
                            var payload = {};
                            payload.token = res.token;
                            payload.userID = res.data;
                            successAuth(res);

                            Facebook.checkUser(payload, function(){
                            } ,
                                //do facebook call for details and register user
                                function(error){
                                //user facebook token
                                $localStorage.fbToken = response.authResponse.accessToken;
                                var user = Facebook.details(response.authResponse.userID, function(){
                                delete $localStorage.fbToken;
                                ///success create user
                                var formData = {
                                    email: user.email,
                                    id: user.id,
                                    gravatarUrl: picture.data.url,
                                    password: "no"
                                }
                                Auth.signup(formData, function(){
                                successAuth(res)
                                }, function (res) {
                                    $rootScope.error = res.error || 'Failed to sign up.';
                                })}, function(){
                                //error ... fail mizerably
                                    $rootScope.error = 'Failed retrieve user data from Facebook.';
                                    delete $localStorage.fbToken;
                                });
                                }
                            );
                            }, function () {
                                $rootScope.error = 'Failed to connect to Facebook.';
                            });
                        }
                    }, {scope: 'public_profile,email'});
                })
                };

                $scope.signup = function () {
                    var formData = {
                        email: $scope.email,
                        password: $scope.password
                    };

                    Auth.signup(formData, successAuth, function (res) {
                        $rootScope.error = res.error || 'Failed to sign up.';
                    });
                };


                $scope.tokenClaims = Auth.getTokenClaims();
            }]);
})();
