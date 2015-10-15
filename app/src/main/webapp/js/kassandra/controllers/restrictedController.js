(function () {
    'use strict';

    angular.module('app')
        .controller('RestrictedController', ['$rootScope', '$scope', 'Data', function ($rootScope, $scope, Data) {
            Data.getRestrictedData(function (res) {
                $scope.data = res.data;
            }, function () {
                $rootScope.error = 'Failed to fetch restricted content.';
            });
            Data.getApiData(function (res) {
                $scope.api = res.data;
            }, function () {
                $rootScope.error = 'Failed to fetch restricted API content.';
            });
        }]);
})();
