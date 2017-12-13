package br.ufpe.cin.if710.podcast.ui;


import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.ufpe.cin.if710.podcast.R;

/**
 * Created by filipejordao on 13/12/17.
 */

@RunWith(AndroidJUnit4.class)
public class UITests {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule(MainActivity.class);

    @Test
    public void checkFirstFourItems_Test(){
        String[] expectedTitles = {"Ciência e Pseudociência","O Homem foi mesmo até a Lua?","Darwin e a Evolução","Vacinas: quando mentiras são perigosas"};
        String[] expectedDates = {"Sun, 20 Jun 2010 10:40:05 GMT","Sun, 20 Jun 2010 10:45:05 GMT","Mon, 21 Jun 2010 10:45:05 GMT","Mon, 28 Jun 2010 10:45:05 GMT"};

        for (int i = 0; i < expectedTitles.length ; i++) {
            onData(anything())
                    .atPosition(i)
                    .onChildView(withId(R.id.item_title))
                    .check(matches(withText(expectedTitles[i])));

            onData(anything())
                    .atPosition(i)
                    .onChildView(withId(R.id.item_date))
                    .check(matches(withText(expectedDates[i])));
        }
    }

    @Test
    public void openDetailsForFirst_Test() {
        String[] expectedTitles = {
                "Ciência e Pseudociência",
                "O Homem foi mesmo até a Lua?",
                "Darwin e a Evolução",
                "Vacinas: quando mentiras são perigosas"
        };

        String[] expectedDescriptions = {
                "Programa 1",
                "Programa 2",
                "Programa 3",
                "Programa 4"
        };

        String[] expectedLinks = {
                "http://dstats.net/download/http://www6.ufrgs.br/frontdaciencia/arquivos/Fronteiras_da_Ciencia-E001-Ciencia-e-Pseudociencia-07.06.2010.mp3",
                "http://dstats.net/download/http://www6.ufrgs.br/frontdaciencia/arquivos/Fronteiras_da_Ciencia-E002-O-homem-na-lua-14.06.2010.mp3",
                "http://dstats.net/download/http://www6.ufrgs.br/frontdaciencia/arquivos/Fronteiras_da_Ciencia-E003-Darwin_e_a_Evolucao-25.09.2009.mp3",
                "http://dstats.net/download/http://www6.ufrgs.br/frontdaciencia/arquivos/Fronteiras_da_Ciencia-E004-Vacinas-28.06.2010.mp3"
        };

        for (int i = 0; i < expectedTitles.length ; i++) {

            onData(anything()).atPosition(i).perform(click());

            onView(withId(R.id.item_title))
                    .check(matches(withText("Title: "+expectedTitles[i])));

            onView(withId(R.id.item_description))
                    .check(matches(withText("Description: "+expectedDescriptions[i])));

            onView(withId(R.id.item_link))
                    .check(matches(withText("Link: "+expectedLinks[i])));

            pressBack();
        }
    }
}
