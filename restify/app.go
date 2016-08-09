package main

import (
	"io"
	"net/http"
	"encoding/json"
	"fmt"

	"github.com/garyburd/redigo/redis"
	"strings"
	"unicode"
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

const BOOKS_COLLECTION = "texts"

var CONN redis.Conn

type Workbench struct {
	Caption  string    `json:"caption"`
	Text 	 []interface{}     `json:"text"`
}

type Book struct {
	Url      string    `json:"url"`
	Caption  string    `json:"caption"`
	Text 	 []interface{}     `json:"text"`
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

	var tmp map[string]interface{}
	json.NewDecoder(r.Body).Decode(&tmp)

	name := tmp["inputValue"].(string)
	text := tmp["textAreaValue"].(string)

	url := bookCaptionToUrl(name)

	if _, err := CONN.Do("SADD", BOOKS_COLLECTION, url); err != nil {
		panic(err)
	}
	if _, err := CONN.Do("HDEL", url); err != nil {
		panic(err)
	}
	// if the url matches with other collection error will be returned
	if _, err := CONN.Do("HSET", url, "caption", name); err != nil {
		panic(err)
	}
	if _, err := CONN.Do("HSET", url, "text", text); err != nil {
		panic(err)
	}

	fmt.Println(name, text)

	//fmt.Println(xx)
}

func listBooks(w http.ResponseWriter, r *http.Request) {

	//var tmp map[string]interface{}
	//json.NewDecoder(r.Body).Decode(&tmp)
	//name := tmp["inputValue"].(string)

	if res, err := redis.Strings(CONN.Do("SMEMBERS", BOOKS_COLLECTION)); err != nil {
		panic(err)
	} else {
		w.Header().Set("Content-Type", "application/json")
		tmp := []Book{}
		for _,x := range(res) {
			book := Book{Url: x}
			tmp = append(tmp, book)
		}
		json.NewEncoder(w).Encode(tmp)
		fmt.Println("listBooks:", res)
	}
}

func workbench(w http.ResponseWriter, r *http.Request) {
	tok := Token{T: "dsad", W: "dsadsa", C: "dsads"}
	tmp := []interface{}{"dasdas", tok, "dsad"}
	user := Workbench{Caption: "US123", Text: tmp}
	w.Header().Set("Content-Type", "application/json; charset=UTF-8")
	if err := json.NewEncoder(w).Encode(&user); err != nil {
		panic(err)
	}

}

func bookCaptionToUrl(ss string) string {
	mapper := func(r rune) rune {
		if unicode.IsLetter(r) || unicode.IsNumber(r) {
			return r
		} else {
			return '-'
		}
	}
	return strings.Trim(strings.ToLower(strings.Map(mapper, ss)), "-")
}


func main() {

	CONN, _ = redis.Dial("tcp", ":6379")

	fmt.Println("BEGIN")
	localPath := "/Users/oleg/IdeaProjects/skillen/src/main/resources/"
	http.Handle("/static/", http.StripPrefix("/static/", http.FileServer(http.Dir(localPath))))
	http.HandleFunc("/api/upload-book", uploadBook)
	http.HandleFunc("/api/texts", listBooks)
	//http.HandleFunc("/api/", hello)


	http.HandleFunc("/workbench/", workbench)
	panic(http.ListenAndServe(":8000", nil))

}
