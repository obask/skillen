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

var UploadBook = React.createClass({
    displayName: "UploadBook",


    render: function () {
        console.log("UploadBook");
        // else
        return React.DOM.div(null,
            React.createElement(Sidebar, this.props.navigation),
            React.DOM.div(null,
                //React.createElement(NewComponent1),
                React.createElement(NewComponent3)
                //// upload as a file
                //React.createElement(
                //    "form",
                //    { className: "js_add_form add_block narrow", action: "/save/add/", method: "post", encType: "multipart/form-data" },
                //    React.createElement("input", { type: "hidden", name: "src_type", defaultValue: "file" }),
                //    React.createElement("input", { type: "hidden", name: "retpath", defaultValue: "/u/spetz911/texts/" }),
                //    React.createElement("input", { type: "hidden", name: "MAX_FILE_SIZE", defaultValue: 15728640 }),
                //    React.createElement(
                //        "h2",
                //        null,
                //        "Загрузить файл:"
                //    ),
                //    React.createElement(
                //        "div",
                //        { className: "ctrl ctrl__file" },
                //        React.createElement("input", { type: "file", name: "file", className: "input_file" })
                //    ),
                //    React.createElement(
                //        "div",
                //        { className: "ctrl ctrl__button" },
                //        React.createElement("input", { type: "submit", name: true, defaultValue: "Добавить текст" })
                //    )
                //)
            )
        )
    }
});


var NewComponent1 = React.createClass({
    displayName: "NewComponent1",

    render: function () {
        return React.createElement(
            "form",
            {action: "#save/add/", method: "post", encType: "multipart/form-data" },
            React.createElement("input", { type: "hidden", name: "src_type", defaultValue: "url" }),
            React.createElement("input", { type: "hidden", name: "retpath", defaultValue: "/u/spetz911/texts/" }),
            React.createElement(
                "h2",
                null,
                "Загрузить с URL-адреса:"
            ),
            React.createElement(
                "div",
                { className: "ctrl ctrl__text" },
                React.createElement("input", { id: "url", type: "text", name: "url", className: "input_text box_shadow", placeholder: "http://... или https://...", defaultValue: true })
            ),
            React.createElement(
                "div",
                { className: "ctrl ctrl__button" },
                React.createElement("input", { type: "submit", defaultValue: "Добавить текст" })
            ),
            React.createElement(
                "div",
                { style: { marginTop: 10 }, className: "smaller gray" },
                "Поддерживаются ",
                React.createElement(
                    "span",
                    { className: "hl" },
                    "html-страницы"
                ),
                " и ссылки на ",
                React.createElement(
                    "span",
                    { className: "hl" },
                    "fb2"
                ),
                ", ",
                React.createElement(
                    "span",
                    { className: "hl" },
                    "epub"
                ),
                ", ",
                React.createElement(
                    "span",
                    { className: "hl" },
                    "mobi"
                ),
                ", ",
                React.createElement(
                    "span",
                    { className: "hl" },
                    "azw3"
                ),
                ". Форматы pdf и ps не рекомендуются к использованию, так как разбиение на предложения может работать некорректно."
            )
        );
    }
});


var NewComponent3 = React.createClass({
    displayName: "NewComponent3",

    getInitialState: function() {
        return {inputValue: 'Hello!', textAreaValue: "OLOLO"};
    },
    handleSubmit: function() {
        AppDispatcher.uploadBook(this.state);
        console.log("this.state.inputValue");
        console.log(this.state.inputValue);
        console.log(this.state.textAreaValue);
    },

    render: function () {
        return React.DOM.form({
                onSubmit: this.handleSubmit
            },
            React.DOM.h2(null,
                "Ввести или вставить текст:"
            ),
            React.DOM.div(null,
                React.DOM.input({type: "text", size: 40, maxLength: 255, placeholder: "Заголовок (необязательно)",
                    value: this.state.inputValue,
                    onChange: function (e) {this.setState({inputValue: e.target.value})}.bind(this)
                })
            ),
            React.DOM.div(null,
                React.DOM.textarea({cols: 40, rows: 8, placeholder: "Текст",
                    value: this.state.textAreaValue,
                    onChange: function (e) {this.setState({textAreaValue: e.target.value})}.bind(this)
                })
            ),
            React.DOM.div(null,
                React.DOM.input({type: "submit", defaultValue: "Добавить текст" })
            )
        );
    }
});









