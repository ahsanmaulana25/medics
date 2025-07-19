package com.example.medics.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medics.R
import com.example.medics.model.Doctor
import com.example.medics.ui.theme.Screen.MessagesScreen
import com.example.medics.ui.theme.Screen.ProfileScreen
import com.example.medics.ui.theme.Screen.ScheduleScreen
import com.example.medics.ui.theme.komponen.HomeBottomNavigationBar
import com.example.medics.ui.theme.komponen.TopDoctorsList


data class HealthArticle(
    val id: String,
    val title: String,
    val imageUrl: Int,
    val category: String,
    val readTimeMinutes: Int
)

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem("home", Icons.Filled.Home, "Home")
    object Messages : BottomNavItem("messages", Icons.Filled.MailOutline, "Messages")
    object Schedule : BottomNavItem("schedule", Icons.Filled.DateRange, "Schedule")
    object Profile : BottomNavItem("profile", Icons.Filled.Person, "Profile")
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onLogoutClicked: () -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    onNotificationClicked: () -> Unit,
    onDoctorCardClicked: (doctorId: String) -> Unit,
    onSeeAllDoctorsClicked: () -> Unit,
    onBottomNavItemClicked: (route: String) -> Unit,
    onNavigateToChatScreen: (conversationId: String, chatPartnerName: String, chatPartnerImageRes: Int) -> Unit,
    onArticleClicked: (articleId: String) -> Unit,
    onPharmacyShortcutClicked: () -> Unit,
    onHospitalShortcutClicked: () -> Unit,
    onAmbulanceShortcutClicked: () -> Unit,
    onSeeAllArticlesClicked: () -> Unit,
    onLearnMoreClicked: () -> Unit
) {
    var selectedBottomNavRoute by remember { mutableStateOf(BottomNavItem.Home.route) }
    val bottomNavItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Messages,
        BottomNavItem.Schedule,
        BottomNavItem.Profile
    )

    Scaffold(
        topBar = {
            if (selectedBottomNavRoute == BottomNavItem.Home.route) {
                HomeTopAppBar(
                )
            } else if (selectedBottomNavRoute == BottomNavItem.Messages.route) {
                MessagesTopAppBar()
            } else if (selectedBottomNavRoute == BottomNavItem.Schedule.route) {
                ScheduleTopAppBar()
            } else if (selectedBottomNavRoute == BottomNavItem.Profile.route) {
                ProfileTopAppBar()
            }
        },
        bottomBar = {
            HomeBottomNavigationBar(
                items = bottomNavItems,
                selectedRoute = selectedBottomNavRoute,
                onItemClick = { route ->
                    selectedBottomNavRoute = route
                    onBottomNavItemClicked(route)
                }
            )
        }
    ) { paddingValues ->
        AnimatedContent(
            targetState = selectedBottomNavRoute,
            label = "BottomNavScreenTransition",
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            transitionSpec = {
                if (initialState.compareTo(targetState) < 0) {
                    slideInHorizontally(animationSpec = tween(durationMillis = 300)) { fullWidth -> fullWidth } + fadeIn(tween(durationMillis = 300)) togetherWith
                            slideOutHorizontally(animationSpec = tween(durationMillis = 300)) { fullWidth -> -fullWidth } + fadeOut(tween(durationMillis = 300))
                } else {
                    slideInHorizontally(animationSpec = tween(durationMillis = 300)) { fullWidth -> -fullWidth } + fadeIn(tween(durationMillis = 300)) togetherWith
                            slideOutHorizontally(animationSpec = tween(durationMillis = 300)) { fullWidth -> fullWidth } + fadeOut(tween(durationMillis = 300))
                }
            }
        ) { targetScreen ->
            when (targetScreen) {
                BottomNavItem.Home.route -> HomeContent(
                    onSearchQueryChanged = onSearchQueryChanged,
                    onDoctorCardClicked = onDoctorCardClicked,
                    onSeeAllDoctorsClicked = onSeeAllDoctorsClicked,
                    onArticleClicked = onArticleClicked,
                    onPharmacyShortcutClicked = onPharmacyShortcutClicked,
                    onHospitalShortcutClicked = onHospitalShortcutClicked,
                    onAmbulanceShortcutClicked = onAmbulanceShortcutClicked,
                    onSeeAllArticlesClicked = onSeeAllArticlesClicked,
                    onLearnMoreClicked = onLearnMoreClicked
                )
                BottomNavItem.Messages.route -> MessagesScreen(
                    onNavigateToChat = onNavigateToChatScreen
                )
                BottomNavItem.Schedule.route -> ScheduleScreen()
                BottomNavItem.Profile.route -> ProfileScreen(
                    painter = painterResource(R.drawable.profile_amelia_renata),
                    onLogoutClicked = onLogoutClicked
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
) {
    var showDevelopmentDialog by remember { mutableStateOf(false) }
    val developmentMessage = "Fitur ini masih dalam pengembangan."

    TopAppBar(
        title = {
            Text("Medics App", fontWeight = FontWeight.Bold)
        },
        actions = {
            IconButton(
                onClick = {showDevelopmentDialog = true}) {
                Icon(Icons.Filled.NotificationsNone, contentDescription = "Notifications")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF008080),
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
    if (showDevelopmentDialog) {
        AlertDialog(
            onDismissRequest = { showDevelopmentDialog = false },
            title = { Text(text = "Informasi") },
            text = { Text(text = developmentMessage) },
            confirmButton = {
                TextButton(
                    onClick = { showDevelopmentDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesTopAppBar() {
    TopAppBar(
        title = {
            Text(
                "Messages",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.testTag("MessagesScreenHeader")
            )
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF008080),
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleTopAppBar() {
    TopAppBar(
        title = { Text("Schedule", fontWeight = FontWeight.Bold) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF008080),
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        actions = {
            IconButton(onClick = { }) {
                Icon(Icons.Filled.Search, contentDescription = "Kalender")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopAppBar() {
    var showDevelopmentDialog by remember { mutableStateOf(false) }
    val developmentMessage = "Fitur ini masih dalam tahap pengembangan."

    TopAppBar(
        title = { Text("Profile", fontWeight = FontWeight.Bold) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF4CB29E),
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        actions = {
            IconButton(onClick = { showDevelopmentDialog = true }) {
                Icon(imageVector = Icons.Default.NotificationsNone, contentDescription = "More Options")
            }
        }
    )
    if (showDevelopmentDialog) {
        AlertDialog(
            onDismissRequest = { showDevelopmentDialog = false },
            title = { Text(text = "Informasi") },
            text = { Text(text = developmentMessage) },
            confirmButton = {
                TextButton(onClick = { showDevelopmentDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}


@Composable
fun HomeContent(
    onSearchQueryChanged: (String) -> Unit,
    onDoctorCardClicked: (doctorId: String) -> Unit,
    onSeeAllDoctorsClicked: () -> Unit,
    onArticleClicked: (articleId: String) -> Unit,
    onPharmacyShortcutClicked: () -> Unit,
    onHospitalShortcutClicked: () -> Unit,
    onAmbulanceShortcutClicked: () -> Unit,
    onSeeAllArticlesClicked: () -> Unit,
    onLearnMoreClicked: () -> Unit
) {
    val scrollState = rememberScrollState()
    var searchQuery by remember { mutableStateOf("") }
    var showDevelopmentDialog by remember { mutableStateOf(false) }
    val developmentMessage = "Fitur ini masih dalam pengembangan."

    val sampleHealthArticles = remember {
        listOf(
            HealthArticle("art1", "The Benefits of a Balanced Diet", R.drawable.article_3, "Nutrition", 5),
            HealthArticle("art2", "Simple Exercises for a Healthier Heart", R.drawable.article_1, "Fitness", 7),
            HealthArticle("art3", "Understanding Mental Wellness", R.drawable.article_2, "Mental Health", 6),
            HealthArticle("art4", "Tips for Better Sleep Quality", R.drawable.article_3, "Lifestyle", 4),
            HealthArticle("art5", "The Importance of Regular Check-ups", R.drawable.article_2, "Healthcare", 5)
        )
    }

    val topDoctorsSample = remember {
        listOf(
            Doctor(
                id = "doc1",
                name = "Dr. Marcus Horizon",
                specialization = "Dental Specialist",
                imageRes = R.drawable.marcus,
                hospital = "Singaparna Hospital",
                rating = 4.7f,
                reviews = 120
            ),
            Doctor(
                id = "doc2",
                name = "Dr. Maria Elena",
                specialization = "Skin Specialist",
                imageRes = R.drawable.maria,
                hospital = "Metro Hospital",
                rating = 4.5f,
                reviews = 98
            ),
            Doctor(
                id = "doc3",
                name = "Dr. Stevi Jessi",
                specialization = "Heart Specialist",
                imageRes = R.drawable.stefi,
                hospital = "Hope Clinic",
                rating = 4.9f,
                reviews = 210
            ),
            Doctor(
                id = "doc4",
                name = "Dr. Marcus Horizon",
                specialization = "Cardiologist",
                imageRes = R.drawable.marcus,
                hospital = "City General",
                rating = 4.8f,
                reviews = 230
            )
        )
    }

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
            .padding(top = 16.dp),
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                onSearchQueryChanged(it)
            },
            label = { Text("Search doctor, drugs, articles...") },
            leadingIcon = { Icon(Icons.Filled.Search, "Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            shape = CircleShape,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ShortcutItem("Doctor", R.drawable.icon_doctor,
                onClick = onSeeAllDoctorsClicked)
            ShortcutItem("Pharmacy", R.drawable.icon_pharmacy,
                onClick = onPharmacyShortcutClicked)
            ShortcutItem("Hospital", R.drawable.icon_hospital,
                onClick = onHospitalShortcutClicked)
            ShortcutItem("Ambulance", R.drawable.icon_ambulance,
                onClick = onAmbulanceShortcutClicked)
        }
        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(horizontal = 16.dp)
                .clickable { onLearnMoreClicked() },
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F2F1))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text(
                        "Early protection for",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        "your family health",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = onLearnMoreClicked,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF008080),
                            contentColor = Color.White
                        ),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text("Learn More", fontWeight = FontWeight.Medium)
                    }
                }

                Image(
                    painter = painterResource(id = R.drawable.promosi_iklan),
                    contentDescription = "Family Health Promotion",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(100.dp))
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.fillMaxWidth())
        {
            SectionHeader(
                title = "Top Doctors",
                onSeeAllClicked = onSeeAllDoctorsClicked
            )
            Spacer(modifier = Modifier.height(12.dp))
            TopDoctorsList(
                doctors = topDoctorsSample,
                onDoctorCardClicked = onDoctorCardClicked
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            SectionHeader(
                title = "Health Articles",
                onSeeAllClicked = onSeeAllArticlesClicked)
            Spacer(modifier = Modifier.height(12.dp))
            HealthArticlesList(
                articles = sampleHealthArticles,
                onArticleClicked = onArticleClicked
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
    if (showDevelopmentDialog) {
        AlertDialog(
            onDismissRequest = { showDevelopmentDialog = false },
            title = { Text(text = "Informasi") },
            text = { Text(text = developmentMessage) },
            confirmButton = {
                TextButton(
                    onClick = { showDevelopmentDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}


@Composable
fun SectionHeader(title: String, onSeeAllClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
        TextButton(onClick = onSeeAllClicked) {
            Text("See All", color = Color(0xFF008080))
        }
    }
}


@Composable
fun ArticleCard(article: HealthArticle, onClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .padding(end = 12.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.clickable { onClick(article.id) }
        ) {
            Image(
                painter = painterResource(id = article.imageUrl),
                contentDescription = article.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = article.category.uppercase(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = article.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${article.readTimeMinutes} min read",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }
        }
    }
}


@Composable
fun HealthArticlesList(
    articles: List<HealthArticle>,
    onArticleClicked: (articleId: String) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(articles) { article ->
            ArticleCard(article = article, onClick = onArticleClicked)
        }
    }
}


@Composable
fun ShortcutItem(
    name: String,
    iconRes: Int,
    onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = name,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color(0xFFE0F2F1))
                .padding(8.dp)
        )
        Text(name, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
    }
}


@Preview(showBackground = true, name = "Home Screen Preview")
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onLogoutClicked = {},
        onSearchQueryChanged = {},
        onNotificationClicked = {},
        onDoctorCardClicked = {},
        onSeeAllDoctorsClicked = {},
        onBottomNavItemClicked = {},
        onNavigateToChatScreen = { conversationId, chatPartnerName, chatPartnerImageRes ->
            println("Preview: Navigate to chat with ID: $conversationId, Name: $chatPartnerName, Image: $chatPartnerImageRes")
        },
        onArticleClicked = {},
        onPharmacyShortcutClicked = {},
        onHospitalShortcutClicked = {},
        onAmbulanceShortcutClicked = {},
        onSeeAllArticlesClicked = {},
        onLearnMoreClicked = {}
    )
}