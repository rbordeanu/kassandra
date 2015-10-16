(function () {
    'use strict';

    angular
        .module('app')
        .controller('EditorController', ['$scope', '$stateParams', '$localStorage', '$http', 'urls', '$state',
            function ($scope, $stateParams, $localStorage, $http, urls, $state) {
            var themeData = [
                ["Chrome"         ],
                ["Clouds"         ],
                ["Crimson Editor" ],
                ["Dawn"           ],
                ["Dreamweaver"    ],
                ["Eclipse"        ],
                ["GitHub"         ],
                ["IPlastic"       ],
                ["Solarized Light"],
                ["TextMate"       ],
                ["Tomorrow"       ],
                ["XCode"          ],
                ["Kuroir"],
                ["KatzenMilch"],
                ["SQL Server"           , "sqlserver"               , "light"],
                ["Ambiance"             , "ambiance"                , "dark"],
                ["Chaos"                , "chaos"                   , "dark"],
                ["Clouds Midnight"      , "clouds_midnight"         , "dark"],
                ["Cobalt"               , "cobalt"                  , "dark"],
                ["idle Fingers"         , "idle_fingers"            , "dark"],
                ["krTheme"              , "kr_theme"                , "dark"],
                ["Merbivore"            , "merbivore"               , "dark"],
                ["Merbivore Soft"       , "merbivore_soft"          , "dark"],
                ["Mono Industrial"      , "mono_industrial"         , "dark"],
                ["Monokai"              , "monokai"                 , "dark"],
                ["Pastel on dark"       , "pastel_on_dark"          , "dark"],
                ["Solarized Dark"       , "solarized_dark"          , "dark"],
                ["Terminal"             , "terminal"                , "dark"],
                ["Tomorrow Night"       , "tomorrow_night"          , "dark"],
                ["Tomorrow Night Blue"  , "tomorrow_night_blue"     , "dark"],
                ["Tomorrow Night Bright", "tomorrow_night_bright"   , "dark"],
                ["Tomorrow Night 80s"   , "tomorrow_night_eighties" , "dark"],
                ["Twilight"             , "twilight"                , "dark"],
                ["Vibrant Ink"          , "vibrant_ink"             , "dark"]
            ];

            var lang_dict = {
                'c': 'c_cpp',
                'cpp': 'c_cpp',
                'java': 'java',
                'js': 'javascript'
            };

            $scope.challengeAnswer = {
                taskId: $stateParams.taskId,
                userId: $localStorage.userId,
                content: ""
            };

            $scope.editorThemes = themeData.map(function (data) {
                var name = data[1] || data[0].replace(/ /g, "_").toLowerCase();
                return {
                    caption: data[0],
                    theme: "ace/theme/" + name,
                    type: data[2] == "dark" ? "Dark" : "Bright",
                    name: name
                };
            });

            var value = [
                "// recursive Fibonacci",
                "function myFunction(x) {",
                "    if (x < 1) {",
                "        return 0;",
                "    } else if (x === 1) {",
                "        return 1;",
                "    } else {",
                "        return myFunction(x - 1) + myFunction(x - 2);",
                "    }",
                "}"].join("\n");

            var value2 = [
                "// iterative fibonacci",
                "function fib(n) {",
                "    var a = 0, b = 1, t;",
                "    while (n-- > 0) {",
                "        t = a;",
                "        a = b;",
                "        b += t;",
                "        console.log(a);",
                "    }",
                "    return a;",
                "}"].join("\n");

            $scope.currentLanguage = 'js';

            $scope.currentColumn = 1;
            $scope.currentLine = 1;

            $scope.onThemeChanged = function (theme) {
                editor.setTheme(theme.theme);
                console.log(theme);
            };

            $scope.onLanguageChanged = function (language) {
                editor.getSession().setMode("ace/mode/" + lang_dict[language]);
                console.log(language);
            };

            var editor = ace.edit("editor");
            editor.setTheme("ace/theme/clouds");
            editor.getSession().setMode("ace/mode/javascript");

            editor.getSession().setValue(value);

            editor.getSession().selection.on('changeCursor', function (e) {
                var selStart = editor.getSelectionRange().start;
                $scope.currentLine = selStart.row + 1;
                $scope.currentColumn = selStart.column + 1;
                $scope.$evalAsync();
            });

            var init = function () {
                console.log($stateParams);

                $scope.currentProblem = $stateParams.body;

            };

            $scope.submit = function () {
                var bd = $('<div class="modal-backdrop fade in"><div class="loading-spinner"></div></div>');
                bd.appendTo(angular.element(document).find('body'));

                var code = editor.getValue();
                console.log(code);

                $scope.challengeAnswer.content = code;

                $http.put(urls.BASE + '/result/answerCoding', $scope.challengeAnswer).success(
                    function (result) {
                        console.log(result);

                        $('#myModal .modal-body').html("<p>Congratulations, you completed the challenge!</p>" +
                            "<p>Your score is: <strong>" + result.score + "</strong></p><p>You have done better than <strong>" +
                            result.percentage + "%</strong> of the people who took this test.</p>");

                        $('#myModal').modal('show');

                        setTimeout(function() {
                            bd.remove();
                            $state.go('user.challenges', {challengeCompleted: true});

                            $scope.$evalAsync();
                        }, 200);

                    }).error(
                    function (error) {
                        bd.remove();
                        console.error(error);
                    }
                );

            };

            init();

        }]);

})();
