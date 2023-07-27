package com.example.firebase2

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.firebase2.ui.theme.Firebase2Theme
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Firebase2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                  ChatInterface()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatInterface() {

    val db = Firebase.firestore



    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var name by remember { mutableStateOf("") }
        var message by remember { mutableStateOf("") }
        Text(text = "Your name is: $name")
        Text(text = "Your message is: $message")
        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Enter your name") },
            leadingIcon = {
                Icon(Icons.Filled.AccountCircle, contentDescription = null, tint = Color(0XFF76D1FE) )
            },
        )
        Spacer(modifier = Modifier.height(10.dp))


        TextField(
            value = message,
            onValueChange = { message = it },
            label = { Text("Enter your Message") },
            leadingIcon = {
                Icon(Icons.Filled.MailOutline, contentDescription = null, tint = Color(0XFF76D1FE) )
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            val chatData = hashMapOf(
                "name" to name,
                "message" to message
            )
            db.collection("chats")
                .add(chatData)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        }) {
            Row(verticalAlignment = Alignment.CenterVertically){
                Icon(Icons.Filled.Send, contentDescription = null, tint = Color(0xff000000))
                Text("    Send")
            }
        }
    }
}

@Composable
fun MessageCard(name: String, message: String) {
    val db = Firebase.firestore
    Column {
        Text(text = name)
        Text(text = message)
    }

    db.collection("chats")
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                Log.d(TAG, "${document.id} => ${document.data}")
            }
        }
        .addOnFailureListener { exception ->
            Log.d(TAG, "Error getting documents: ", exception)
        }
}

@Composable
fun Discussion() {

    // Initialize Firebase Realtime Database
    val db = Firebase.firestore

    db.collection("chats")
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                Log.d(TAG, "${document.id} => ${document.data}")
            }
        }
        .addOnFailureListener { exception ->
            Log.d(TAG, "Error getting documents: ", exception)
        }


    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

    }
}