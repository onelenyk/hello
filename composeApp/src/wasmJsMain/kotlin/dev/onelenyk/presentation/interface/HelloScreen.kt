import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.onelenyk.presentation.components.HelloComponent
import dev.onelenyk.presentation.components.HelloState
import hello.composeapp.generated.resources.Res
import hello.composeapp.generated.resources.compose_multiplatform
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.vectorResource


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
            }

            OneTimeInitCurrentTime()

            PoweredByComposeMultiplatform()
            Spacer(modifier = Modifier.height(16.dp))
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
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            state.socials.forEachIndexed { index, item ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomIconButton(
                        icon = vectorResource(item.icon),
                        contentDescription = item.title,
                        onClick = {
                            item.url.openInBrowser()
                        }
                    )
                    Text(
                        text = item.title,
                        color = Color.Black,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun OneTimeInitCurrentTime() {
    val currentTime = remember { Clock.System.now() }

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
    Text(
        text = formattedTime,
        modifier = Modifier
            .wrapContentSize(),
        fontSize = 12.sp,
        fontWeight = FontWeight.ExtraLight,
        lineHeight = 18.sp,
        color = Color(0xff000000)
    )
}

@Composable
fun PoweredByComposeMultiplatform() {
    val link = "https://www.jetbrains.com/compose-multiplatform/"
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
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
