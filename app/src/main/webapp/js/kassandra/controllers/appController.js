(function () {
    'use strict';

    angular
        .module('app')
        .controller('appCtrl', ['$scope', '$rootScope', 'Auth', function ($scope, $rootScope, Auth) {
            $rootScope.token = null;
            $scope.logout = function () {
                Auth.logout(function () {
                    window.location = "#/logout";
                });
            };
        }]);
})();
