package com.example.pulltorefresh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pulltorefresh.ui.theme.PullToRefreshTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PullToRefreshTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val items = remember {
                        (1..100).map { "item $it" }
                    }

                    var isRefreshing by remember {
                        mutableStateOf(false)
                    }
                    
                    val scope = rememberCoroutineScope()
                    
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ){
                        PullToRefreshLazyColumn(
                            item = items,
                            content = {
                                      itemTitle->
                                      Text(
                                          text = itemTitle,
                                          modifier = Modifier
                                              .padding(16.dp)
                                      )
                            },
                            isRefreshing = isRefreshing,
                            onRefresh = {
                                scope.launch {
                                    isRefreshing = true
                                    delay(7000L) //Simulated Api call
                                    isRefreshing = false
                                }
                            })

                        Button(onClick = {
                            isRefreshing = true
                        }) {
                            Text(text = "Refresh")
                        }
                    }
                }
            }
        }
    }
}

