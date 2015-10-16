(function () {
    'use strict';

    angular
        .module('app')
        .controller('UserPanelController', ['$scope', '$rootScope', '$state', '$http', '$localStorage', 'urls', function ($scope, $rootScope, $state, $http, $localStorage, urls) {


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

            var init = function () {
                $scope.currentTab = 'challenges';
                $state.go('user.challenges');

                // populating some default values for a user
                $scope.user = {
                    "_id": "",
                    "firstName": "FirstName",
                    "lastName": "LastName",
                    "username": "",
                    "email": "",
                    "gravatarUrl": "http://www.gravatar.com/avatar/00000000000000000000000000000000",
                    "admin": false
                };

                var payload = {};
                payload.token = $localStorage.token;

                $http.post(urls.BASE_API + $localStorage.userId, payload).success(
                    function (data) {
                        angular.forEach(data, function (value, key) {
                            if (($scope.user[key]) || ($scope.user[key] == '')) {
                                $scope.user[key] = value;
                            }
                        });

                    }).error(
                    function (error) {
                        console.error('<--------- ERROR ----------->');
                        $rootScope.logout();
                    }
                );
            };

            var currentStateEqualTo = function (tab) {

                return $state.is(tab.route);
            };

            $scope.switchTab = function (tab) {
                console.log(tab);
                if (!currentStateEqualTo(tab) && !tab.disabled) {
                    $state.go(tab.route);
                }
            };

            init();

        }]);

})();
