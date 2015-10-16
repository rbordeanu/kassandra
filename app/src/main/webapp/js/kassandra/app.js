(function () {
    'use strict';

    angular.module('app', [
        'ui.router',
        'ngStorage',
        'angular-loading-bar'
    ])
        .constant('urls', {
            BASE: 'http://bucd472:8180/kassandra',
            BASE_API: 'http://bucd472:8180/kassandra/api/user/'
        })
        .config(['$stateProvider', '$urlRouterProvider', '$httpProvider',
            function ($stateProvider, $urlRouterProvider, $httpProvider) {
                $stateProvider
                    .state('home', {
                        url: '/',
                        templateUrl: 'partials/home.html',
                        controller: 'HomeController'
                    })
                    .state('logout', {
                        url: '/logout',
                        templateUrl: 'partials/logout.html',
                        controller: 'HomeController'
                    })
                    .state('user', {
                        url: '/user',
                        templateUrl: 'partials/userPanel.html',
                        controller: 'UserPanelController'
                    })
                    .state('user.profile', {
                        templateUrl: 'partials/user.profile.html',
                        controller: 'ProfileController'
                    })
                    .state('user.challenges', {
                        templateUrl: 'partials/user.challenges.html',
                        controller: 'ChallengesController',
                        params: {
                            challengeCompleted: null
                        }
                    })
                    .state('user.challenges.quiz', {
                        templateUrl: 'partials/user.challenges.quiz.html',
                        controller: 'QuizController',
                        params: {
                            taskId: null,
                            questions: null
                        }
                    })
                    .state('user.challenges.coding', {
                        templateUrl: 'partials/user.challenges.coding.html',
                        controller: 'EditorController',
                        params: {
                            taskId: null,
                            body: null
                        }
                    })
                    .state('user.submissions', {
                        templateUrl: 'partials/user.submissions.html',
                        controller: 'SumbmissionsController'
                    })
                    .state('user.leaderboard', {
                        templateUrl: 'partials/user.leaderboard.html',
                        controller: 'LeaderboardController'
                    })
                    .state('user.discussion', {
                        templateUrl: 'partials/user.discussion.html',
                        controller: 'DiscussionController'
                    })
                    .state('user.social', {
                        templateUrl: 'partials/user.social.html',
                        controller: 'SocialController'
                    })
                    .state('user.tasks', {
                        templateUrl: 'partials/user.tasks.html',
                        controller: 'TasksController'
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
                /**/
                if ($localStorage.token == null) {
                    if (next.templateUrl === "partials/restricted.html") {
                        $location.path("/signin");
                    }
                }

                //$location.path("/user");
            });
        });
})();

