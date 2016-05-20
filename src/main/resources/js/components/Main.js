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

var MainFetch = React.createClass({
    displayName: "MainFetch",

    getInitialState: function () {
        return {
            data: SlotsStore.getAll()
        };
    },

    _onChange: function() {
        this.setState({
            data: SlotsStore.getAll()
        })
    },

    componentDidMount: function() {
        SlotsStore.on(CHANGE_EVENT, this._onChange)
    },

    componentWillUnmount: function() {
        SlotsStore.removeListener(CHANGE_EVENT, this._onChange)
    },

    componentWillMount: function () {
        var nextProps = this.props;
        AppDispatcher.handleLoadText({
            url: nextProps.url,
            zoom: nextProps.request.zoom,
            time: nextProps.request.time
        })
    },

    componentWillReceiveProps: function (nextProps) {
        AppDispatcher.handleLoadText({
            url: nextProps.url,
            zoom: nextProps.request.zoom,
            time: nextProps.request.time
        })
    },

    render: function () {
        console.log("this.state.data.toJS()");
        var tmp = this.state.data.get("workbench");
        if (tmp === undefined) {
        console.log("return React.DOM.div()");
            return React.DOM.div()
        }
        // else
        return React.DOM.div(null,
            React.createElement(Workbench, {
                workbench: tmp,
                breadcrumbs: Immutable.Seq("workbench")
            })
        )
    }
});
