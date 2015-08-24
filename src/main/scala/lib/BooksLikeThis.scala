package lib

/**
 * Copyright Manning Publications Co.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific lan
 */

import org.apache.lucene.document.Document
import org.apache.lucene.index.{DirectoryReader, IndexReader, Term}
import org.apache.lucene.search.BooleanQuery
import org.apache.lucene.search.TopDocs
import org.apache.lucene.search.IndexSearcher
import org.apache.lucene.search.TermQuery
import org.apache.lucene.search.BooleanClause
import org.apache.lucene.store.Directory
import org.apache.lucene.store.FSDirectory
import java.io.File
import org.apache.lucene.queries.mlt.MoreLikeThisQuery

object BooksLikeThis {

}

//  def main(args: Array[String]) {
//    val dir: Directory = FSDirectory.open(new File("/Users/oleg/kalinin/idx"))
//    val reader: IndexReader = DirectoryReader.open(dir)
//    val numDocs: Int = reader.maxDoc
//    val blt: BooksLikeThis = new BooksLikeThis(reader)
//
////    val mlt: MoreLikeThisQuery = new MoreLikeThisQuery("success", Array(""))
////    val likeQuery = mlt.like(hits[i].doc)
////    val results : TopDocs = isearcher.search(likeQuery)
//
//
//
//    {
//      var i: Int = 0
//      while (i < numDocs) {
//        {
//          System.out.println()
//          val doc: Document = reader.document(i)
//          System.out.println(doc.get("title"))
//          val docs: Array[Document] = blt.docsLike(i, 10)
//          if (docs.length == 0) {
//            System.out.println("  None like this")
//          }
//          for (likeThisDoc <- docs) {
//            System.out.println("  -> " + likeThisDoc.get("title"))
//          }
//        }
//        i += 1
//      }
//    }
//    reader.close()
//    dir.close()
//  }
//}

//class BooksLikeThis {
//  def this(reader: IndexReader) {
//    this()
//    this.reader = reader
//    searcher = new IndexSearcher(reader)
//  }

//  def docsLike(id: Int, max: Int): Array[Document] = {
//
//
//
//    val doc: Document = reader.document(id)
//    val authors: Array[String] = doc.getValues("author")
//    val vector = reader.getTermFreqVector(id, "subject")
//    val subjectQuery: BooleanQuery = new BooleanQuery
//    import scala.collection.JavaConversions._
//    for (vecTerm <- vector.getTerms) {
//      val tq: TermQuery = new TermQuery(new Term("subject", vecTerm))
//      subjectQuery.add(tq, BooleanClause.Occur.SHOULD)
//    }
//    val likeThisQuery: BooleanQuery = new BooleanQuery
//    likeThisQuery.add(subjectQuery, BooleanClause.Occur.SHOULD)
//    likeThisQuery.add(new TermQuery(new Term("isbn", doc.get("isbn"))), BooleanClause.Occur.MUST_NOT)
//    val hits: TopDocs = searcher.search(likeThisQuery, 10)
//    var size: Int = max
//    if (max > hits.scoreDocs.length) size = hits.scoreDocs.length
//    val docs: Array[Document] = new Array[Document](size)
//    {
//      var i: Int = 0
//      while (i < size) {
//        {
//          docs(i) = reader.document(hits.scoreDocs(i).doc)
//        }
//          i += 1
//      }
//    }
//    docs
//  }
//
//  private var reader: IndexReader = null
//  private var searcher: IndexSearcher = null
//}

/*
#1 Iterate over every book
#2 Look up books like this
#3 Boosts books by same author
#4 Use terms from "subject" term vectors
#5 Create final query
#6 Exclude current book
*/