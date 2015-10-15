(function () {
    'use strict';

    angular.module('app', [
        'ui.router',
        'ngStorage',
        'angular-loading-bar'
    ])
        .constant('urls', {
            BASE: 'http://localhost:8080/kassandra',
            BASE_API: 'http://localhost:8080/kassandra/api/user' // bucd491:8080/kassandra/signin
        })
        .config(['$stateProvider', '$urlRouterProvider', '$httpProvider',
            function ($stateProvider, $urlRouterProvider, $httpProvider) {
                $stateProvider
                    .state('home', {
                        url: '/',
                        templateUrl: 'partials/home.html',
                        controller: 'HomeController'
                    })
                    .state('user', {
                        url: '/user',
                        templateUrl: 'partials/userPanel.html',
                        controller: 'UserPanelController'
                    })
                    .state('profile', {
                        url: '/profile',
                        templateUrl: 'partials/userPanel.html',
                        controller: 'UserPanelController'
                    })
                    .state('challenges', {
                        url: '/challenges',
                        templateUrl: 'partials/challenges.html',
                        controller: 'ChallengesController'
                    })
                    .state('submissions', {
                        url: '/submissions',
                        templateUrl: 'partials/userPanel.html',
                        controller: 'UserPanelController'
                    })
                    .state('leaderboard', {
                        url: '/leaderboard',
                        templateUrl: 'partials/userPanel.html',
                        controller: 'UserPanelController'
                    })
                    .state('discussion', {
                        url: '/discussion',
                        templateUrl: 'partials/userPanel.html',
                        controller: 'UserPanelController'
                    })
                    .state('social', {
                        url: '/social',
                        templateUrl: 'partials/userPanel.html',
                        controller: 'UserPanelController'
                    })
                    .state('tasks', {
                        url: '/tasks',
                        templateUrl: 'partials/userPanel.html',
                        controller: 'UserPanelController'
                    })
                    .state('signin', {
                        url: '/signin',
                        templateUrl: 'partials/signin.html',
                        controller: 'HomeController'
                    })
                    .state('signup', {
                        url: '/signup',
                        templateUrl: 'partials/signup.html',
                        controller: 'HomeController'
                    })
                    .state('testUserId', {
                        url: '/testUserId',
                        templateUrl: 'partials/restricted.html',
                        controller: 'RestrictedController'
                    })
                    .state('restricted', {
                        url: '/restricted',
                        templateUrl: 'partials/restricted.html',
                        controller: 'RestrictedController'
                    });
                $urlRouterProvider.otherwise('/');


                $httpProvider.interceptors.push(['$q', '$location', '$localStorage', function ($q, $location, $localStorage) {
                    return {
                        'request': function (config) {
                            config.headers = config.headers || {};
                            if ($localStorage.token) {
                                config.headers.Authorization = 'Bearer ' + $localStorage.token;
                            }
                            return config;
                        },
                        'responseError': function (response) {
                            if (response.status === 401 || response.status === 403) {
                                delete $localStorage.token;
                                $location.path('/signin');
                            }
                            return $q.reject(response);
                        }
                    };
                }]);
            }
        ]).run(function ($rootScope, $location, $localStorage) {
            $rootScope.$on("$locationChangeStart", function (event, next) {
                /**/    if ($localStorage.token == null) {
                 if ( next.templateUrl === "partials/restricted.html") {
                 $location.path("/signin");
                 }
                 }

                //$location.path("/user");
            });
        });
})();