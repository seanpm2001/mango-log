/**
 * 
 */
package test.java;

import static org.junit.Assert.*;

import java.util.regex.Matcher;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.io.Text;
import org.junit.Test;

import ua_parser.Parser;

import com.mozilla.custom.parse.LogLine;
import com.mozilla.udf.ParseDateForQtr;

/**
 * @author aphadke
 *
 */
public class TestLogLine {


	@Test
	public void testUDF() {
		ParseDateForQtr pdfw = new ParseDateForQtr();
		assertEquals("2014-Q2", pdfw.evaluate(new Text("2014-05-28:10:34:22 +0000")).toString());
	}

	/**
	 * Test method for {@link com.mozilla.custom.parse.LogLine#validateSplit(java.lang.String)}.
	 */
	@Test
	public void testValidateSplitNullInput() {
		LogLine ll;
		try {
			ll = new LogLine(null, null);
		} catch (Exception e) {
			assertNotNull(e.getMessage());
		}

	}

	/**
	 * Test method for {@link com.mozilla.custom.parse.LogLine#validateSplit(java.lang.String)}.
	 */
	@Test
	public void testValidateSplitDmoLine() {

		String v = "2620:101:8003:200:e5af:bd49:7c0f:ceaa - dmo=10.8.81.215.1367977569087177; __utma=150903082.1262655689.1367998579.1367998579.1367998579.1; __utmz=150903082.1367998579.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none) [10/May/2013:00:00:24 -0700] \"GET /?product=firefox-20.0.1-complete&os=win&lang=zh-TW HTTP/1.1\" 302 422 \"-\" \"Mozilla/5.0 (Windows NT 6.1; rv:14.0) Gecko/20100101 Firefox/14.0.1\" \"-\"";		
		LogLine ll;
		try {
			ll = new LogLine(v, "-");
			assertEquals(ll.getSplitCount(), 12);
		} catch (Exception e) {
			assertNull(e.getMessage());
		}
	}


	/**
	 * Test method for {@link com.mozilla.custom.parse.LogLine#validateSplit(java.lang.String)}.
	 */
	@Test
	public void testValidateSplitAmoLine() {
		String v = "187.22.120.82 addons.mozilla.org - [29/Jul/2013:00:19:57 +0000] \"GET /blocklist/3/%7Bec8030f7-c20a-464f-9b0e-13a3a9e97384%7D/22.0/Firefox/20130618035212/WINNT_x86-msvc/pt-BR/release/Windows_NT%206.1/default/default/21/75/1/ HTTP/1.1\" 200 8228 \"-\" \"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:22.0) Gecko/20100101 Firefox/22.0\" \"__utma=150903082.120566378.1363530862.1369872736.1374269744.4; __utmz=150903082.1363530862.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); optimizelySegments=%7B%22197878113%22%3A%22none%22%2C%22197755715%22%3A%22direct%22%2C%22197870141%22%3A%22ff%22%2C%22197869430%22%3A%22false%22%2C%22246002457%22%3A%22campaign%22%2C%22246073290%22%3A%22ff%22%2C%22245984388%22%3A%22false%22%2C%22246073289%22%3A%22none%22%7D; optimizelyEndUserId=oeu1373232329475r0.13031423857226065; optimizelyBuckets=%7B%7D\" 272";		
		LogLine ll;
		try {
			ll = new LogLine(v, "addons.mozilla.org");
			assertEquals(ll.getSplitCount(), 12);

			Matcher m = ll.getDbSplitPattern();

			for (int i = 1; i <= m.groupCount(); i++) {
				System.err.println(m.group(i));
			}

		} catch (Exception e) {
			assertNull(e.getMessage());
		}
	}

	/**
	 * Test method for {@link com.mozilla.custom.parse.LogLine#validateSplit(java.lang.String)}.
	 */
	@Test
	public void testValidateSplitMarketplaceLine() {
		String v = "62.158.149.18 marketplace.firefox.com - [17/Jun/2013:23:52:11 -0700] \"GET /packaged.webapp HTTP/1.1\" 304 669 \"-\" \"Mozilla/5.0 (Mobile; rv:18.0) Gecko/18.0 Firefox/18.0\" \"-\" \"DNT:1\" \"X-MOZ-B2G-DEVICE:GP-KEON X-MOZ-B2G-MCC:- X-MOZ-B2G-MNC:- X-MOZ-B2G-SHORTNAME:- X-MOZ-B2G-LONGNAME:-\"";
		LogLine ll;
		try {
			ll = new LogLine(v, "marketplace.mozilla.org");
			assertEquals(ll.getSplitCount(), 12);

			Matcher m = ll.getDbSplitPattern();

			for (int i = 1; i <= m.groupCount(); i++) {
				System.err.println(m.group(i));
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertNull(e.getMessage());
		}

	}

	/**
	 * Test method for {@link com.mozilla.custom.parse.LogLine#validateSplit(java.lang.String)}.
	 */
	@Test
	public void testValidateSplitVamoLine() {
		String v = "1.1.1.1 versioncheck.addons.mozilla.org - [14/May/2013:07:00:09 -0700] \"GET /update/VersionCheck.php?reqVersion=2&id={972ce4c6-7e08-4474-a285-3208198ce6fd}&version=10.0&maxAppVersion=10.0&status=userEnabled&appID={ec8030f7-c20a-464f-9b0e-13a3a9e97384}&appVersion=10.0&appOS=WINNT&appABI=x86-msvc&locale=en-US&currentAppVersion=10.0&updateType=112&compatMode=normal HTTP/1.1\" 200 525 \"-\" \"Mozilla/5.0 (Windows NT 6.1; rv:10.0) Gecko/20100101 Firefox/10.0\" \"__utma=150903082.733308021.1361633654.1361633654.1361633654.1; __utmz=150903082.1361633654.1.1.utmcsr=firstrow1.eu|utmccn=(referral)|utmcmd=referral|utmcct=/watch/155095/1/watch-manchester-united-vs-queens-park-rangers.html\"";		
		LogLine ll;
		try {
			ll = new LogLine(v, "versioncheck.addons.mozilla.org");
			assertEquals(ll.getSplitCount(), 12);
		} catch (Exception e) {
			assertNull(e.getMessage());
		}
	}

	/**
	 * Test method for {@link com.mozilla.custom.parse.LogLine#validateSplit(java.lang.String)}.
	 */
	@Test
	public void testInvalidSplitLine() {
		String v = "1.1.1.1 - - [12/May/2013:07:44:57 -0700] \"GET /bundles/bing/addon/bing.xpi HTTP/1.1\" 200 24166 - \"-\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:19.0) Gecko/20100101 Firefox/19.0\"";		
		LogLine ll;
		try {
			ll = new LogLine(v,"-");
			assertEquals(ll.getSplitCount(), -1);
		} catch (Exception e) {
			assertNull(e.getMessage());
		}
	}


	/**
	 * Test method for {@link com.mozilla.custom.parse.LogLine#getRawTableString()}.
	 */
	@Test
	public void testGetRawTableString() {

		String v = "1.1.1.1 versioncheck.addons.mozilla.org - [14/May/2013:07:00:09 -0700] \"GET /update/VersionCheck.php?reqVersion=2&id={972ce4c6-7e08-4474-a285-3208198ce6fd}&version=10.0&maxAppVersion=10.0&status=userEnabled&appID={ec8030f7-c20a-464f-9b0e-13a3a9e97384}&appVersion=10.0&appOS=WINNT&appABI=x86-msvc&locale=en-US&currentAppVersion=10.0&updateType=112&compatMode=normal HTTP/1.1\" 200 525 \"-\" \"Mozilla/5.0 (Windows NT 6.1; rv:10.0) Gecko/20100101 Firefox/10.0\" \"__utma=150903082.733308021.1361633654.1361633654.1361633654.1; __utmz=150903082.1361633654.1.1.utmcsr=firstrow1.eu|utmccn=(referral)|utmcmd=referral|utmcct=/watch/155095/1/watch-manchester-united-vs-queens-park-rangers.html\"";		
		LogLine ll;
		try {
			ll = new LogLine(v, "versioncheck.addons.mozilla.org");
			assertEquals(ll.getSplitCount(), 12);
			assertNotNull(ll.getRawTableString());
		} catch (Exception e) {
			assertNull(e.getMessage());
		}

	}

	@Test
	public void testCorrectaddDate() {

		String v = "1.1.1.1 versioncheck.addons.mozilla.org - [14/May/2013:07:00:09 -0700] \"GET /update/VersionCheck.php?reqVersion=2&id={972ce4c6-7e08-4474-a285-3208198ce6fd}&version=10.0&maxAppVersion=10.0&status=userEnabled&appID={ec8030f7-c20a-464f-9b0e-13a3a9e97384}&appVersion=10.0&appOS=WINNT&appABI=x86-msvc&locale=en-US&currentAppVersion=10.0&updateType=112&compatMode=normal HTTP/1.1\" 200 525 \"-\" \"Mozilla/5.0 (Windows NT 6.1; rv:10.0) Gecko/20100101 Firefox/10.0\" \"__utma=150903082.733308021.1361633654.1361633654.1361633654.1; __utmz=150903082.1361633654.1.1.utmcsr=firstrow1.eu|utmccn=(referral)|utmcmd=referral|utmcct=/watch/155095/1/watch-manchester-united-vs-queens-park-rangers.html\"";		
		LogLine ll;
		try {
			ll = new LogLine(v, "versioncheck.addons.mozilla.org");
			assertEquals(ll.getSplitCount(), 12);
			assertTrue(ll.addDate());

			assertEquals(ll.getDbLogLine().get(0), "2013-05-14:14:00:09 +0000");
			assertEquals(ll.getDbLogLine().get(1), ll.getDbSplitPattern().group(4));

		} catch (Exception e) {
			assertNull(e.getMessage());
		}

	}

	@Test
	public void testInCorrectaddDate() {

		String v = "1.1.1.1 versioncheck.addons.mozilla.org - [14/May/2013:07:00:09 -0700] \"GET /update/VersionCheck.php?reqVersion=2&id={972ce4c6-7e08-4474-a285-3208198ce6fd}&version=10.0&maxAppVersion=10.0&status=userEnabled&appID={ec8030f7-c20a-464f-9b0e-13a3a9e97384}&appVersion=10.0&appOS=WINNT&appABI=x86-msvc&locale=en-US&currentAppVersion=10.0&updateType=112&compatMode=normal HTTP/1.1\" 200 525 \"-\" \"Mozilla/5.0 (Windows NT 6.1; rv:10.0) Gecko/20100101 Firefox/10.0\" \"__utma=150903082.733308021.1361633654.1361633654.1361633654.1; __utmz=150903082.1361633654.1.1.utmcsr=firstrow1.eu|utmccn=(referral)|utmcmd=referral|utmcct=/watch/155095/1/watch-manchester-united-vs-queens-park-rangers.html\"";		
		LogLine ll;
		try {
			ll = new LogLine(v, "versioncheck.addons.mozilla.org");
			assertEquals(ll.getSplitCount(), 12);
			assertTrue(ll.addDate());

			assertNotSame(ll.getDbLogLine().get(0), "2013-05-15:14:00:09 +0000");

		} catch (Exception e) {
			assertNull(e.getMessage());
		}

	}

	@Test
	public void testaddFilename() {
		String v = "1.1.1.1 - - [11/May/2013:21:05:57 -0700] \"GET /google/3.0.5/update/win32/en-US/firefox-3.0.5.complete.mar HTTP/1.1\" 200 9857215 - \"-\" \"Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.8.1.20) Gecko/20081217 Firefox/2.0.0.20\"";

		LogLine ll;
		try {
			ll = new LogLine(v, "-");
			//System.err.println(ll.getSplitCount());
			assertEquals(ll.getSplitCount(), 12);
			Matcher m = ll.getDbSplitPattern();

			for (int i = 1; i <= m.groupCount(); i++) {
				System.err.println(m.group(i));
			}
			assertFalse(ll.addFilename(null));
			assertFalse(ll.addFilename(""));
		} catch (Exception e) {
			assertNull(e.getMessage());
		}

	}

	@Test
	public void testJobDate() {
		String d = "";
		String input = "/user/aphadke/temp_intermediate_raw_anon_logs-addons.mozilla.org-2013-06-03/";
		String[] splitSlash = StringUtils.split(input, "/");
		if (splitSlash.length > 0) {
			String[] splitDash = StringUtils.split(splitSlash[2],"-");
			System.err.println(splitDash[2] + "-" + splitDash[3] + "-" + splitDash[4]);
		}


	}

}
