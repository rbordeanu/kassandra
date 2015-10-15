(function () {
    'use strict';

    angular
        .module('app')
        .controller('UserPanelController', ['$scope', '$state', function ($scope, $state) {


            $scope.userTabs = [
                {
                    name: 'Profile',
                    id: 'profile',
                    route: 'user.profile'
                },
                {
                    name: 'Challenges',
                    id: 'challenges',
                    route: 'user.challenges'
                },
                {
                    name: 'Submissions',
                    id: 'submissions',
                    route: 'user.submissions'
                },
                {
                    name: 'Leaderboard',
                    id: 'leaderboard',
                    route: 'user.leaderboard'
                },
                {
                    name: 'Discussion',
                    id: 'discussion',
                    route: 'user.discussion',
                    disabled: true
                },
                {
                    name: 'Social',
                    id: 'social',
                    route: 'user.social'
                },
                {
                    name: 'Tasks',
                    id: 'tasks',
                    route: 'user.tasks',
                    hasNotifications: true,
                    notifications: 3
                }
            ];

            $scope.currentTab = 'profile';

            var currentStateEqualTo = function(tab) {

                return $state.is(tab.route);
            };

            $scope.switchTab = function(tab) {
                console.log(tab);
                if (!currentStateEqualTo(tab) && !tab.disabled) {
                    $state.go(tab.route);
                }
            }

        }]);

})();
