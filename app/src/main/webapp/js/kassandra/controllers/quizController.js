(function () {
    'use strict';

    angular
        .module('app')
        .controller('QuizController', ['$scope', '$stateParams', function ($scope, $stateParams) {

            $scope.quizQuestions = [];

            $scope.numberOfQuestions = 0;

            var updateProgressBar = function (step) {
                var percent = (step / $scope.numberOfQuestions) * 100;
                $('.progress-bar').css({width: percent + '%'});
            };

            var init = function () {
                console.log($stateParams);
                $scope.quizQuestions = $stateParams.task;

                $scope.numberOfQuestions = $scope.quizQuestions.length;

                updateProgressBar(1);

                $scope.currentQuestion = $scope.quizQuestions[0];
                console.log($scope.currentQuestion);
            };

            $scope.goNext = function () {
                var nextId = parseInt($scope.currentQuestion.id, 10) + 1;

                var result = $scope.quizQuestions.filter(function(item) {
                    return item.id === ('' + nextId);
                });

                $scope.currentQuestion = result[0];
                updateProgressBar(nextId);

                console.log(nextId);
            };

            $scope.goBack = function () {
                var prevId = parseInt($scope.currentQuestion.id, 10) - 1;

                var result = $scope.quizQuestions.filter(function(item) {
                    return item.id === ('' + prevId);
                });

                $scope.currentQuestion = result[0];
                updateProgressBar(prevId);
            };

            $scope.isFirstQuestion = function () {
                return ($scope.currentQuestion.id === '1');
            };

            $scope.isLastQuestion = function () {
                return ($scope.currentQuestion.id === $scope.quizQuestions[$scope.quizQuestions.length - 1].id);
            };

            init();
        }]);

})();

