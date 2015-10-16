(function () {
    'use strict';

    angular
        .module('app')
        .controller('LeaderboardController', ['$scope', '$http', 'urls', function ($scope, $http, urls) {

            var init = function() {

                $http.get(urls.BASE + '/result/leaderboard').success(
                    function (result) {
                        console.log(result);
                        $scope.board = result;

                    }).error(
                    function (error) {
                        console.error(error);
                    }
                );

            };

            init();
        }]);

})();
