(function () {
    'use strict';

    angular
        .module('app')
        .controller('RegisterController', RegisterController);

    RegisterController.$inject = ['UserService', '$location', '$rootScope'];
    function RegisterController(UserService, $location, $rootScope) {
        var vm = this;

        vm.register = register;

        function register() {
            vm.dataLoading = true;
            UserService.Create(vm.user)
                .then(function (response) {
                    if (response.success) {
                        console.log('Registration successful');
                        $location.path('/login');
                    } else {
                        console.log(response.message);
                        vm.dataLoading = false;
                    }
                });
        }
    }

})();