(function () {
    'use strict';

    angular
        .module('app')
        .controller('UserPanelController', ['$scope', '$state', function ($scope, $state) {


            $scope.userTabs = [
                {
                    name: 'Profile',
                    id: 'profile'
                },
                {
                    name: 'Challenges',
                    id: 'challenges'
                },
                {
                    name: 'Submissions',
                    id: 'submissions'
                },
                {
                    name: 'Leaderboard',
                    id: 'leaderboard'
                },
                {
                    name: 'Discussion',
                    id: 'discussion',
                    disabled: true
                },
                {
                    name: 'Social',
                    id: 'social'
                },
                {
                    name: 'Tasks',
                    id: 'tasks',
                    hasNotifications: true,
                    notifications: 3
                }
            ];

            $scope.currentTab = 'profile';

            $scope.switchTab = function(tab) {
                console.log(tab);
            }

        }]);

})();
