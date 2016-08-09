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

var Workbench = React.createClass({
    displayName: "Workbench",

    render: function () {
        return React.DOM.div(null,
            React.DOM.h1(null, this.props.workbench.get("caption")),
            React.DOM.br(),
            React.createElement(MarkedText, {
                            text: this.props.workbench.get("text"),
                            bc: this.props.breadcrumbs.concat("text")
                        })
        )
    }

});



var MarkedText = React.createClass({
    displayName: "MarkedText",

    TMP: Immutable.fromJS([
        "Dsd asd as dasd a",
        "dsad sa sa ",
        {w: "QQQQ"}
    ]),

    render: function () {
        return React.DOM.div({onContextMenu: this.handleContextMenu},
            //this.TMP.map(function (textOrItem, i) {
                this.props.text.map(function (textOrItem, i) {
                                                 console.log("textOrItem");
                                                 if (typeof(textOrItem) == "string") {
                                                     console.log(textOrItem);
                                                     return textOrItem
                                                 } else {
                                                     console.log(textOrItem.toJS());
                                                     var clazz = textOrItem.get("c");
                                                     return React.DOM.span({key: i, style: {color: "red"}},
                                                             textOrItem.get("w")
                                                             )
                                                 }
                                             }.bind(this)).valueSeq().toJS()
                )
    }

});
