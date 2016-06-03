package main

import (
	"io"
	"net/http"
	"encoding/json"
	"fmt"

	"github.com/garyburd/redigo/redis"
)


//{
//caption: "dSADAS  sad ADSa",
//text: [
//"Say "
//,{t: "hel",
//w: "hello",
//c: "word"}
//,", my little"
//," friend"
//]
//}


var CONN redis.Conn

type Work struct {
	Caption      string    `json:"caption"`
	Text []interface{}     `json:"text"`
}

type Token struct {
	T      string    `json:"t"`
	W      string    `json:"w"`
	C      string    `json:"c"`
}


func hello(w http.ResponseWriter, r *http.Request) {

	res, _ := redis.String(CONN.Do("GET", "ololo"))

	io.WriteString(w, res)
}


func uploadBook(w http.ResponseWriter, r *http.Request) {

	res, _ := redis.String(CONN.Do("GET", "ololo"))

	io.WriteString(w, res)

	var tmp map[string]interface{}
	json.NewDecoder(r.Body).Decode(&tmp)

	fmt.Println(tmp)

}


func workbench(w http.ResponseWriter, r *http.Request) {
	tok := Token{T: "dsad", W: "dsadsa", C: "dsads"}
	tmp := []interface{}{"dasdas", tok, "dsad"}
	user := Work{Caption: "US123", Text: tmp}
	w.Header().Set("Content-Type", "application/json; charset=UTF-8")
	if err := json.NewEncoder(w).Encode(&user); err != nil {
		panic(err)
	}

}


func main() {

	CONN, _ = redis.Dial("tcp", ":6379")

	fmt.Println("BEGIN")
	localPath := "/Users/oleg/IdeaProjects/skillen/src/main/resources/"
	http.Handle("/static/", http.StripPrefix("/static/", http.FileServer(http.Dir(localPath))))
	http.HandleFunc("/api/upload-book", uploadBook)
	//http.HandleFunc("/api/", hello)
	http.HandleFunc("/workbench/", workbench)
	panic(http.ListenAndServe(":8000", nil))

}
