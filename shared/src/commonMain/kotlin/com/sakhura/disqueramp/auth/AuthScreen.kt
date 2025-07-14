package com.sakhura.disqueramp.auth

@OptIn
@Composable
fun AuthScreen(
    viewModel: AuthViewModel = viewModel()
    onAuthScreen: () -> Unit
){
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val email by remember {mutableStateOf("")}
    val password by remember {mutableStateOf("")}
    val confirmPassword by remember {mutableStateOf("")}
    val passwordVisibility by remember {mutableStateOf(false)}
    val confirmPasswordVisibility by remember {mutableStateOf(false)}

    //login exitoso
    LaunchedEffect(uiState.isAuthenticated){
        if(uiState.isAuthenticated){
            onAuthSuccess()
        }
        }
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text= id(uiState.isLoginMode)
            "Bienvenido" else "Crea tú cuenta"
            fontSize = 30.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(20.dp))
    }

    //campo email - pass -confpass - mesgError- Boton


}