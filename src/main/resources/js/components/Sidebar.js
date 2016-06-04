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

    FIELDS: Immutable.fromJS({
        "#about": "About",
        "#add-text": "Add text",
        "#list-books": "Library",
        "#learn-words": "Vocabulary",
        "#workbench": "Workbench"
    }),

    render: function () {
        var selected = this.props.hash;
        console.log(selected);
        return React.DOM.div({style: {"background-color": "beige"}},
            React.DOM.h4(null, "SIDEBAR:"),
            React.DOM.ul(null,
                this.FIELDS.map(function (label, k) {
                    var cl = (this.props.hash == k) ? "red" : "black";
                    return React.DOM.li({key: k},
                        React.DOM.a({href: k, style: {color: cl}}, label)
                    )
                }.bind(this))
            )
        )
    }

});
