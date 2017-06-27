package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class EndPointAsyncTaskTest {

    @Test
    public void testVerifyJoke() throws InterruptedException {
        Context context = InstrumentationRegistry.getContext();
        final CountDownLatch latch = new CountDownLatch(1);
        new EndpointsAsyncTask(context, "Manfred") {
            @Override
            protected void onPreExecute() {
                //empty
            }

            @Override
            protected void onPostExecute(String result) {
                assertNotNull(result);
                assertTrue(result.length() > 0);
                latch.countDown();
            }
        }.execute();
        latch.await();

    }
}
