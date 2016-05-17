/**
 * This file provided by Facebook is for non-commercial testing and evaluation purposes only.
 * Facebook reserves all rights not expressly granted.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * FACEBOOK BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

var CommentBox = React.createClass({
  loadCommentsFromServer: function() {
    $.ajax({
      url: this.props.url,
      dataType: 'json',
      cache: false,
      success: function(data) {
        this.setState({data: data});
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(this.props.url, status, err.toString());
      }.bind(this)
    });
  },
  getInitialState: function() {
    return {data: []};
  },
  componentDidMount: function() {
    this.loadCommentsFromServer();
  },
  render: function() {
    return (
      <div >
        <ProductTable data={this.state.data} />
      </div>
    );
  }
});


var ProductTable = React.createClass({
    render: function() {
        var rows = [];
        var lastCategory = null;
        this.props.data.forEach(function(product) {
            rows.push(<ProductRow product={product} key={product.key} />);
        }.bind(this));
        return (
    <table className="table table-striped">
        <thead>
        <tr>
            <th>#</th>
            <th>Header</th>
            <th>Header</th>
            <th>Header</th>
            <th>Header</th>
        </tr>
        </thead>
            <tbody>
                {rows}
            </tbody>
        </table>
        );
    }
});


var ProductRow = React.createClass({
    render: function() {
        return (
            <tr>
                <td><a href={this.props.product.url}> {this.props.product.text} </a> </td>
                <td>{this.props.product.author}</td>
                <td>Empty</td>
                <td>Empty</td>
            </tr>
        );
    }
});


//var CommentForm = React.createClass({
//  handleSubmit: function(e) {
//    e.preventDefault()
//    var author = React.findDOMNode(this.refs.author).value.trim()
//    var text = React.findDOMNode(this.refs.text).value.trim()
//    if (!text || !author) {
//      return;
//    }
//    this.props.onCommentSubmit({author: author, text: text})
//    React.findDOMNode(this.refs.author).value = ''
//    React.findDOMNode(this.refs.text).value = ''
//  },
//  render: function() {
//    return (
//      <form className="commentForm" onSubmit={this.handleSubmit}>
//        <input type="text" placeholder="Your name" ref="author" />
//        <input type="text" placeholder="Say something..." ref="text" />
//        <input type="submit" value="Post" />
//      </form>
//    )
//  }
//});


var ProblemTable = React.createClass({
    render: function() {
        console.log(this.props)
        var rows = this.props.data.text.map(function(row, idx) {
                                            return (<p>{row}</p>);
                                            });
        return (
            <div>
            <h2> {this.props.data.header} </h2>
            {rows}
            <h4> ---------- </h4>
            </div>
        )
    }
});

var ProblemInfo = React.createClass({
  loadInfoFromServer: function() {
    $.ajax({
      url: this.props.url,
      dataType: 'json',
      cache: false,
      success: function(data) {
        this.setState({data: data});
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(this.props.url, status, err.toString())
      }.bind(this)
    })
  },
  getInitialState: function() {
    return {data: {text: []}};
  },
  componentDidMount: function() {
    this.loadInfoFromServer()
  },
  render: function() {
    return (
      <div >
        <ProblemTable data={this.state.data} />
      </div>
    )
  }
});

console.log("one:", document.referrer)
console.log("two:", window.location.pathname)
console.log("three:", window.location.href)
console.log("four:", window.location.hash)


routie('hello', function() {
    //this gets called when hash == #hello
    React.render(
      <ProblemInfo url="/assets/problem1.json" pollInterval={0} />,
      document.getElementById('content')
    )
});


routie('*', function() {

    alert("ERROR: wrong route!")

});

// Silly router:
//if (window.location.pathname == "/skillen") {
//    React.render(
//      <ProblemInfo url="/assets/problem1.json" pollInterval={0} />,
//      document.getElementById('content')
//    )
//} else if (window.location.pathname.indexOf("/skillen/problems") === 0) {
//    React.render(
//      <CommentBox url="/js/comments.json" pollInterval={0} />,
//      document.getElementById('content')
//    )
//} else if (window.location.pathname.indexOf("/skillen/problems/") === 0) {
//    React.render(
//      <ProblemInfo url="/js/problem1.json" pollInterval={0} />,
//      document.getElementById('content')
//    )
//} else {
//    React.render(
//      <h1> ERROR! path {window.location.pathname} doesnt exists </h1>,
//      document.getElementById('content')
//    )
//}
