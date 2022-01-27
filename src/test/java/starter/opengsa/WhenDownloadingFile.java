package starter.opengsa;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.thucydides.core.annotations.Managed;

@ExtendWith(SerenityJUnit5Extension.class)
public class WhenDownloadingFile {
	
	/**
	 * Define the webdriver instance to be used for these tests
	 */
	@Managed(driver = "chrome", options = "headless")
	WebDriver driver;

	/**
	 * Navigation actions. This is a UIInteraction class so it will be instantiated
	 * automatically by Serenity.
	 */
	NavigateActions navigate;
	
	/**
	 * A page object representing a OpenGsa article that is currently appearing in
	 * the browser. Page Objects are automatically initialized by Serenity.
	 */
	
	OpenGsaPage openGsaPage;
	
	public static final String FILE_DOWNLAOD_PATH = System.getProperty("user.dir") + "\\downloads\\";
	
	public static final String OPEN_GSA_OPPS_URL = "https://open.gsa.gov/api/opportunities-api/";
	
	public static final Map<String, String> FILE_MAP = new HashMap<>();
	
	static {
		FILE_MAP.put("Opportunity Management REST Workflow Updated.pdf", "42a9af93c428cb5ff140aa3592931e168509290a3a4814e3853989413e87138c");
	}
	
	@Test
	void verifyDownloadedFileName() {
		navigate.openPage(OPEN_GSA_OPPS_URL);
		String sourceUrl = openGsaPage.getHref();
		String fullFileName = getFullFileNameFromUrl(sourceUrl);
		Serenity.reportThat("Check the URL file name is same?", 
				() -> assertThat(FILE_MAP.containsKey(fullFileName)).isTrue()
		);
		
	}


	@Test
	void downloadFile() {
		navigate.openPage(OPEN_GSA_OPPS_URL);
		Date date = new Date();
		String sourceUrl = openGsaPage.getHref();
		String fullFileName = getFullFileNameFromUrl(sourceUrl);
		String destination = FILE_DOWNLAOD_PATH + fullFileName;
		downloadFileFromUrl(sourceUrl, destination);
		Serenity.reportThat("Check if the new file is downloaded", 
				() -> assertThat(FileUtils.isFileNewer(new File(destination), date)).isTrue()
		);
	
	}
	
	
	@Test
	void verifyFileChecksum_SHA256() {
		
		navigate.openPage(OPEN_GSA_OPPS_URL);
		String sourceUrl = openGsaPage.getHref();
		String fullFileName = getFullFileNameFromUrl(sourceUrl);
		String destination = FILE_DOWNLAOD_PATH + fullFileName;
		
		Serenity.reportThat("Check if downloaded file has the same SHA256 checksum", 
				() -> assertThat(getFileChecksumSHA256(destination)).isEqualTo(FILE_MAP.get(fullFileName))
		);
	}


	private static String getFullFileNameFromUrl(String sourceUrl) {

		URLCodec codec = new URLCodec();
		String fullFileName = null;
		try {
			fullFileName = codec.decode(FilenameUtils.getName(sourceUrl).toString());
		} catch (DecoderException e) {
			e.printStackTrace();
		}

		return fullFileName;

	}

	private static void downloadFileFromUrl(String sourceUrl, String destination) {

		int CONNECT_TIMEOUT = 10000;
        int READ_TIMEOUT = 10000;
        try {
            FileUtils.copyURLToFile(new URL(sourceUrl), new File(destination), CONNECT_TIMEOUT, READ_TIMEOUT);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	private static String getFileChecksumSHA256(String destination) {
		String checksumSHA256 = null;
		try {
			checksumSHA256 = DigestUtils.sha256Hex(new FileInputStream(destination));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return checksumSHA256;
	}

}
