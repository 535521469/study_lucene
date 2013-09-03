package test1;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermContext;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Search1 {

	private static Directory directory = null;

	static {
		String filePath = new StringBuilder("c:").append(File.separatorChar)
				.append("test").append(File.separatorChar).append("lucene1")
				.toString();
		try {
			// directory = new SimpleFSDirectory(new File(filePath));
			directory = FSDirectory.open(new File(filePath));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {

		ScoreDoc[] docs = searchDoc();

		for (ScoreDoc scoreDoc : docs) {
			System.out.println(scoreDoc.score + "..." + scoreDoc.doc);
		}
	}

	private static ScoreDoc[] searchDoc() throws IOException {

		Query q = new TermQuery(new Term("name", "jose"));
		
		BooleanQuery bq = new BooleanQuery();
		bq.add(q, Occur.MUST);
		Query q2 = new TermQuery(new Term("address", "haimen"));
		bq.add(q2,Occur.MUST);
		
		DirectoryReader dr = DirectoryReader.open(directory);
		IndexSearcher is = new IndexSearcher(dr);

		TopScoreDocCollector topCollector = TopScoreDocCollector
				.create(3, true);

		is.search(q, topCollector);

		ScoreDoc[] docs = topCollector.topDocs().scoreDocs;
		
		System.out.println(docs.length);
		
		return docs;
	}

}
