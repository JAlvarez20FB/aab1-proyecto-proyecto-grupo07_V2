
import java.io.File;
import java.util.Formatter;
import java.util.Scanner;

public class SistemaCRUDM_salida {
    static int m = 20;
    static int n = 9;
    public static void main(String[] args) {
        String datosIn[][] = leerArchivo_Matriz("entradaDatos.csv", m, n);
        String datosOut[][] = procesarConsumo_Matriz(datosIn, 6);
        persistirResultados("reporte_consumo.csv", datosIn, datosOut);
    }
    public static String[][] leerArchivo_Matriz(String archivo, int m, int n) {
        String datos[][] = new String[m][n];
        try {
            Scanner fin = new Scanner(new File(archivo));
            fin.nextLine(); // saltar primera linea
            int i = 0;
            while (fin.hasNextLine() && i < m) {
                String linea[] = fin.nextLine().split(";");
                for (int j = 0; j < n; j++)
                    datos[i][j] = linea[j];
                i++;
            }
            fin.close();
        } catch (Exception e) {
            System.out.println("Error al procesar el archivo.");
        }
        return datos;
    }    
    public static String[][] procesarConsumo_Matriz(String datos[][], int n) {
        String salida[][] = new String[datos.length][n];
        String actualizar = "";
        for (int i = 0; i < datos.length; i++) {
            // Evita filas vacías
            if (datos[i][2] == null) break;
            int dias = Double.valueOf(
                    datos[i][2].replace(",", ".")
            ).intValue();
            if (dias == 0) continue; // evita división por cero
            double clases = Double.valueOf(datos[i][3].replace(",", "."));
            double redes  = Double.valueOf(datos[i][4].replace(",", "."));
            double videos = Double.valueOf(datos[i][5].replace(",", "."));
            double juegos = Double.valueOf(datos[i][6].replace(",", "."));
            double tareas = Double.valueOf(datos[i][7].replace(",", "."));
            int plan = Double.valueOf(
                    datos[i][8].replace(",", ".")
            ).intValue();
            double total = clases + redes + videos + juegos + tareas;
            double prom = total / dias;
            double proy = prom * 30;
            if (proy <= plan)
                actualizar = "NO";
            else
                actualizar = "SI (subir plan)";
            if ((proy + 20)<= plan)
                actualizar = "SI (bajar plan)";
            String sugerencia =
                    (juegos >= videos && juegos >= redes) ? "Limitar juegos" :
                    (videos >= redes) ? "Reducir videos" :
                    "Optimizar redes";
            salida[i][0] = String.format("%.2f", total);
            salida[i][1] = String.format("%.2f", prom);
            salida[i][2] = String.format("%.2f", proy);
            salida[i][3] = String.valueOf(plan);
            salida[i][4] = actualizar;
            salida[i][5] = sugerencia;
        }
        return salida;
    }
    public static void persistirResultados(String nombreArchivo,
                                           String datosIn[][],
                                           String datosOut[][]) {
        try {
            Formatter fout = new Formatter(new File(nombreArchivo));
            fout.format(
              "ID;NOMBRE;DIAS;TOTAL;PROM_D;PROY_M;PLAN_USADO;ACTUALIZAR;SUGERENCIA%n"
            );
            int actualizar = 0;
            int barato = 0;
            int caro = 0;
            for (int i = 0; i < datosOut.length; i++) {
                if (datosOut[i][0] == null) break;
                fout.format("%s;", datosIn[i][0]);
                fout.format("%s;", datosIn[i][1]);
                fout.format("%s;", datosIn[i][2]);
                for (int j = 0; j < datosOut[0].length; j++) {
                    fout.format("%s;", datosOut[i][j]);
                }
                fout.format("%n");
                if ("SI (subir plan)".equals(datosOut[i][4]) || "SI (bajar plan)".equals(datosOut[i][4])) actualizar++;
                if (Integer.parseInt(datosOut[i][3]) == 30) barato++;
                if (Integer.parseInt(datosOut[i][3]) == 80) caro++;
            }
            fout.format("\nUSUARIOS;CONTADOR");
            fout.format("\nACT_PLAN;%d", actualizar);
            fout.format("\nPLAN_BARATO;%d", barato);
            fout.format("\nPLAN_CARO;%d", caro);
            fout.close();
            System.out.println("Archivo generado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al generar el archivo");
            e.printStackTrace();
        }
    }
}