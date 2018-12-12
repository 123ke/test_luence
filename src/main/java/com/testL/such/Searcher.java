package com.testL.such;

import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MergePolicy.OneMerge;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Searcher {

	public static void seach(String indexDir, String q) throws Exception {

		Directory dir = FSDirectory.open(Paths.get(indexDir));
		IndexReader redReader = DirectoryReader.open(dir);
		IndexSearcher isSearcher = new IndexSearcher(redReader);
		Analyzer alAnalyzer = new StandardAnalyzer();
		QueryParser parser = new QueryParser("content", alAnalyzer);
		Query query = parser.parse(q);
		long startTime = System.currentTimeMillis();
		TopDocs docs = isSearcher.search(query, 10);
		long endtime = System.currentTimeMillis();
		System.out.println("匹配" + q + ",共花费" + (endtime - startTime) + "毫秒"
				+ "查询到" + docs.totalHits + "个记录");
		for (ScoreDoc ScoreDoc : docs.scoreDocs) {
			Document document = isSearcher.doc(ScoreDoc.doc);
			System.out.println(document.get("fullPath"));
		}
		redReader.close();
	}

	public static void main(String[] args) {
		String indexDir = "E:\\luence";
		String q = "Zygmunt Saloni";

		try {
			seach(indexDir, q);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
