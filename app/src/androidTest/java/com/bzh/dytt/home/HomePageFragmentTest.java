package com.bzh.dytt.home;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.bzh.dytt.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

@RunWith(AndroidJUnit4.class)
public class HomePageFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Before
    public void waitForDbCreation() throws Throwable {
//        CountDownLatch latch = new CountDownLatch(1);
//        MutableLiveData<Boolean> databaseCreated = AppDatabase.getInstance(InstrumentationRegistry.getTargetContext()).getIsDatabaseCreated();
//        mActivityTestRule.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                databaseCreated.observeForever(new Observer<Boolean>() {
//                    @Override
//                    public void onChanged(@Nullable Boolean aBoolean) {
//                        if (Boolean.TRUE.equals(aBoolean)) {
//                            databaseCreated.removeObserver(this);
//                            latch.countDown();
//                        }
//                    }
//                });
//            }
//        });
//
//        MatcherAssert.assertThat("database should've initialized",
//                latch.await(1, TimeUnit.MINUTES), CoreMatchers.is(true));

    }

    @Test
    public void testTabLayoutClick() {

        onView(withText("欧美电视剧")).perform(click());

        onView(withText("2018新片精品")).perform(click());
    }

}