package com.example.iniciar_sesion.utils;

public final class ApiConfig {

    private ApiConfig() {
        // Evita instanciar esta clase
    }

    public static final String BASE_URL = "http://localhost:3000";

    // Emulador Android Studio -> 10.0.2.2 - PC / (IPv4 - wifi) Con USB movil - http://localhost:3000/alumnos
    public static final String ALUMNOS = BASE_URL + "/alumnos";

    public static  final String CURSOS = BASE_URL + "/cursos";

}
