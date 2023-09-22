package com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun ShowPickedImage(
    uri:String,
    isEnabled:Boolean,
    onRemove:(String) -> Unit
){

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(data=uri.toUri())
            .build()
    )
    Box(
        modifier = Modifier
            .width(135.dp)
            .height(135.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
        ,
    ){
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            Image(
                painter = painter,
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
          if(isEnabled){
              Column(
                  modifier = Modifier.fillMaxSize(),
                  horizontalAlignment = Alignment.End,
                  verticalArrangement = Arrangement.Top
              ) {
                  Row(
                      modifier = Modifier
                          .width(40.dp)
                          .height(40.dp)
                          .clip(CircleShape)
                          .background(Color.White)
                          .clickable(onClick = {if(isEnabled){onRemove(uri)} }),
                      horizontalArrangement = Arrangement.Center,
                      verticalAlignment = Alignment.CenterVertically,
                  ) {

                      Icon(
                          imageVector = Icons.Default.Delete,
                          tint = MaterialTheme.colorScheme.background,
                          contentDescription = "image",
                          modifier = Modifier.width(25.dp).height(25.dp)
                      )
                  }
              }
          }
        }
    }
}