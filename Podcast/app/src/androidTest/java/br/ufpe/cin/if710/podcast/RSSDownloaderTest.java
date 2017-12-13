package br.ufpe.cin.if710.podcast;

import android.content.Intent;
import android.os.IBinder;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ServiceTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ProviderTestCase2;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.ufpe.cin.if710.podcast.db.PodcastProvider;

/**
 * Created by filipejordao on 12/12/17.
 */
@RunWith(AndroidJUnit4.class)
public class RSSDownloaderTest extends ProviderTestCase2 {

    public RSSDownloaderTest() {
        super(PodcastProvider.class, "br.ufpe.cin.if710.podcast.feed");
    }

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        setContext(InstrumentationRegistry.getTargetContext());
    }

    @After
    @Override
    public void tearDown() throws Exception{
        super.tearDown();
    }

    @Test
    public void test() throws Exception{
        RSSDownloader downloader = new RSSDownloader();

        boolean success = downloader.startDownload("http://leopoldomt.com/if710/fronteirasdaciencia.xml", this.getMockContext());

        assertEquals("The download was not successful",true,success);
    }
}
