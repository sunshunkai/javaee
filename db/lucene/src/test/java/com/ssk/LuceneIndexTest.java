package com.ssk;

import com.ssk.pojo.Book;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.xml.builders.SpanQueryBuilder;
import org.apache.lucene.search.*;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.TFIDFSimilarity;
import org.apache.lucene.search.spans.SpanNearQuery;
import org.apache.lucene.search.spans.SpanQuery;
import org.apache.lucene.search.spans.SpanTermQuery;
import org.apache.lucene.store.*;
import org.junit.Test;


import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class LuceneIndexTest {
    @Test
    public  void  testCreateIndex() throws  Exception{
        //1.采集数据
        List<Book> bookList = new ArrayList<>();
        Book  booka  = new Book();
        booka.setId(1);
        booka.setDesc("Lucene Core is a Java library providing powerful indexing and search features, as well as spellchecking, hit highlighting and advanced analysis/tokenization capabilities. The PyLucene sub project provides Python bindings for Lucene Core. ");
        booka.setName("Lucene");
        booka.setPrice(100.45f);
        bookList.add(booka);

        Book  bookb  = new Book();
        bookb.setId(11);
        bookb.setDesc("Solr is highly scalable, providing fully fault tolerant distributed indexing, search and analytics. It exposes Lucene's features through easy to use JSON/HTTP interfaces or native clients for Java and other languages. ");
        bookb.setName("Solr");
        bookb.setPrice(320.45f);
        bookList.add(bookb);
        Book  bookc  = new Book();
        bookc.setId(21);
        bookc.setDesc("The Apache Hadoop software library is a framework that allows for the distributed processing of large data sets across clusters of computers using simple programming models.");
        bookc.setName("Hadoop");
        bookc.setPrice(620.45f);
        bookList.add(bookc);
        //2.创建Document 文档对象
        List<Document> documents  = new ArrayList<>();
        for (Book book:bookList){
            Document  document  = new Document();
            // 给document 添加Field
            document.add(new TextField("id",book.getId().toString(),Field.Store.YES));
            document.add(new TextField("name",book.getName(),Field.Store.YES));
            document.add(new TextField("price",book.getPrice().toString(),Field.Store.YES));
            document.add(new TextField("desc",book.getDesc(),Field.Store.YES));
            documents.add(document);
        }


        //3.创建Analyzer 分词器 对文档进行分词
        Analyzer  analyzer  = new StandardAnalyzer();
        // 创建Directory   和 IndexWriterConfig 对象
        Directory  directory = FSDirectory.open(Paths.get("lucene/index"));
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        // 4.创建IndexWriter 写入对象
        IndexWriter  indexWriter = new IndexWriter(directory,indexWriterConfig);
        // 添加文档对象
        for (Document doc : documents) {
            indexWriter.addDocument(doc);
        }
        // 释放资源
        indexWriter.close();
    }
    @Test
    public  void  testSearchIndex()throws  Exception {

        //1.创建Query 搜索对象
        // 创建分词器
        Analyzer analyzer = new StandardAnalyzer();
        // 创建搜索解析器
        QueryParser queryParser = new QueryParser("id", analyzer);
        // 创建搜索对象
        Query query = queryParser.parse("desc:java OR name:solr");
        //2.创建Directory 流对象  指定索引库位置
        //Directory directory = FSDirectory.open(Paths.get("lucene/index"));
        //Directory directory = SimpleFSDirectory.open(Paths.get("lucene/index"));
        //Directory directory = NIOFSDirectory.open(Paths.get("lucene/index"));
        Directory directory = MMapDirectory.open(Paths.get("lucene/index"));
        //3.创建索引读取对象
        IndexReader indexReader = DirectoryReader.open(directory);
        //4.创建索引搜索对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        indexSearcher.setSimilarity(new ClassicSimilarity());
        System.out.println(indexSearcher.getSimilarity(true));
       // indexSearcher.setSimilarity(indexSearcher.getSimilarity(false));

        //5.执行搜索 返回结果集 TopDocs
        TopDocs topDocs = indexSearcher.search(query, 2);
        System.out.println("查询到的数据总条数:" + topDocs.totalHits);
        // 获取排序的文档
        ScoreDoc[] docs = topDocs.scoreDocs;
        //6.解析结果集
        for (ScoreDoc scoreDoc : docs) {
            // 获取文档id
            int docID = scoreDoc.doc;
            Document doc = indexSearcher.doc(docID);
            System.out.println("score:" + scoreDoc.score);

            System.out.println("docId:" + docID);
            System.out.println("bookId:" + doc.get("id"));
            System.out.println("name:" + doc.get("name"));
            System.out.println("price:" + doc.get("price"));
            System.out.println("desc:" + doc.get("desc"));
            System.out.println();
        }
        indexReader.close();
    }

    @Test
    public void createIndex() throws Exception {
        // 1.采集数据
        Book  booka  = new Book();
        List<Book> bookList = new ArrayList<Book>();
        booka.setId(1);
        booka.setDesc("Lucene Core is a Java library providing powerful indexing and search features, as well as spellchecking, hit highlighting and advanced analysis/tokenization capabilities. The PyLucene sub project provides Python bindings for Lucene Core. ");
        booka.setName("Lucene");
        booka.setPrice(100.45f);
        bookList.add(booka);

        Book  bookb  = new Book();
        bookb.setId(11);
        bookb.setDesc("Solr is highly scalable, providing fully fault tolerant distributed indexing, search and analytics. It exposes Lucene's features through easy to use JSON/HTTP interfaces or native clients for Java and other languages. ");
        bookb.setName("Solr");
        bookb.setPrice(320.45f);
        bookList.add(bookb);
        Book  bookc  = new Book();
        bookc.setId(21);
        bookc.setDesc("The Apache Hadoop software library is a framework that allows for the distributed processing of large data sets across clusters of computers using simple programming models.");
        bookc.setName("Hadoop");
        bookc.setPrice(620.45f);
        bookList.add(bookc);

        // 2.将采集到的数据封装到Document对象中
        List<Document> docList = new ArrayList<>();
        Document document;
        for (Book book : bookList) {
            document = new Document();
            // IntPoint  分词 索引 不存储 存储结合  StoredField
            Field id = new IntPoint("id", book.getId());
            System.out.println(id.fieldType().tokenized() + ":" + id.fieldType().stored());
            Field id_v  = new StoredField("id", book.getId());
            // 分词、索引、存储 TextField
            Field name = new TextField("name", book.getName(), Field.Store.YES);
            // 分词、索引、不存储 但是是数字类型，所以使用FloatPoint
            Field price = new FloatPoint("price", book.getPrice());
            // 分词、索引、不存储 TextField
            Field desc = new TextField("desc",
                    book.getDesc(), Field.Store.YES);

            // 将field域设置到Document对象中

            document.add(id);
            document.add(id_v);
            document.add(name);
            document.add(price);
            document.add(desc);

            docList.add(document);
        }
        //3.创建Analyzer 分词器 对文档进行分词
        Analyzer  analyzer  = new StandardAnalyzer();
        // 创建Directory   和 IndexWriterConfig 对象
        Directory  directory = FSDirectory.open(Paths.get("lucene/index"));

        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);

        // 4.创建IndexWriter 写入对象
        IndexWriter  indexWriter = new IndexWriter(directory,indexWriterConfig);

        // 添加文档对象
        for (Document doc : docList) {
            indexWriter.addDocument(doc);
        }
        // 释放资源
        indexWriter.close();
    }


}
