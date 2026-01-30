
import java.io.File;
import java.util.Formatter;
import java.util.Random;


public class proyectoBIM2 {
    static Random ale = new Random();
    static int contActualizar = 0;
    static int contBarato = 0;
    static int contCaro = 0;
    public static void main(String[] args) {
        int n = 10; // aqui ponemos el numero de estudiantes
        String nombres[][] = new String[n][2];
        double datos[][] = new double[n][6];
        String extra[][] = new String[n][2];

        generarNombres(nombres);
        generarConsumo(datos, extra);
        calcularIndicadores(datos, extra);
        mostrarTabla(nombres, datos, extra);
        generarArchivo("reporte_consumo.csv", nombres, datos, extra);
        mostrarConteos();
    }
    // este metodo genera los nombres automaticamente
    static void generarNombres(String nombres[][]) {
    String nombresBase[] = {
            "Ana", "Luis", "Maria", "Carlos", "Sofia",
            "Pedro", "Lucia", "Juan", "Andrea", "Miguel"
    };
    String apellidosBase[] = {
            "Torres", "Lopez", "Perez", "Ruiz", "Vega",
            "Mendoza", "Castro", "Ramos", "Ortiz", "Silva"
    };
    for (int i = 0; i < nombres.length; i++) {
        nombres[i][0] = String.valueOf(i + 1);
        nombres[i][1] =
                nombresBase[i % nombresBase.length] + " " +
                apellidosBase[i % apellidosBase.length];
    }
}
    // este metodo genera los dias y el consumo total
    static void generarConsumo(double datos[][], String extra[][]) {
        for (int i = 0; i < datos.length; i++) {
            int dias = 10 + ale.nextInt(21);
            double clases = (200 + ale.nextInt(4000)) / 1024.0;
            double redes = (200 + ale.nextInt(4000)) / 1024.0;
            double videos = (500 + ale.nextInt(6000)) / 1024.0;
            double juegos = (500 + ale.nextInt(6000)) / 1024.0;
            double tareas = (100 + ale.nextInt(2000)) / 1024.0;
            double consumoTotal = clases + redes + videos + juegos + tareas;
            datos[i][0] = dias;
            datos[i][1] = consumoTotal;
            if (videos >= redes && videos >= juegos) {
                extra[i][0] = "Reducir streaming";
            } else if (juegos >= videos && juegos >= redes) {
                extra[i][0] = "Limitar juegos";
            } else if (redes >= videos) {
                extra[i][0] = "Optimizar redes";
            } else {
                extra[i][0] = "Uso adecuado";
            }
        }
    }
    // este metodo calcula el promedio la proyeccion y el plan 
    static void calcularIndicadores(double datos[][], String extra[][]) {
        for (int i = 0; i < datos.length; i++) {
            double promedio = datos[i][1] / datos[i][0];
            double proyeccion = promedio * 30;
            int plan, actualizar;
            String tipo;
            if (proyeccion <= 20) {
                plan = 30;
                actualizar = 0;
                tipo = "BARATO";
                contBarato++;
            } else if (proyeccion <= 40) {
                plan = 60;
                actualizar = 0;
                tipo = "NORMAL";
            } else {
                plan = 60;
                actualizar = 1;
                tipo = "CARO";
                contCaro++;
                contActualizar++;
            }
            datos[i][2] = promedio;
            datos[i][3] = proyeccion;
            datos[i][4] = plan;
            datos[i][5] = actualizar;
            extra[i][1] = tipo;
        }
    }
    // este metodo muestra la tabla en consola
    static void mostrarTabla(String nombres[][], double datos[][], String extra[][]) {
        System.out.printf(
                "%-3s %-15s %-5s %-10s %-10s %-12s %-6s %-10s %-18s%n",
                "ID", "NOMBRE", "DIAS", "TOTAL", "PROM",
                "PROY", "PLAN", "ACTUAL", "SUGERENCIA"
        );
        System.out.println("---------------------------------------------------------------------------------------------");        
        for (int i = 0; i < datos.length; i++) {
            System.out.printf(
                    "%-3s %-15s %-5.0f %-10.2f %-10.2f %-12.2f %-6.0f %-10s %-18s%n",
                    nombres[i][0], nombres[i][1],
                    datos[i][0], datos[i][1], datos[i][2],
                    datos[i][3], datos[i][4],
                    datos[i][5] == 1 ? "SI" : "NO",
                    extra[i][0]
            );
        }
    }

    // este metodo genera el archivo CSV
    static void generarArchivo(String archivo, String nombres[][], double datos[][], String extra[][]) {
        try {
            Formatter fout = new Formatter(new File(archivo));
            fout.format("ID;NOMBRE;DIAS;CONSUMO TOTAL;PROMEDIO;PROYECCION;PLAN;ACTUALIZAR;SUGERENCIA;TIPO%n");
            for (int i = 0; i < datos.length; i++) {
                fout.format(
                        "%s;%s;%.0f;%.2f;%.2f;%.2f;%.0f;%s;%s;%s%n",
                        nombres[i][0], nombres[i][1],
                        datos[i][0], datos[i][1], datos[i][2],
                        datos[i][3], datos[i][4],
                        datos[i][5] == 1 ? "SI" : "NO",
                        extra[i][0], extra[i][1]
                );
            }
            fout.close();
            System.out.println("\nArchivo '" + archivo + "' generado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al generar el archivo.");
        }
    }
     static void mostrarConteos() {
        System.out.println("\n--- RESUMEN GENERAL ---");
        System.out.println("Usuarios que deben actualizar plan: " + contActualizar);
        System.out.println("Usuarios con plan mas barato: " + contBarato);
        System.out.println("Usuarios con plan mas caro: " + contCaro);
    }
}

/**
 * run:
ID  NOMBRE          DIAS  TOTAL      PROM       PROY         PLAN   ACTUAL     SUGERENCIA        
---------------------------------------------------------------------------------------------
1   Ana Torres      24    12,10      0,50       15,13        30     NO         Limitar juegos    
2   Luis Paredes    15    12,37      0,82       24,74        30     NO         Limitar juegos    
3   Maria Lopez     30    11,23      0,37       11,23        30     NO         Limitar juegos    
4   Carlos Ruiz     28    11,21      0,40       12,01        30     NO         Optimizar redes   
5   Sofia Vega      14    12,66      0,90       27,13        30     NO         Limitar juegos    

Archivo 'reporte_consumo.csv' generado correctamente.

--- RESUMEN GENERAL ---
Usuarios que deben actualizar plan: 0
Usuarios con plan mas barato: 5
Usuarios con plan mas caro: 0
BUILD SUCCESSFUL (total time: 7 seconds)
 */
