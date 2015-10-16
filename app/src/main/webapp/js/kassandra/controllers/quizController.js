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
            };

            init();
        }]);

})();

