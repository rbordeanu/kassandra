(function () {
    'use strict';

    angular
        .module('app', ['ui.router', 'ngCookies'])
        .config(config)
        .run(run);

    config.$inject = ['$stateProvider', '$urlRouterProvider'];
    function config($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state('home', {
                url:'/home',
                templateUrl: 'home.html'
            })
            .state('login', {
                url:'/login',
                controller: 'LoginController',
                templateUrl: 'login.html',
                controllerAs: 'vm'
            })
            .state('signup', {
                url:'/signup',
                controller: 'RegisterController',
                templateUrl: 'signup.html',
                controllerAs: 'vm'
            });
        $urlRouterProvider.otherwise('/home');
    }

    run.$inject = ['$rootScope', '$location', '$cookieStore', '$http'];
    function run($rootScope, $location, $cookieStore, $http) {
        // keep user logged in after page refresh
        $rootScope.globals = $cookieStore.get('globals') || {};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
        }

        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            // redirect to login page if not logged in and trying to access a restricted page
            var restrictedPage = $.inArray($location.path(), ['/login', '/register']) === -1;
            var loggedIn = $rootScope.globals.currentUser;
            if (restrictedPage && !loggedIn) {
                $location.path('/login');
            }
        });
    }

})();