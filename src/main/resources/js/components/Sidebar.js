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


var Sidebar = React.createClass({
    displayName: "Sidebar",

    TMP: Immutable.fromJS({
        selected: "ololo"
    }),

    render: function () {
        var fields = Immutable.fromJS(["about", "add text", "my texts", "learn words"]);
        return React.DOM.div({style: {"background-color": "beige"}},
            "SIDEBAR:",
            React.DOM.ul(null,
                fields.map(function (label, i) {
                    return React.DOM.li({key: i}, label)
                }.bind(this)).valueSeq().toJS()
            )
        )
    }

});
