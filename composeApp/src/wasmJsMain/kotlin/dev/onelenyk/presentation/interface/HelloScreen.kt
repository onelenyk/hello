import androidx.compose.foundation.Image
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.stack.Children
import dev.onelenyk.Greeting
import dev.onelenyk.presentation.components.HelloComponent
import dev.onelenyk.presentation.components.HelloState
import dev.onelenyk.presentation.components.RootComponent
import hello.composeapp.generated.resources.Res
import hello.composeapp.generated.resources.compose_multiplatform
import hello.composeapp.generated.resources.github_icon
import hello.composeapp.generated.resources.instagram_brands_solid
import hello.composeapp.generated.resources.linkedin_brands_solid
import hello.composeapp.generated.resources.resume_icon
import hello.composeapp.generated.resources.telegram_brands_solid
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.internal.JSJoda.DateTimeFormatter
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview


// Extension function to open a URL in the web browser
fun String.openInBrowser() {
    window.open(this, "_blank")
}

@Composable
fun HelloScreen(component: HelloComponent) {
    val state = component.state.collectAsState()
    HelloContent(state = state.value)
    PageTitle(title = state.value.pageTitle)
}

@Composable
fun PageTitle(title: String) {
    LaunchedEffect(title) {
        document.title = title
    }
}

@Composable
fun HelloContent(state: HelloState) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .width(350.dp)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TitleItem(title = state.uiTitle)
                DescriptionItem(description = state.description)
                StoryItem(story = state.shortStory)
                Spacer(modifier = Modifier.height(16.dp))
                SocialMediaButtons(state = state)
                ResumeButton()
            }

            OneTimeInitCurrentTime()
            PoweredByComposeMultiplatform()
            // Any further items can be added here
        }
    }
}

@Composable
fun TitleItem(title: AnnotatedString) {
    ItemRow {
        Text(text = title)
    }
}

@Composable
fun DescriptionItem(description: AnnotatedString) {
    ItemRow {
        Text(text = description)
    }
}

@Composable
fun StoryItem(story: AnnotatedString) {
    ItemRow {
        Text(text = story)
    }
}

@Composable
fun ItemRow(content: @Composable () -> Unit) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        //  HorizontalDivider()
        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.width(12.dp))
        content()
    }
}

@Composable
fun VerticalDivider() = Box(
    modifier = Modifier
        .width(2.dp)
        .height(6.dp)
        .background(Color.Black, RoundedCornerShape(4.dp))
)

@Composable
fun HorizontalDivider() = Box(
    modifier = Modifier
        .width(6.dp)
        .height(2.dp)
        .background(Color.Black, RoundedCornerShape(4.dp))
)

@Composable
fun Divider() = Text(
    modifier = Modifier.padding(horizontal = 8.dp),
    text = "-",
    style = TextStyle(
        color = Color.Black,
        fontSize = 16.sp,
        fontWeight = FontWeight.W600
    )
)

@Composable
fun CustomIconButton(
    icon: ImageVector,
    contentDescription: String?,
    backgroundColor: Color = Color.White,
    iconTint: Color = Color.Black,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(34.dp) // Size of the circular button
            .clip(CircleShape) // Circular shape
            .background(backgroundColor) // Background color of the button
            .clickable(onClick = onClick), // Click behavior
        contentAlignment = Alignment.Center // Center the icon
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = iconTint,
            modifier = Modifier.size(24.dp) // Size of the icon
        )
    }
}

@Composable
fun SocialMediaButtons(state: HelloState) {
    val icons = listOf(
        vectorResource(Res.drawable.telegram_brands_solid), // Replace with actual icons for your case
        vectorResource(Res.drawable.linkedin_brands_solid), // Replace with actual icons for your case
        vectorResource(Res.drawable.instagram_brands_solid), // Replace with actual icons for your case
        vectorResource(Res.drawable.github_icon), // Replace with actual icons for your case
    )
    val labels = listOf("tg", "link", "inst", "git")
    val links = listOf(state.telegram, state.linkedin, state.instagram, state.github)
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            icons.forEachIndexed { index, icon ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // VerticalDivider()

                    CustomIconButton(
                        icon = icon,
                        contentDescription = labels[index],
                        onClick = {
                            links[index].openInBrowser()
                        }
                    )
                    Text(
                        text = labels[index],
                        color = Color.Black,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ResumeButton() {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomIconButton(
                icon = vectorResource(Res.drawable.resume_icon),
                contentDescription = "Resume",
                onClick = {
                    val uri  = Res.getUri("files/lenyk_resume.pdf")
                    print(uri)
                    DownloadFileButton(uri, "lenyk_resume.pdf");
                }
            )
            Text(
                text = "Resume",
                color = Color.Black,
                fontSize = 12.sp
            )
        }
    }
}

fun DownloadFileButton(fileUrl: String, fileName: String = "downloaded_file") {
    val link = document.createElement("a") as org.w3c.dom.HTMLAnchorElement
    link.href = fileUrl
    link.download = fileName
    link.click()
}


@Composable
fun OneTimeInitCurrentTime() {
    // Remember the current time only once
    val currentTime = remember { Clock.System.now() }

    // Format the current time
    val formattedTime = remember(currentTime) {
        val localDateTime = currentTime.toLocalDateTime(TimeZone.currentSystemDefault())
        buildString {
            append(localDateTime.year)
            append("-")
            append(localDateTime.monthNumber.toString().padStart(2, '0'))
            append("-")
            append(localDateTime.dayOfMonth.toString().padStart(2, '0'))
            append(" ")
            append(localDateTime.hour.toString().padStart(2, '0'))
            append(":")
            append(localDateTime.minute.toString().padStart(2, '0'))
            append(":")
            append(localDateTime.second.toString().padStart(2, '0'))
        }
    }

    // Display the formatted time
    Text(text = formattedTime)
}

@Composable
fun PoweredByComposeMultiplatform() {
    val link = "https://www.jetbrains.com/compose-multiplatform/"
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .padding(bottom = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Transparent, RoundedCornerShape(8.dp))
                .clickable {
                    link.openInBrowser()
                }
                .padding(4.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = vectorResource(Res.drawable.compose_multiplatform),
                contentDescription = "Icon",
                modifier = Modifier.size(24.dp) // Size of the icon
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Powered by Compose Multiplatform",
                modifier = Modifier
                    .wrapContentSize(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 18.sp,
                color = Color(0xFF0078D7)
            )
        }
    }
}
