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

var Library = React.createClass({
    displayName: "Library",

    getInitialState: function () {
        return {
            data: null
        }
    },

    componentWillMount: function () {
        var nextProps = this.props;
        this.handleLoadBooksFromServer();
    },

    componentWillReceiveProps: function (nextProps) {
        this.handleLoadBooksFromServer();
    },

    handleLoadBooksFromServer: function () {
        ajaxGetJson("/api/list-books",
            {},
            function(res) {
                console.log("LOADED");
                this.setState({data: res})
            }.bind(this)
        )
    },

    render: function () {
        return React.DOM.div(null,
            React.createElement(Sidebar, this.props.navigation),
            // if
            (this.state.data == null)
            ? // then
            React.DOM.div(null,
                React.DOM.h1(null, "Library"),
                React.DOM.br(),
                React.DOM.h4(null, "loading..."))
            : // else
            React.createElement(FetchedLibrary, {books: this.state.data})
        )
    }

});


var FetchedLibrary = React.createClass({
    displayName: "FetchedLibrary",

    render: function () {
        console.log("this.state");
        return React.DOM.div({className: "container"},
            React.DOM.h1(null, "Library2"),
            React.DOM.ul(null,
                Immutable.fromJS(this.props.books).map(function (text, title) {
                        return React.DOM.li(null, title)
                })
            )
        )
    }

});


























