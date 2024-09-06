package uk.ac.aber.dcs.cs31620.faa.ui.home

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.faa.R
import uk.ac.aber.dcs.cs31620.faa.datasource.FaaRepository
import uk.ac.aber.dcs.cs31620.faa.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.faa.ui.theme.FAATheme
import java.time.LocalDateTime

/**
 * Represents the home screen. For this version we have
 * an ImageView of kittens, followed by some text, followed
 * by an ImageView with an image that is randomly obtained.
 * Later versions will use a database.
 * @author Chris Loftus
 */
@Composable
fun HomeScreen(
    navController: NavHostController
) {
    val coroutineScope = rememberCoroutineScope()

    TopLevelScaffold(
        navController = navController,
        coroutineScope = coroutineScope
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            HomeScreenContent(
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
private fun HomeScreenContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.home_picture),
            contentDescription = stringResource(R.string.home_picture_image),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(id = R.string.welcome),
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.featured_cat_title),
            fontSize = 18.sp
        )

        FeaturedCat(Modifier.fillMaxWidth())
    }
}

@Composable
private fun FeaturedCat(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current.applicationContext
    LaunchedEffect(key1 = Unit){
        val repository = FaaRepository(context as Application)
        val past = LocalDateTime.now().minusDays(30)
        repository.getRecentCatsSync(past, LocalDateTime.now())
    }

    //val catPos = Random.nextInt(cats.size)
    /*
    Image(
        modifier = modifier,
        painter = painterResource(cats[catPos].resourceId),
        contentDescription = stringResource(R.string.featured_cat_description),
        contentScale = ContentScale.Crop
    )
    */
}

@Preview
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    FAATheme(dynamicColor = false) {
        HomeScreen(navController)
    }
}
