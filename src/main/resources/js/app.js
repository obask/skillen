/*
 * Copyright 2015 Collective, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

"use strict";


// http://magic.reactjs.net/htmltojsx.htm
// https://babeljs.io/repl/

var defaultController = function () {
    if (window.location.hash === "" || window.location.hash === "#ui") {
        ReactDOM.render(React.createElement(MainFetch, {request: {}}), document.getElementById('content'));
    } else if (startsWith("#ui?", window.location.hash)) {
        var params = parseParams(window.location.hash.substring("#ui?".length).split("&"));
        var request = {groups: params.groups, zoom: params.zoom, time: params.time};
        ReactDOM.render(React.createElement(CelosMainFetch, {
            url: "/main",
            request: request
        }), document.getElementById('content'))
    } else if (window.location.hash === "#add-text") {
            ReactDOM.render(React.createElement(UploadBook, {navigation: {hash: window.location.hash}}), document.getElementById('content'))
    } else if (window.location.hash === "#about") {
        ReactDOM.render(React.createElement(AboutTab, {navigation: {hash: window.location.hash}}), document.getElementById('content'))
    } else if (startsWith("#library/", window.location.hash)) {
        ReactDOM.render(React.createElement(Library, {navigation: {hash: window.location.hash}}), document.getElementById('content'))
    } else if (window.location.hash === "#library") {
        ReactDOM.render(React.createElement(Library, {navigation: {hash: window.location.hash}}), document.getElementById('content'))
    } else {
        throw "no route for this URL: " + window.location.hash
    }
};

window.addEventListener('hashchange', function () {
    console.log("URL:", window.location.hash);
    defaultController();
});



var STARTUP_DATA = {
    workbench: {
        caption: "QWERTY",
        text: []
        }
};


_internalSlotsData = Immutable.fromJS(STARTUP_DATA);
defaultController();


//// application entry point
//ajaxGetJson(
//    /*url=*/ "/workbench",
//    /*data=*/ {
//    },
//    /*success=*/ (function (data) {
//        // deep merge works fine with empty lists
//    })
//);

//window.onclick = function(e) {
//    // close context menu
//    ReactDOM.render(React.createElement(ContextMenu, {showElement: false}),
//        document.getElementById('contextMenu'));
//    return true;
//};
