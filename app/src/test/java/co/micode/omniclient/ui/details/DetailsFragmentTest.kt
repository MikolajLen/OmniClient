package co.micode.omniclient.ui.details

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
class DetailsFragmentTest {

    @Test
    fun `should display details`() {
        //given
        val exampledText = "Exampled text"

        //when
        launchFragmentInContainer<DetailsFragment>(
            bundleOf(
                DetailsFragment.DETAILS_TAG
                        to exampledText
            )
        )

        //then
        onView(ViewMatchers.withText(exampledText)).check(matches(isDisplayed()))
    }
}