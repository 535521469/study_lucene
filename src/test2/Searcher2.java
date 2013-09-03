package test2;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.SingleTermsEnum;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.CachingWrapperFilter;
import org.apache.lucene.search.ConstantScoreQuery;
import org.apache.lucene.search.FieldValueFilter;
import org.apache.lucene.search.FilteredQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.MultiPhraseQuery;
import org.apache.lucene.search.MultiTermQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.MultiTermQuery.RewriteMethod;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

public class Searcher2 {

	private static Directory directory = null;

	static {
		String pathname = new StringBuilder("c:").append(File.separatorChar)
				.append("test").append(File.separatorChar).append("lucene2")
				.toString();

		try {
			directory = FSDirectory.open(new File(pathname));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {

		for (ScoreDoc doc : getScoreDocs()) {
			System.out.println(doc.toString());
		}
	}

	private static ScoreDoc[] getScoreDocs() throws IOException {
		IndexSearcher is = new IndexSearcher(DirectoryReader.open(directory));

		// Query fq = new TermQuery(new Term("address","hunan"));

		// Query fq = new FilteredQuery(
		// new TermQuery(new Term("address", "hunan")),
		// new CachingWrapperFilter(new FieldValueFilter("id")));

		// PhraseQuery fq = new PhraseQuery();
		// fq.add(new Term("address", "jiangsu"), 0);
		// fq.add(new Term("address", "province"), 1);
		// fq.add(new Term("address", "haimen"), 4);
		// for (int i : fq.getPositions()) {
		// System.out.println(i);
		// }

		// Query fq = new ConstantScoreQuery(new TermQuery(new Term("address",
		// "hunan")));

		// Query fq = new MatchAllDocsQuery();

		// NumericRangeQuery fq = NumericRangeQuery.newIntRange("id", 1, 2,
		// true,
		// true);
		// System.out.println(fq.getPrecisionStep());

		// TermRangeQuery fq = TermRangeQuery.newStringRange("name", "cor",
		// "jose2", true, true);
		// System.out.println(fq.getLowerTerm().utf8ToString());

		// Term t = new Term("name", "l");
		// Query fq = new TermQuery(t);
		// PrefixQuery fq = new PrefixQuery(t);

		// Term t = new Term("name", "luohiu");
		// FuzzyQuery fq = new FuzzyQuery(t, 2, 1);

		Term t0 = new Term("address", "jiangsu");
		Term t1 = new Term("address", "jiangxi");
		Term t2 = new Term("address", "province");
		MultiPhraseQuery fq = new MultiPhraseQuery();
		fq.add(new Term[] { t0, t1 });
		fq.add(t2);
		TopScoreDocCollector results = TopScoreDocCollector.create(10, true);

		is.search(fq, results);

		return results.topDocs().scoreDocs;

	}
}