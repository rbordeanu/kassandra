(function () {
    'use strict';

    angular.module('app', [
        'ui.router',
        'ngStorage',
        'angular-loading-bar'
    ])
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
                /*if ($localStorage.token == null) {
                 if ( next.templateUrl === "partials/restricted.html") {
                 $location.path("/signin");
                 }
                 }*/

                $location.path("/user");
            });
        });
})();