package com.vdcast.mornhousetesttask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.vdcast.mornhousetesttask.domain.NetworkDataSource
import com.vdcast.mornhousetesttask.ui.theme.MornhouseTestTaskTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val networkDataSource: NetworkDataSource by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MornhouseTestTaskTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppUi()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        networkDataSource.close()
    }
}