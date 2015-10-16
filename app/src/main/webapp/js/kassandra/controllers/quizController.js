(function () {
    'use strict';

    angular
        .module('app')
        .controller('QuizController', ['$scope', '$stateParams', '$localStorage', '$http', 'urls', '$state',
            function ($scope, $stateParams, $localStorage, $http, urls, $state) {

            $scope.quizQuestions = [];

            $scope.numberOfQuestions = 0;

            $scope.quizAnswer = {
                taskId: $stateParams.taskId,
                userId: $localStorage.userId,
                answers: {}
            };

            var updateProgressBar = function (step) {
                var percent = (step / $scope.numberOfQuestions) * 100;
                $('.progress-bar').css({width: percent + '%'});
            };

            var init = function () {
                console.log($stateParams);
                $scope.quizQuestions = $stateParams.questions;

                $scope.numberOfQuestions = $scope.quizQuestions.length;

                updateProgressBar(1);

                $scope.currentQuestion = $scope.quizQuestions[0];
                console.log($scope.currentQuestion);
            };

            $scope.goNext = function () {
                removeRadioChecks();

                var nextId = parseInt($scope.currentQuestion.id, 10) + 1;

                var result = $scope.quizQuestions.filter(function(item) {
                    return item.id === ('' + nextId);
                });

                $scope.currentQuestion = result[0];
                updateProgressBar(nextId);

                //console.log(nextId);
            };

            $scope.goBack = function () {
                removeRadioChecks();

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

            function removeRadioChecks() {
                $('.custom-radio label').each(function() {
                    $(this).removeClass('checked checkedHover checkedFocus')
                });
            }

            $scope.selectAnswer = function (answer) {
                removeRadioChecks();

                $('#radio' + answer + ' label').addClass('checked');

                $scope.quizAnswer.answers[$scope.currentQuestion.id] = answer;

                console.log($scope.quizAnswer);
            };

            $scope.submitQuiz = function () {

                $http.put(urls.BASE + '/result/answerQuestion', $scope.quizAnswer).success(
                    function (result) {
                        console.log(result);

                        $('#myModal .modal-body').html("<p>Congratulations, you completed the challenge!</p>" +
                            "<p>Your score is: <strong>" + result.score + "</strong></p><p>You have done better than <strong>" +
                            result.percentage + "%</strong> of the people who took this test.</p>");

                        $('#myModal').modal('show');

                        $state.go('user.challenges', {challengeCompleted: true});
                    }).error(
                    function (error) {
                        console.error(error);
                    }
                );
            };

            init();

        }]);
})();

