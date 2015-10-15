(function () {
    'use strict';

    angular
        .module('app')
        .controller('UserPanelController', ['$scope', '$state', '$http', '$localStorage', 'urls', function ($scope, $state, $http, $localStorage, urls) {


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

            var init = function(){
                $scope.currentTab = 'profile';
                $state.go('user.profile');

                $scope.user = {
                    "_id": "testUserId",
                    "firstName": "Alex",
                    "lastName": "Last",
                    "username": "aturbatu",
                    "email": "aturbatu@misys.com",
                    "password": "passSecre",
                    "gravatarUrl": "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xfp1/v/t1.0-1/c28.116.675.675/s160x160/11822746_1085060741521915_7892215979629432132_n.jpg?oh=d97a2df88054b86d22fa3c5923e890e3&oe=569895CD&__gda__=1451952774_e86712f063ffe82e12d24d4ccb57962f",
                    "admin": true
                };

                var payload = {};
                payload.token = $localStorage.token;

                $http.post(urls.BASE_API + $localStorage.userId, payload).success(
                    function(data){
                        $scope.user = data;
                    }).error(
                    function(error){
                        console.error(error);
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
