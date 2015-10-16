(function () {
    'use strict';

    angular.module('app')
        .controller('HomeController', ['$rootScope', '$scope', '$location', '$localStorage', 'Auth',
            function ($rootScope, $scope, $location, $localStorage, Auth) {
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

                $scope.signup = function () {
                    var formData = {
                        email: $scope.email,
                        password: $scope.password
                    };

                    Auth.signup(formData, successAuth, function (res) {
                        $rootScope.error = res.error || 'Failed to sign up.';
                    })
                };


                $scope.tokenClaims = Auth.getTokenClaims();
            }])
})();
