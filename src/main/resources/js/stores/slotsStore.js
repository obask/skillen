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


var _internalSlotsData = undefined;

var _internalLastSelectedSlotPath = undefined;

var _internalNavigationData = {};

var SlotsStore = Object.assign({}, EventEmitter.prototype, {

    getAll: function () {
        return _internalSlotsData;
    },

    LOAD_TEXT: function (payload) {
        ajaxGetJson(
            /*url=*/ "/workbench",
            /*data=*/ {
                group: payload.params.group,
                zoom: payload.params.zoom,
                time: payload.params.time
            },
            /*success=*/ function (data) {
                var newState = _internalSlotsData;
                newState = newState.setIn(payload.breadcrumbs.concat("text"), Immutable.Seq());

                //newState = newState.updateIn(payload.breadcrumbs.concat("rows"), function (wfs) {
                //    return wfs.map(function (workflow) {
                //        return workflow.set("rows", Immutable.List())
                //    })
                //});
                newState = newState.mergeDeepIn(payload.breadcrumbs, Immutable.fromJS(data));
                _internalSlotsData = newState;
                SlotsStore.emit(CHANGE_EVENT);
            }.bind(this)
        );
    },

    LOAD_NAVIGATION: function (payload) {
        _internalSlotsData = _internalSlotsData.set("navigation", Immutable.fromJS({}));
        SlotsStore.emit(CHANGE_EVENT);
    },
    MODAL_BOX: function (payload) {
        _internalSlotsData = _internalSlotsData.set("modalBox", Immutable.Map(payload.params));
    }

});

AppDispatcher.register(function (payload) {

    switch (payload.actionType) {

        case TodoConstants.CLEAR_SELECTION:
            SlotsStore.CLEAR_SELECTION(payload);
            break;

        case TodoConstants.TODO_UPDATE:
            SlotsStore.TODO_UPDATE(payload);
            break;

        case TodoConstants.FOCUS_ON_SLOT:
            SlotsStore.FOCUS_ON_SLOT(payload);
            break;

        case TodoConstants.RECTANGLE_UPDATE:
            SlotsStore.RECTANGLE_UPDATE(payload);
            break;

        case TodoConstants.LOAD_TEXT:
            SlotsStore.LOAD_TEXT(payload);
            break;

        case TodoConstants.LOAD_NAVIGATION:
            SlotsStore.LOAD_NAVIGATION(payload);
            break;

        case TodoConstants.MODAL_BOX:
            SlotsStore.MODAL_BOX(payload);
            break;

        // add more cases for other actionTypes, like TODO_DESTROY, etc.
    }


    return true; // No errors. Needed by promise in Dispatcher.
});
