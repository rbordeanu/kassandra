(function () {
    'use strict';

    angular
        .module('app')
        .controller('ChallengesController', ['$scope', '$http', 'urls', function ($scope, $http, urls) {

            var init = function() {

                $http.get(urls.BASE + '/task/get').success(
                    function(data){
                        console.log(data);
                    }).error(
                    function(error){
                        console.error(error);
                    }
                )
            };

            init();

        }]);

})();
