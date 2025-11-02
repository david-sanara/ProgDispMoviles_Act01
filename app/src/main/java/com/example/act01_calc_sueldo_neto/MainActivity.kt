package com.example.act01_calc_sueldo_neto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.compose.composable
import com.example.act01_calc_sueldo_neto.ui.theme.Act01_Calc_Sueldo_NetoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Act01_Calc_Sueldo_NetoTheme {
                MyApp()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview
    @Composable
    fun MyApp() {

        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "formulario"){

            composable(route = "formulario") {
                PantallaFormulario(navController = navController)
            }

            composable(
                route = "resultado/{brutoAnual}/{brutoMensual}/{netoAnual}/{netoMensual}/{irpf}/{ss}/{pagas}",
                arguments = listOf(
                    navArgument("brutoAnual") { type = NavType.StringType },
                    navArgument("brutoMensual") { type = NavType.StringType },
                    navArgument("netoAnual") { type = NavType.StringType },
                    navArgument("netoMensual") { type = NavType.StringType },
                    navArgument("irpf") { type = NavType.StringType },
                    navArgument("ss") { type = NavType.StringType },
                    navArgument("pagas") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val brutoAnual = backStackEntry.arguments?.getString("brutoAnual") ?: "0.0"
                val brutoMensual = backStackEntry.arguments?.getString("brutoMensual") ?: "0.0"
                val netoAnual = backStackEntry.arguments?.getString("netoAnual") ?: "0.0"
                val netoMensual = backStackEntry.arguments?.getString("netoMensual") ?: "0.0"
                val irpf = backStackEntry.arguments?.getString("irpf") ?: "0.0"
                val ss = backStackEntry.arguments?.getString("ss") ?: "0.0"
                val pagas = backStackEntry.arguments?.getString("pagas") ?: "12"

                PantallaResultado(
                    navController = navController,
                    brutoAnual = brutoAnual,
                    brutoMensual = brutoMensual,
                    netoAnual = netoAnual,
                    netoMensual = netoMensual,
                    irpf = irpf,
                    ss = ss,
                    pagas = pagas
                )
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PantallaFormulario(navController: NavController) {

        val scrollState = rememberScrollState()
        var edad by rememberSaveable { mutableStateOf("") }
        var grupoProfesional by rememberSaveable { mutableStateOf("") }
        var grupoProfExpanded by rememberSaveable { mutableStateOf(false) }
        val opcionesGrupoProf = listOf(
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"
        )
        var estCivil by rememberSaveable { mutableStateOf("") }
        var estCivilExpanded by rememberSaveable { mutableStateOf(false) }
        val opcionesEstCivil = listOf(
            "Soltero/a", "Casado/a", "Pareja de hecho", "Divorciado/a", "Viudo/a"
        )
        var hijos by rememberSaveable { mutableStateOf("") }
        var discapacidad by rememberSaveable { mutableStateOf("") }
        var discapacidadExpanded by rememberSaveable { mutableStateOf(false) }
        val opcionesDiscapacidad = listOf(
            "Sin discapacidad (o < 33%)", "Entre 33% y 64%", "65% o más"
        )
        var salarioBruto by rememberSaveable { mutableStateOf("") }
        var pagas by rememberSaveable { mutableStateOf("") }
        var pagasExpanded by rememberSaveable { mutableStateOf(false) }
        val opcionesPagas = listOf("12", "14")
        var alerta by rememberSaveable { mutableStateOf(false) }

        Scaffold(
            topBar = {
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = "CALCULADORA SUELDO NETO",
                        )
                    }
                )
            },
            bottomBar = {
                BottomAppBar(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.primary,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                val ok = listOf(
                                    edad,
                                    grupoProfesional,
                                    estCivil,
                                    hijos,
                                    discapacidad,
                                    salarioBruto,
                                    pagas
                                ).all { it.isNotBlank() }

                                if (!ok) {
                                    alerta = true
                                    return@Button
                                }

                                val bruto = salarioBruto.replace(".", "").replace(",", ".").toDoubleOrNull()
                                val numPagas = pagas.toIntOrNull()

                                if (bruto == null || numPagas == null) {
                                    alerta = true
                                    return@Button
                                }

                                val brutoMensualCalculado = bruto / numPagas
                                val netoAnualCalculado = calcularSueldoNetoAnual(bruto)
                                val netoMensualCalculado = netoAnualCalculado / numPagas

                                val deduccionSS = bruto * 0.07
                                val baseIRPF = bruto * (1 - 0.07)
                                val retencionIRPF = baseIRPF * 0.15

                                navController.navigate("resultado/${salarioBruto}/$brutoMensualCalculado/" +
                                        "${netoAnualCalculado}/${netoMensualCalculado}/" +
                                        "$retencionIRPF/$deduccionSS/${pagas}")

                            }
                        ) {
                            Text("CALCULAR")
                        }
                    }

                    if (alerta) {
                        AlertDialog(
                            onDismissRequest = { alerta = false },
                            confirmButton = { TextButton({ alerta = false }) { Text("Aceptar") } },
                            title = { Text("Faltan datos o formato inválido") },
                            text  = { Text("Por favor, inserte todos los datos y use números válidos.") }
                        )
                    }

                }
            },

            ) { innerPadding ->

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                Text(
                    modifier = Modifier
                        .padding(top = 25.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,

                    text = "INTRODUCE LOS DATOS PARA REALIZAR EL CÁLCULO"
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    modifier = Modifier
                        .padding(top = 25.dp, start = 58.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,

                    text = "Edad"
                )
                TextField(
                    value = edad,
                    onValueChange = { edad = it
                    },
                    placeholder = { Text(text = "Introduce la edad") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )


                Spacer(Modifier.height(16.dp))

                Text(
                    modifier = Modifier
                        .padding(top = 25.dp, start = 58.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,

                    text = "Grupo Profesional"
                )
                ExposedDropdownMenuBox(
                    expanded = grupoProfExpanded,
                    onExpandedChange = { grupoProfExpanded = !grupoProfExpanded }
                ) {
                    TextField(
                        value = grupoProfesional,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text("Selecciona 1 a 11",
                            color = Color.Blue) },
                        singleLine = true,
                        modifier = Modifier
                            .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                            .padding(horizontal = 16.dp)
                    )

                    ExposedDropdownMenu(
                        expanded = grupoProfExpanded,
                        onDismissRequest = { grupoProfExpanded = false }
                    ) {
                        opcionesGrupoProf.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(opcion) },
                                onClick = {
                                    grupoProfesional = opcion
                                    grupoProfExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    modifier = Modifier
                        .padding(top = 25.dp, start = 58.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,

                    text = "Estado Civil"
                )
                ExposedDropdownMenuBox(
                    expanded = estCivilExpanded,
                    onExpandedChange = { estCivilExpanded = !estCivilExpanded }
                )
                { TextField(
                        value = estCivil,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Selecciona tu estado civil",
                    color = Color.Blue) },
                singleLine = true,
                modifier = Modifier
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                    .padding(horizontal = 16.dp)
                    
                )
                    ExposedDropdownMenu(
                expanded = estCivilExpanded,
                onDismissRequest = { estCivilExpanded = false }
            ) {
                        opcionesEstCivil.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(opcion) },
                                onClick = {
                                    estCivil = opcion
                                    estCivilExpanded = false
                        }
                    )
                }
            }
            }

                Spacer(Modifier.height(16.dp))

                Text(
                    modifier = Modifier
                        .padding(top = 25.dp, start = 58.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,

                    text = "Número de hijos"
                )
                TextField(
                    value = hijos,
                    onValueChange = { hijos = it
                    },
                    placeholder = { Text(text = "Inserte 0 si no tiene") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(16.dp))

                Text(
                    modifier = Modifier
                        .padding(top = 25.dp, start = 58.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,

                    text = "Grado de discapacidad"
                )
                ExposedDropdownMenuBox(
                    expanded = discapacidadExpanded,
                    onExpandedChange = { discapacidadExpanded = !discapacidadExpanded }
                ) {
                    TextField(
                        value = discapacidad,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text("Selecciona grado",
                            color = Color.Blue) },
                        singleLine = true,
                        modifier = Modifier
                            .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                            .padding(horizontal = 16.dp)
                    )

                    ExposedDropdownMenu(
                        expanded = discapacidadExpanded,
                        onDismissRequest = { discapacidadExpanded = false }
                    ) {
                        opcionesDiscapacidad.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(opcion) },
                                onClick = {
                                    discapacidad = opcion
                                    discapacidadExpanded = false
                                }
                            )
                        }
                    }
                }


                Spacer(Modifier.height(16.dp))

                Text(
                    modifier = Modifier
                        .padding(top = 25.dp, start = 58.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,

                    text = "Salario Bruto Anual"
                )
                TextField(
                    value = salarioBruto,
                    onValueChange = { salarioBruto = it
                    },
                    placeholder = { Text(text = "Introduce el bruto anual") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    modifier = Modifier
                        .padding(top = 25.dp, start = 58.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,

                    text = "Número de pagas"
                )
                ExposedDropdownMenuBox(
                    expanded = pagasExpanded,
                    onExpandedChange = { pagasExpanded = !pagasExpanded }
                ) {
                    TextField(
                        value = pagas,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text("Selecciona 12 o 14",
                            color = Color.Blue) },
                        singleLine = true,
                        modifier = Modifier
                            .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 24.dp)
                    )

                    ExposedDropdownMenu(
                        expanded = pagasExpanded,
                        onDismissRequest = { pagasExpanded = false }
                    ) {
                        opcionesPagas.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(opcion) },
                                onClick = {
                                    pagas = opcion
                                    pagasExpanded = false
                                }
                            )
                        }
                    }
                }

            }

        }
    }

    private fun calcularSueldoNetoAnual(
        salarioBrutoAnual: Double
    ): Double {
        val cotizacionSS = 0.07
        val irpf = 0.15
        val sueldoTrasSS = salarioBrutoAnual * (1 - cotizacionSS)
        val sueldoNetoAnual = sueldoTrasSS * (1 - irpf)
        return sueldoNetoAnual
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PantallaResultado(
        navController: NavController,
        brutoAnual: String,
        brutoMensual: String,
        netoAnual: String,
        netoMensual: String,
        irpf: String,
        ss: String,
        pagas: String
    ) {

        val brutoAnualDouble = brutoAnual.toDoubleOrNull() ?: 0.0
        val brutoMensualDouble = brutoMensual.toDoubleOrNull() ?: 0.0
        val netoAnualDouble = netoAnual.toDoubleOrNull() ?: 0.0
        val netoMensualDouble = netoMensual.toDoubleOrNull() ?: 0.0
        val irpfDouble = irpf.toDoubleOrNull() ?: 0.0
        val ssDouble = ss.toDoubleOrNull() ?: 0.0
        val numPagas = pagas.toIntOrNull() ?: 12



        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Resultado del Cálculo") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
                        }
                    }
                )
            }

        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Resultado:",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(32.dp))

                Text(
                    text = "Sueldo bruto anual: ${"%.2f".format(brutoAnualDouble)} €",
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    fontSize = 20.sp
                )
                Spacer(Modifier.height(16.dp))

                Text(
                    text = "Sueldo bruto mensual (${numPagas} pagas): ${"%.2f".format(brutoMensualDouble)} €",
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    fontSize = 20.sp
                )
                Spacer(Modifier.height(32.dp))

                Text(
                    text = "Retención IRPF (15%): ${"%.2f".format(irpfDouble)} €",
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    fontSize = 20.sp,
                    color = Color.Red
                )
                Spacer(Modifier.height(16.dp))

                Text(
                    text = "Deducciones SS (7%): ${"%.2f".format(ssDouble)} €",
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    fontSize = 20.sp,
                    color = Color.Red
                )
                Spacer(Modifier.height(32.dp))

                Text(
                    text = "Sueldo neto anual: ${"%.2f".format(netoAnualDouble)} €",
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue
                )
                Spacer(Modifier.height(16.dp))

                Text(
                    text = "Sueldo neto mensual (${numPagas} pagas): ${"%.2f".format(netoMensualDouble)} €",
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue
                )
            }
        }
    }

}
