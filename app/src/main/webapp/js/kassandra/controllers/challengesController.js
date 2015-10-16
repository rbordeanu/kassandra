(function () {
    'use strict';

    angular
        .module('app')
        .controller('ChallengesController', ['$scope', '$http', 'urls', '$q', '$state',
            function ($scope, $http, urls, $q, $state) {

            $scope.challenges = [];

            $scope.challengeInProgress = true;

            function getTasks() {
                var deferred = $q.defer();

                $http.get(urls.BASE + '/task/get').success(
                    function (data) {
                        console.log(data);
                        deferred.resolve(data);
                    }).error(
                    function (data) {
                        deferred.reject(data);
                    }
                );

                return deferred.promise;
            }

            var init = function () {

                getTasks().then(function success(data) {
                    $scope.challenges = data;

                    $.each($scope.challenges, function (index, item) {
                        $http.get(urls.BASE + '/result/statistics/' + item._id).success(
                            function (result) {
                                console.log(result);
                                item.submissionsCount = result.submissions;
                                item.taskAccuracy = result.accuracy;
                            }).error(
                            function (error) {
                                console.error(error);
                            }
                        );

                    });

                }, function error(reason) {
                    console.error(reason);
                });
            };

            $scope.isQuiz = function (task) {
                return task.quiz ? "QUIZ" : "CODING";
            };

            $scope.startChallenge = function (task) {
                $scope.challengeInProgress = false;
                if (task.quiz) {
                    $state.go('user.challenges.quiz', {questions: task.body.questions});
                } else {
                    $state.go('user.challenges.coding', {body: task.body});
                }
            };

            init();

        }]);
})();
