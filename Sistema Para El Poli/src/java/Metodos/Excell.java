/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metodos;

import conexion.Conectar;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author user
 */
public class Excell {

    @SuppressWarnings("empty-statement")
    public String Exportar(String Nombrea) throws Exception {
        String datos = "";
        try {

            Textos txt = new Textos();
            //Creamos una instancia tipo calendario-- esto es para obtener el mes delaño presente para 
            //Cuando se lea el archivo excel, lea  la hoja del mes actual
            // Obtenemos el mes actual
            Month mes = LocalDate.now().getMonth();

            // Obtenemos el nombre del mes
            String Nmes = mes.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));

            // Convierte a mayúscula la primera letra del nombre.
            String primeraLetra = Nmes.substring(0, 1);
            String mayuscula = primeraLetra.toUpperCase();
            String demasLetras = Nmes.substring(1, Nmes.length());
            Nmes = mayuscula + demasLetras;

            //Direccion del archivo para poder leerlo
            FileInputStream file = new FileInputStream(new File("C:\\Users\\user\\Desktop\\Sistema Para El Poli\\build\\web\\uploadFiles\\" + Nombrea));

            //Traemo la informacion del archivo
            XSSFWorkbook wb = new XSSFWorkbook(file);

            //Declaramo con cual hoja va a trabajar
            //REcibe un String con el nombre de la hoja la cual obtuvimos 
            XSSFSheet sheet = wb.getSheet(Nmes);

            JOptionPane.showMessageDialog(null, "El mes a leer es: " + Nmes);

            //Capturamos cuantas filas tiene
            int numFilas = sheet.getLastRowNum();

            for (int a = 1; a <= numFilas; a++) {//Extraemos las filas
                //Capturamos la fila
                Row fila = sheet.getRow(a);
                Cell celda = fila.getCell(21);

                String Identificacion_estudiante = fila.getCell(0).toString();
                String Nombre = fila.getCell(1).toString();
                String Apellido = fila.getCell(2).toString();
                String Fecha_nacimiento = fila.getCell(3).toString();
                String Deporte = fila.getCell(4).toString();
                String Horario=fila.getCell(5).toString();
                String Id_tutuor = fila.getCell(6).toString();
                String Parentesco = fila.getCell(7).toString();
                String Nombre_tutor = fila.getCell(8).toString();
                String Apellido_tutor = fila.getCell(9).toString();
                String Telefono_tutor = fila.getCell(10).toString();
                String Celular_tutor = fila.getCell(11).toString();
                String Direccion_tutor = fila.getCell(12).toString();
                String Pregunta_tutor_2 = fila.getCell(13).toString();
                String Categoria = String.valueOf(celda.getNumericCellValue());
                
                String[] arrOfStr = Categoria.split("[.]+");  //Creamos un split, de cuando encuentre uno de esos caracteres los separe
                System.out.println(arrOfStr[0]); 
                
                Conectar Con = new Conectar();

                int i = Con.Ver_estudiante(Identificacion_estudiante);
                if (i == 1) { //Preguntamos si el estudiane ya tiene una matricula previa, en caso de ser afirmativa esta pregunta, procederemos sola mente a ingresar la matricula
                    Con.matricularCurso(arrOfStr[0], Deporte, Identificacion_estudiante,Horario);
                } else {

                    int Matricula = Con.matricular(Identificacion_estudiante, Nombre, Apellido, Fecha_nacimiento);
                    int Persona = Con.persona(Id_tutuor, Nombre_tutor, Apellido_tutor, Telefono_tutor, Celular_tutor);
                    int Tutor = Con.tutor(Parentesco, Id_tutuor, Direccion_tutor);
                    int Estudiante_tutor = Con.estudianteTutor(Identificacion_estudiante, Id_tutuor);
                    int Matricularcurso = Con.matricularCurso(arrOfStr[0], Deporte, Identificacion_estudiante,Horario);//estudiante-deporte-horario-categoria
                    

                    /*
                     * Antes de crear los registro debemos preguntar si hubo un error en alguna inserción
                     * en caso de ser verdad procederemos a capturar la identificacion y el nombre de la persona que no se pudo insertar
                     * y procederemos a eliminar los datos que si alcazaron a insertarse para que no haya incongruencias en la base de datos
                     * 0 --> significa que hubo un error al insertar
                     */
                    if (Matricula == 0 || Persona == 0 || Tutor == 0 || Estudiante_tutor == 0 || Matricularcurso == 0) {
                        datos += Identificacion_estudiante + " " + Nombre ;
                    } else {

                        txt.Crear_registro_estudiante(Identificacion_estudiante, Nombre, Apellido, Fecha_nacimiento);
                        txt.Concatenar_acudiente(Nombre, Id_tutuor, Nombre_tutor, Apellido_tutor, Parentesco, Telefono_tutor, Celular_tutor, Direccion_tutor);
                        txt.Concatenar_matricula(Nombre, Deporte, Fecha_nacimiento, "");
                    }

                    if (Pregunta_tutor_2.equalsIgnoreCase("Si")) {
                        String Id_tutuor_2 = fila.getCell(14).toString();
                        String Parentesco_2 = fila.getCell(15).toString();
                        String Nombre_tutor_2 = fila.getCell(16).toString();
                        String Apellido_tutor_2 = fila.getCell(17).toString();
                        String Telefono_tutor_2 = fila.getCell(18).toString();
                        String Celular_tutor_2 = fila.getCell(19).toString();
                        String Direccion_tutor_2 = fila.getCell(20).toString();

                        int Persona_2 = Con.persona(Id_tutuor_2, Nombre_tutor_2, Apellido_tutor_2, Telefono_tutor_2, Celular_tutor_2);
                        int Tutor_2 = Con.tutor(Parentesco_2, Id_tutuor_2, Direccion_tutor_2);
                        int Estudiante_tutor_2 = Con.estudianteTutor(Identificacion_estudiante, Id_tutuor_2);

                        txt.Concatenar_acudiente(Nombre, Id_tutuor_2, Nombre_tutor_2, Apellido_tutor_2, Parentesco_2, Telefono_tutor_2, Celular_tutor_2, Direccion_tutor_2);

                    }
                    System.out.println("");

                }
            }

        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
        return datos;
    }
}
