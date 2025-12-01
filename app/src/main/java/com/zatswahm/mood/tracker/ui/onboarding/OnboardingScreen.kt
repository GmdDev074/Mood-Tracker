package com.zatswahm.mood.tracker.ui.onboarding

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zatswahm.mood.tracker.R
import com.zatswahm.mood.tracker.ui.components.AppPrimaryButton
import com.zatswahm.mood.tracker.ui.components.AppSecondaryButton
import com.zatswahm.mood.tracker.ui.theme.MoodTrackerTheme
import kotlinx.coroutines.launch
import androidx.compose.foundation.Canvas

/**
 * Entry point for Onboarding feature.
 * In the future this can be backed by a ViewModel and DataStore flag (hasSeenOnboarding).
 */
@Composable
fun OnboardingRoute(
    onFinished: () -> Unit,
) {
    OnboardingScreen(
        onFinished = onFinished
    )
}

private data class OnboardingPage(
    val title: String,
    val description: String,
    @DrawableRes val imageRes: Int,
)

private val onboardingPages = listOf(
    OnboardingPage(
        title = "Understand your cycle with clarity",
        description = "Log periods, symptoms, and moods. Get gentle insights into your rhythm and fertile window.",
        imageRes = R.drawable.onboarding1
    ),
    OnboardingPage(
        title = "Follow pregnancy week by week",
        description = "Track baby’s growth, kicks, contractions, and appointments with one simple dashboard.",
        imageRes = R.drawable.onboarding2
    ),
    OnboardingPage(
        title = "Never miss a medicine dose again",
        description = "Smart reminders, refill alerts, and history so you always stay on top of your care.",
        imageRes = R.drawable.onboarding3
    ),
    OnboardingPage(
        title = "All your health in one private place",
        description = "Secure, offline-first, and designed just for women’s health. You’re always in control.",
        imageRes = R.drawable.onboarding4
    ),
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onFinished: () -> Unit,
) {
    val context = LocalContext.current
    var lastBackPressTime by remember { mutableStateOf(0L) }

    BackHandler {
        val now = System.currentTimeMillis()
        if (now - lastBackPressTime < 1500L) {
            (context as? Activity)?.finish()
        } else {
            lastBackPressTime = now
            Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT).show()
        }
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize().padding(24.dp)
        ) {
            val isTablet = maxWidth >= 600.dp
            val horizontalPadding: Dp = if (isTablet) 48.dp else 0.dp
            val pagerState = rememberPagerState(
                initialPage = 0,
                pageCount = { onboardingPages.size }
            )
            val coroutineScope = rememberCoroutineScope()
            val isLastPage = pagerState.currentPage == onboardingPages.lastIndex

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = horizontalPadding),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top bar: Skip on the right (hidden on last page)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!isLastPage) {
                        Text(
                            text = "Skip",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .clickable { onFinished() }
                                .background(
                                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (isTablet) {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(32.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OnboardingPager(
                            isTablet = true,
                            pagerState = pagerState,
                            modifier = Modifier
                                .weight(1f)
                        )
                        OnboardingActions(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            showPrevious = pagerState.currentPage > 0,
                            isLastPage = isLastPage,
                            onPrevious = {
                                coroutineScope.launch {
                                    val prev = (pagerState.currentPage - 1).coerceAtLeast(0)
                                    pagerState.animateScrollToPage(prev)
                                }
                            },
                            onNextOrFinish = {
                                coroutineScope.launch {
                                    if (isLastPage) {
                                        onFinished()
                                    } else {
                                        val next = (pagerState.currentPage + 1).coerceAtMost(onboardingPages.lastIndex)
                                        pagerState.animateScrollToPage(next)
                                    }
                                }
                            }
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            OnboardingPager(
                                isTablet = false,
                                pagerState = pagerState
                            )
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        OnboardingActions(
                            modifier = Modifier.fillMaxWidth(),
                            showPrevious = pagerState.currentPage > 0,
                            isLastPage = isLastPage,
                            onPrevious = {
                                coroutineScope.launch {
                                    val prev = (pagerState.currentPage - 1).coerceAtLeast(0)
                                    pagerState.animateScrollToPage(prev)
                                }
                            },
                            onNextOrFinish = {
                                coroutineScope.launch {
                                    if (isLastPage) {
                                        onFinished()
                                    } else {
                                        val next = (pagerState.currentPage + 1).coerceAtMost(onboardingPages.lastIndex)
                                        pagerState.animateScrollToPage(next)
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OnboardingPager(
    isTablet: Boolean,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { pageIndex ->
            OnboardingPageContent(
                page = onboardingPages[pageIndex],
                isTablet = isTablet
            )
        }
        OnboardingPagerIndicator(
            pageCount = onboardingPages.size,
            currentPage = pagerState.currentPage
        )
    }
}

@Composable
private fun OnboardingPageContent(
    page: OnboardingPage,
    isTablet: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = if (isTablet) Alignment.Start else Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Title at top
        Text(
            text = page.title,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = if (isTablet) TextAlign.Start else TextAlign.Center
        )
        // Image in the middle
        Image(
            painter = painterResource(id = page.imageRes),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(if (isTablet) 0.8f else 0.9f)
                .height(if (isTablet) 260.dp else 220.dp),
            contentScale = ContentScale.Fit
        )
        // Description at bottom
        Text(
            text = page.description,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
            textAlign = if (isTablet) TextAlign.Start else TextAlign.Center
        )
    }
}

@Composable
private fun OnboardingPagerIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val selectedColor = MaterialTheme.colorScheme.primary
        val unselectedColor = MaterialTheme.colorScheme.outline
        repeat(pageCount) { index ->
            val isSelected = index == currentPage
            Canvas(
                modifier = Modifier
                    .height(8.dp)
                    .fillMaxWidth(if (isSelected) 0.06f else 0.04f)
            ) {
                drawRoundRect(
                    color = if (isSelected) selectedColor else unselectedColor,
                    cornerRadius = CornerRadius(20f, 20f)
                )
            }
        }
    }
}

@Composable
private fun OnboardingActions(
    modifier: Modifier = Modifier,
    showPrevious: Boolean,
    isLastPage: Boolean,
    onPrevious: () -> Unit,
    onNextOrFinish: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = "Let’s set up what matters most to you. You can switch between Period, Pregnancy, and Medicine reminders anytime.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (showPrevious) {
                AppSecondaryButton(
                    text = "Previous",
                    onClick = onPrevious,
                    modifier = Modifier.weight(1f)
                )
            }
            AppPrimaryButton(
                text = if (isLastPage) "Get started" else "Next",
                onClick = onNextOrFinish,
                modifier = if (showPrevious) Modifier.weight(1f) else Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun OnboardingPreviewPhoneLight() {
    MoodTrackerTheme(darkTheme = false) {
        OnboardingScreen(
            onFinished = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun OnboardingPreviewPhoneDark() {
    MoodTrackerTheme(darkTheme = true) {
        OnboardingScreen(
            onFinished = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 800, heightDp = 1280)
@Composable
private fun OnboardingPreviewTabletLight() {
    MoodTrackerTheme(darkTheme = false) {
        OnboardingScreen(
            onFinished = {}
        )
    }
}


