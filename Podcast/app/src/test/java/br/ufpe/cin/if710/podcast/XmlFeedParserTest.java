package br.ufpe.cin.if710.podcast;

/**
 * Created by filipejordao on 10/12/17.
 */

import android.content.ClipData;

import org.junit.Test;
import org.mockito.Mockito;
import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.if710.podcast.domain.ItemFeed;
import br.ufpe.cin.if710.podcast.domain.XmlFeedParser;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class XmlFeedParserTest {

    XmlPullParser xpp = Mockito.mock(XmlPullParser.class);
    @Test
    public void RssParserTest() throws Exception{
        ItemFeed[] correctItems = { new ItemFeed("Ciência e Pseudociência","http://dstats.net/download/http://www6.ufrgs.br/frontdaciencia/arquivos/Fronteiras_da_Ciencia-E001-Ciencia-e-Pseudociencia-07.06.2010.mp3","Sun, 20 Jun 2010 10:40:05 GMT","Programa 1","",0)};
        String xmlString = "";

        when(xpp.next()).thenReturn(2)

                .thenReturn(2)
                .thenReturn(2)
                .thenReturn(4)
                .thenReturn(2)
                .thenReturn(4)
                .thenReturn(2)
                .thenReturn(4)
                .thenReturn(2)
                .thenReturn(3)
                .thenReturn(3);

        when(xpp.getEventType()).thenReturn(2)

                .thenReturn(2)
                .thenReturn(2)
                .thenReturn(2)
                .thenReturn(2)
                .thenReturn(2)
                .thenReturn(3)
                .thenReturn(3);

        when(xpp.getAttributeValue(null,"url")).thenReturn("http://dstats.net/download/http://www6.ufrgs.br/frontdaciencia/arquivos/Fronteiras_da_Ciencia-E001-Ciencia-e-Pseudociencia-07.06.2010.mp3");
        when(xpp.getText()).thenReturn("Ciência e Pseudociência").thenReturn("Programa 1").thenReturn("Sun, 20 Jun 2010 10:40:05 GMT");
        when(xpp.getName()).thenReturn("channel").thenReturn("item")
        .thenReturn("title").thenReturn("description").thenReturn("pubDate").thenReturn("enclosure");
        List<ItemFeed> items = XmlFeedParser.parse(xmlString,xpp);

        assertEquals(correctItems.length,items.size());

        for (int i = 0; i < items.size(); i ++){
            ItemFeed item = items.get(i);
            ItemFeed correctItem = correctItems[i];

            assertEquals(correctItem.getTitle(), item.getTitle());
            assertEquals(correctItem.getDescription(),item.getDescription());
            assertEquals(correctItem.getLink(),item.getLink());
            assertEquals(correctItem.getPubDate(),item.getPubDate());
            assertEquals(correctItem.getPosition(),item.getPosition());
        }
    }
}
