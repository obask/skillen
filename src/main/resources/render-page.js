
"use strict";


// don't use network in test mode
var ajaxGetJson = function(url0, data, successCallback) {
    console.log("ajaxGetJson" + url0);
};

var slotsNum = 42;

// TODO select some slot to check sidebar render

_internalSlotsData = Immutable.fromJS(MOCK_CONFIG);
_internalSlotsData = _internalSlotsData.set("navigation", Immutable.Map());

console.log("config loaded", _internalSlotsData.toJS());
var request = { groups: ["Flume", "Parquetify"], zoom: undefined, time: undefined };

var tmp = React.createElement(MainFetch, { url: "/main", request: request });
//var result = ReactDOMServer.renderToStaticMarkup(tmp);

//console.log(result);

//console.log(JSON.stringify(_internalSlotsData.toJS()));


//document.getElementById('content').innerHTML = "<div> dsadasdsa </div>";


// workbench
//var data = Immutable.fromJS(MOCK_CONFIG);
//var tmp = React.createElement(Workbench, {caption: data.get("caption"), text: data.get("text")});
//ReactDOM.render(tmp, document.getElementById('content'));


console.log("OK")
