(function () {
    'use strict';

    angular
        .module('app')
        .controller('EditorController', ['$scope', function ($scope) {
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
            $scope.currentTheme = 'chrome';

            $scope.currentColumn = 1;
            $scope.currentLine = 1;

            $scope.onThemeChanged = function (theme) {
                editor.setTheme(theme.theme);
                console.log(theme);
            };

            $scope.onLanguageChanged = function (language) {
                console.log(language);
            };

            var editor = ace.edit("editor");
            editor.setTheme("ace/theme/chrome");
            editor.getSession().setMode("ace/mode/javascript");

            editor.getSession().setValue(value);

            editor.getSession().selection.on('changeCursor', function (e) {
                var selStart = editor.getSelectionRange().start;
                $scope.currentLine = selStart.row + 1;
                $scope.currentColumn = selStart.column + 1;
                $scope.$evalAsync();
            });
        }]);

})();
