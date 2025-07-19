package com.example.medics.ui.theme.Screen

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medics.R
import com.example.medics.ui.theme.komponen.PharmacyProduct
import com.example.medics.ui.theme.komponen.PharmacyProductCard
import com.example.medics.ui.screens.SectionHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PharmacyScreen(
    onNavigateBack: () -> Unit,
    onUploadPrescriptionClicked: () -> Unit,
    onAddToCartClicked: (productId: String) -> Unit,
    onSeeAllProductsClicked: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    val popularProducts = remember {
        listOf(
            PharmacyProduct("panadol", "Panadol", R.drawable.drugs_2, "20pcs", 15.99),
            PharmacyProduct("bodrex_herbal", "Bodrex Herbal", R.drawable.drugs_4, "100ml", 7.99),
            PharmacyProduct("konidin", "Konidin", R.drawable.drugs_1, "3pcs", 5.99),
            PharmacyProduct("oskadon", "Oskadon", R.drawable.drugs_3, "10pcs", 10.50)
        )
    }

    val productsOnSale = remember {
        listOf(
            PharmacyProduct("obh_combi", "OBH Combi", R.drawable.drugs_1, "75ml", 9.99, 12.99),
            PharmacyProduct("betadine", "Betadine", R.drawable.drugs_2, "50ml", 6.99, 8.99),
            PharmacyProduct("bodrexin", "Bodrexin", R.drawable.drugs_3, "75ml", 7.99, 9.50),
            PharmacyProduct("biogesic", "Biogesic", R.drawable.drugs_4, "100ml", 5.00, 7.00)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pharmacy", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.ShoppingCart, contentDescription = "Shopping Cart")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF008080),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Search drugs, category...") },
                    leadingIcon = { Icon(Icons.Filled.Search, "Search") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = CircleShape,
                )
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(horizontal = 16.dp),
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
                                "Order quickly with",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                "Prescription",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Button(
                                onClick = onUploadPrescriptionClicked,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF008080),
                                    contentColor = Color.White
                                ),
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Text("Upload Prescription", fontWeight = FontWeight.Medium)
                            }
                        }

                        Image(
                            painter = painterResource(id = R.drawable.drugs_promo),
                            contentDescription = "Pills Strip",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(180.dp)
                        )
                    }
                }
            }

            item {
                Column {
                    SectionHeader(
                        title = "Popular Product",
                        onSeeAllClicked = onSeeAllProductsClicked
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(popularProducts) { product ->
                            PharmacyProductCard(
                                product = product,
                                onAddClicked = onAddToCartClicked
                            )
                        }
                    }
                }
            }

            item {
                Column {
                    SectionHeader(
                        title = "Product on Sale",
                        onSeeAllClicked = onSeeAllProductsClicked
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(productsOnSale) { product ->
                            PharmacyProductCard(
                                product = product,
                                onAddClicked = onAddToCartClicked
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Pharmacy Screen Preview")
@Composable
fun PharmacyScreenPreview() {
    PharmacyScreen(
        onNavigateBack = {},
        onUploadPrescriptionClicked = { println("Upload Resep Diklik") },
        onAddToCartClicked = { productId -> println("Ditambahkan ke keranjang: $productId") },
        onSeeAllProductsClicked = { println("Lihat Semua Produk Diklik") }
    )
}