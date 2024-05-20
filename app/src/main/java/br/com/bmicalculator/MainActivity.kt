package br.com.bmicalculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.com.bmicalculator.ui.theme.BMICalculatorTheme
import kotlin.math.round

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BMICalculatorTheme(darkTheme = true) {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .padding(vertical = 150.dp)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        val height = remember {
                            TextFieldState()
                        }
                        val weight = remember {
                            TextFieldState()
                        }

                        var result by remember { mutableDoubleStateOf(0.0) }

                        Text(text = "BMI Calculator")
                        Input(
                            label = "Height",
                            imeAction = ImeAction.Next,
                            KeyboardType.Decimal,
                            height
                        )
                        Input(
                            label = "Weight",
                            imeAction = ImeAction.Done,
                            KeyboardType.Number,
                            weight
                        )
                        Button(onClick = {
                            if (height.text.isNotBlank() && weight.text.isNotBlank()) {
                                result =
                                    weight.text.toDouble() / (height.text.toDouble() * height.text.toDouble())
                            } else {
                                Toast.makeText(
                                    baseContext,
                                    "Please type height and weight",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }) {
                            Text(text = "Calculate BMI")
                        }
                        Text("Height: ${height.text}")
                        Text("Weight: ${weight.text}")
                        Text("Result: ${round(result).toInt()}")
                    }
                }
            }
        }
    }
}

@Composable
fun Input(
    label: String,
    imeAction: ImeAction,
    keyboardType: KeyboardType,
    state: TextFieldState = remember {
        TextFieldState()
    }
) {
    TextField(
        value = state.text,
        onValueChange = { value ->
            state.text = value
        },
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        )
    )
}

class TextFieldState {
    var text: String by mutableStateOf("")
}
