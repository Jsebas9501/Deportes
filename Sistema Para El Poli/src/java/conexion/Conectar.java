/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import Metodos.Fechas;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

import java.util.Random;

/**
 *
 * @author user
 *
 * Clase que permite conectar a bases de datos
 */
public class Conectar {

    Connection Conect = null;

    private static String correoF = "";

    private static String Codigo_relacion = "";

    public static String getCodigo_relacion() {
        return Codigo_relacion;
    }

    public static void setCodigo_relacion(String Codigo_relacion) {
        Conectar.Codigo_relacion = Codigo_relacion;
    }

    public static InputStream img;

    public static String codigo = "";

    public static Fechas fecha = new Fechas();

    public static Random rm = new Random();

    public Connection conexion() {

        try {
            //Cargamos el Driver MySQL
            Class.forName("com.mysql.jdbc.Driver");
            Class.forName("org.gjt.mm.mysql.Driver");
            Conect = DriverManager.getConnection("jdbc:mysql://localhost/deporte?"
                    + "user=root&password=");
            System.out.println("Se conecto");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conectar.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No se conecto");
        } catch (SQLException ex) {
            Logger.getLogger(Conectar.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No se conecto");
        }

        return Conect;
    }

    //------------------------------------------ Inicio metodo para el inicio de seccion ------------------------------------------//
    //Validar el correo//
    public boolean checkEmail(String email) {
        if (correoInvalido(email)) {
            System.out.println("Correo no valido");
        } else {
            // Establecer el patron
            Pattern p = Pattern.compile("[-\\w\\.]+@[\\.\\w]+\\.\\w+");

            // Asociar el string al patron
            Matcher m = p.matcher(email);

            // Comprobar si encaja
            return m.matches();
        }

        return false;
    }

    public static String[] invalidos = new String[]{"example", "example@.com.com",
        "exampel101@test.a", "exampel101@.com", ".example@test.com",
        "example**()@test.com", "example@%*.com",
        "example..101@test.com", "example.@test.com",
        "test@example101.com", "example@test@test.com",
        "example@test.com.a5"};

    public static boolean correoInvalido(String correo) {
        for (int i = 0; i < invalidos.length; i++) {
            if (correo.equalsIgnoreCase(invalidos[i])) {
                return true;
            }
        }

        return false;

    }

    // Validacion correo con la base de daros//
    public boolean validar(String correo) {
        boolean esta = false;
        String clave = "";

        try {

            Connection cn = conexion();

            PreparedStatement pstm = cn.prepareStatement(" SELECT clave"
                    + " FROM usuario "
                    + " WHERE e_mail = '" + correo + "'");
            //Se crea un objeto donde se almacena el resultado
            //Y con el comando executeQuery se ejecuta la consulta en la base de datos
            ResultSet res = pstm.executeQuery();
            //Recorre el resultado para mostrarlo en los jtf
            while (res.next()) {
                //jTF_identificacion.setText(res.getString( "id_persona" ));
                clave = (res.getString("clave"));

            }

            if (clave.equals("")) {
                esta = false;
            } else {
                esta = true;
            }
            res.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "El correo no existe");
            System.err.println(e.getMessage());
        }

        return esta;
    }

    public boolean contrasena(String correo, String contrasena) {
        boolean esta = false;

        String clave = "";

        try {

            Connection cn = conexion();

            PreparedStatement pstm = cn.prepareStatement(" SELECT clave"
                    + " FROM usuario"
                    + " WHERE e_mail = '" + correo + "'");
            //Se crea un objeto donde se almacena el resultado
            //Y con el comando executeQuery se ejecuta la consulta en la base de datos
            ResultSet res = pstm.executeQuery();
            //Recorre el resultado para mostrarlo en los jtf
            while (res.next()) {
                //jTF_identificacion.setText(res.getString( "id_persona" ));
                clave = (res.getString("clave"));

            }

            if (contrasena.equals(clave)) {
                esta = true;
            } else {
                esta = false;
            }
            res.close();

        } catch (SQLException e) {

        }

        return esta;
    }

    //Mirar que tipo de perfil es//
    public int tipou(String correo) {
        int h = 0;
        try {

            Connection cn = conexion();

            PreparedStatement pstm = cn.prepareStatement(" SELECT tipo_usuario"
                    + " FROM usuario "
                    + " WHERE e_mail = '" + correo + "'");
            //Se crea un objeto donde se almacena el resultado
            //Y con el comando executeQuery se ejecuta la consulta en la base de datos
            ResultSet res = pstm.executeQuery();
            //Recorre el resultado para mostrarlo en los jtf
            while (res.next()) {
                //jTF_identificacion.setText(res.getString( "id_persona" ));
                h = (res.getInt("tipo_usuario"));

            }

            res.close();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return h;
    }
    //------------------------------------------ Fin metodo para el inicio de seccion ------------------------------------------//

    /*
     * Metodo que nos permite ver si el estudiante esta matriculado en cualquier deporte
     * este metodo tambien nos permite ver si existe el estudiante
     */
    public int Ver_estudiante(String Id_estudiante) {
        int i = 0;

        try {

            Connection cn = conexion();

            PreparedStatement pstm = cn.prepareStatement(" SELECT COUNT(*)"
                    + " FROM estudiante"
                    + " WHERE id = '" + Id_estudiante + "'");
            //Se crea un objeto donde se almacena el resultado
            //Y con el comando executeQuery se ejecuta la consulta en la base de datos
            ResultSet res = pstm.executeQuery();
            //Recorre el resultado para mostrarlo en los jtf
            while (res.next()) {
                //jTF_identificacion.setText(res.getString( "id_persona" ));
                i = (res.getInt("COUNT(*)"));
            }
            res.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
        return i;
    }

    //------------------------------------------------//
    //Pruba para insertar imagenes
    public void InsertarImagen() throws FileNotFoundException {
        File imageFile = new File("C:\\Users\\user\\Desktop\\Server3\\server-icon.png");  //Direccion de la imagen
        FileInputStream fis = new FileInputStream(imageFile);

        try {
            Connection cn = conexion();
            PreparedStatement rs = cn.prepareStatement("INSERT INTO usuario"
                    + "(e_mail,Clave,tipo_usuario,Imagen)"
                    + "VALUES (?,?,?,?)");
            rs.setString(1, "C@gmail.com");
            rs.setString(2, "C@gmail.com");
            rs.setInt(3, 0);
            rs.setBinaryStream(4, (InputStream) fis, (int) imageFile.length());//Convertir los datos a blond
            rs.executeUpdate();

        } catch (Exception e) {
        }
    }

    public OutputStream verImagen() {
        try {
            Connection cn = conexion();
            PreparedStatement rs = cn.prepareStatement("SELECT Imagen FROM usuario"
                    + " WHERE e_mail='carlos_moreno82151@elpoli.edu.co'");
            ResultSet res = rs.executeQuery();
            int i = 0;
            while (res.next()) {
                img = res.getBinaryStream(1);
                OutputStream out = new FileOutputStream(new File("C:\\Users\\user\\Desktop\\Server3\\Imagendelusuari00o.png"));
                i++;
                int c = 0;
                while ((c = img.read()) > -1) {
                    System.out.println(c);
                    out.write(c);
                }
            }
            InputStreamReader isReader = new InputStreamReader(img);
            //Creating a BufferedReader object
            BufferedReader reader = new BufferedReader(isReader);
            StringBuffer sb = new StringBuffer();
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
            JOptionPane.showMessageDialog(null, "La imagen esta" + sb.toString());
            out.close();
            img.close();
            return out;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return out;

        }
    }

    public int matricular(String id, String Nombre, String Apellido, String fechan) {
        int bien = 0;
        try {

            Connection cn = conexion();
            PreparedStatement rs = cn.prepareStatement("INSERT INTO estudiante"
                    + "(id,nombre,apellido,fecha_nacimiento)"
                    + "VALUES (?,?,?,?)");

            rs.setString(1, id);
            rs.setString(2, Nombre);
            rs.setString(3, Apellido);
            rs.setString(4, fechan);

            rs.executeUpdate();
            bien = 1;
        } catch (Exception e) {
            bien = 0;
            JOptionPane.showMessageDialog(null, e);
        }
        return bien;

    }

    public int estudianteTutor(String id, String idtutor) {
        int bien = 0;
        try {

            Connection cn = conexion();
            PreparedStatement rs = cn.prepareStatement("INSERT INTO estudiante_tutor"
                    + "(estudiante_id,tutor_id)"
                    + "VALUES (?,?)");

            rs.setString(1, id);
            rs.setString(2, idtutor);

            rs.executeUpdate();
            bien = 1;
        } catch (Exception e) {
            bien = 0;
            JOptionPane.showMessageDialog(null, e);
        }
        return bien;

    }

    public int tutor(String parentensco, String id, String Direccion) {
        int bien = 0;
        try {

            Connection cn = conexion();
            PreparedStatement rs = cn.prepareStatement("INSERT INTO tutor"
                    + "(parentesco,id,direccion)"
                    + "VALUES (?,?,?)");

            rs.setString(1, parentensco);
            rs.setString(2, id);
            rs.setString(3, Direccion);

            rs.executeUpdate();
            bien = 1;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            bien = 0;
        }
        return bien;

    }

    public int persona(String id, String nombre, String apellido, String telefono, String celular) {
        int bien = 0;
        try {

            Connection cn = conexion();
            PreparedStatement rs = cn.prepareStatement("INSERT INTO persona"
                    + "(id,nombre,apellido,telefono,celular)"
                    + "VALUES (?,?,?,?,?)");

            rs.setString(1, id);
            rs.setString(2, nombre);
            rs.setString(3, apellido);
            rs.setString(4, telefono);
            rs.setString(5, celular);

            rs.executeUpdate();
            bien = 1;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            bien = 0;
        }
        return bien;

    }

    public int matricularCurso(String fechn, String deporte, String id) {

        int categoria = fecha.calcularCategoria(fechn);

        int controlador = 0;

        // Creamos un codigo totalmente aleatorio el cual va a quedar como codigo de matricula
        // Reiniciamos el codigo
        codigo = "";
        for (int i = 0; i < 5; i++) {
            codigo += rm.nextInt(10);
        }

        try {

            Connection cn = conexion();
            PreparedStatement rs = cn.prepareStatement("INSERT INTO matricula"
                    + "(codigo_matricula,estudiante_id)"
                    + "VALUES (?,?)");

            rs.setString(1, codigo);
            rs.setString(2, id);

            rs.executeUpdate();

            controlador = 1;
            Matricula_Deporte(codigo, deporte);
        } catch (Exception e) {
            controlador = 0;
            JOptionPane.showMessageDialog(null, e);
        }
        return controlador;
    }

    public void Matricula_Deporte(String codigomatricula, String Deporte) {
        String codigode = verCodigoDeporte(Deporte);
        int bien = 0;
        try {
            Connection cn = conexion();
            PreparedStatement rs = cn.prepareStatement("INSERT INTO matricula_deporte"
                    + "(matricula_codigo,deporte_codigo)"
                    + "VALUES (?,?)");

            rs.setString(1, codigomatricula);
            rs.setString(2, codigode);

            rs.executeUpdate();
            bien = 1;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    /**
     * Metodo que devuelve el codigo segun el tipo de deporte
     *
     * @param deporte
     * @return String
     */
    public String verCodigoDeporte(String deporte) {
        String codigod = "";

        try {

            Connection cn = conexion();

            PreparedStatement pstm = cn.prepareStatement(" SELECT codigo "
                    + " FROM deporte  "
                    + " WHERE descripcion = '" + deporte + "'");
            //Se crea un objeto donde se almacena el resultado
            //Y con el comando executeQuery se ejecuta la consulta en la base de datos
            ResultSet res = pstm.executeQuery();
            //Recorre el resultado para mostrarlo en los jtf
            while (res.next()) {
                //jTF_identificacion.setText(res.getString( "id_persona" ));
                codigod = (res.getString("codigo"));
            }
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return codigod;
    }

    public String[] deportes() {

        String Deportes[] = new String[cuantosDeportes()];
        try {

            Connection cn = conexion();

            PreparedStatement pstm = cn.prepareStatement(" SELECT nombre "
                    + " FROM deporte  ");
            //Se crea un objeto donde se almacena el resultado
            //Y con el comando executeQuery se ejecuta la consulta en la base de datos
            ResultSet res = pstm.executeQuery();
            //Recorre el resultado para mostrarlo en los jtf
            int i = 0;
            while (res.next()) {
                //jTF_identificacion.setText(res.getString( "id_persona" ));
                Deportes[i] = (res.getString("nombre"));
                i++;
            }
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return Deportes;
    }

    /**
     * Metodo que permite saber cuantos deportes hay registrados
     *
     * @return int cantidad de deportes
     */
    public int cuantosDeportes() {
        int count = 0;

        try {

            Connection cn = conexion();

            PreparedStatement pstm = cn.prepareStatement(" SELECT count(*)"
                    + " FROM deporte ");
            //Se crea un objeto donde se almacena el resultado
            //Y con el comando executeQuery se ejecuta la consulta en la base de datos
            ResultSet res = pstm.executeQuery();
            //Recorre el resultado para mostrarlo en los jtf
            while (res.next()) {
                //jTF_identificacion.setText(res.getString( "id_persona" ));
                count = (res.getInt("count(*)"));

            }
//            JOptionPane.showMessageDialog(null, count);
            res.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
        return count;
    }

    public int Numero_de_matriculas_totales() {
        int count = 0;

        try {

            Connection cn = conexion();

            PreparedStatement pstm = cn.prepareStatement(" SELECT count(*)"
                    + " FROM matricula ");
            //Se crea un objeto donde se almacena el resultado
            //Y con el comando executeQuery se ejecuta la consulta en la base de datos
            ResultSet res = pstm.executeQuery();
            //Recorre el resultado para mostrarlo en los jtf
            while (res.next()) {
                //jTF_identificacion.setText(res.getString( "id_persona" ));
                count = (res.getInt("count(*)"));

            }
//            JOptionPane.showMessageDialog(null, count);
            res.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
        return count;
    }
    /*
     * Devuelve una matriz tipo String con la informacion captura 
     */

    public String[][] Estudiante() {
        String datos[][] = new String[4][Numero_de_matriculas_totales()];
        try {

            Connection cn = conexion();

            PreparedStatement pstm = cn.prepareStatement(" SELECT estudiante.id, estudiante.nombre, "
                    + "matricula.codigo_matricula,"
                    + "deporte.nombre as Nombre_deporte "
                    + "from estudiante "
                    + "INNER JOIN matricula ON estudiante.id = matricula.id "
                    + "INNER JOIN deporte_categoria_horario ON matricula.codigo_relacion = deporte_categoria_horario.codigo_relacion "
                    + "INNER JOIN deporte on deporte_categoria_horario.codigo_deporte=deporte.codigo_deporte");
            ResultSet res = pstm.executeQuery();
            int i = 0;
            while (res.next()) {
                datos[0][i] = res.getString("id");
                datos[1][i] = res.getString("nombre");
                datos[2][i] = res.getString("codigo_matricula");
                datos[3][i] = res.getString("Nombre_deporte");
                i++;
            }
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return datos;
    }

    /**
     * Metodo que permite saber si el estudiante ya esta matriculado
     *
     * @param id
     * @return int cantidad de matriculas
     */
    public int Estudiante_prematriculado(String id) {
        int Count = 0;

        try {

            Connection cn = conexion();

            PreparedStatement pstm = cn.prepareStatement(" SELECT COUNT(*) "
                    + " FROM matricula  "
                    + " WHERE id = '" + id + "'");
            //Recorre el resultado para mostrarlo en los jtf
            try ( //Se crea un objeto donde se almacena el resultado
                    //Y con el comando executeQuery se ejecuta la consulta en la base de datos
                    ResultSet res = pstm.executeQuery()) {
                //Recorre el resultado para mostrarlo en los jtf
                while (res.next()) {
                    //jTF_identificacion.setText(res.getString( "id_persona" ));
                    Count = (res.getInt("COUNT(*)"));

                }
//            JOptionPane.showMessageDialog(null, count);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
        return Count;
    }

    /*
     * Metodo que regresa el nombre del estudiantes
     */
    public String Nombre_estudiante(String id) {
        String Nombre = "";

        try {

            Connection cn = conexion();

            PreparedStatement pstm = cn.prepareStatement(" SELECT nombre "
                    + " FROM estudiante  "
                    + " WHERE id = '" + id + "'");
            //Se crea un objeto donde se almacena el resultado
            //Y con el comando executeQuery se ejecuta la consulta en la base de datos
            ResultSet res = pstm.executeQuery();
            //Recorre el resultado para mostrarlo en los jtf
            while (res.next()) {
                //jTF_identificacion.setText(res.getString( "id_persona" ));
                Nombre = (res.getString("nombre"));

            }
//            JOptionPane.showMessageDialog(null, count);
            res.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
        return Nombre;
    }

    public int Numero_tutores(String id) {
        int Numero = 0;

        try {

            Connection cn = conexion();

            PreparedStatement pstm = cn.prepareStatement(" SELECT COUNT(*) "
                    + " FROM estudiante_tutor  "
                    + " WHERE estudiante_tutor.estudiante_id = '" + id + "'");
            //Se crea un objeto donde se almacena el resultado
            //Y con el comando executeQuery se ejecuta la consulta en la base de datos
            ResultSet res = pstm.executeQuery();
            //Recorre el resultado para mostrarlo en los jtf
            while (res.next()) {
                //jTF_identificacion.setText(res.getString( "id_persona" ));
                Numero = (res.getInt("COUNT(*)"));

            }
//            JOptionPane.showMessageDialog(null, count);
            res.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
        return Numero;
    }
    /*
     *SELECT estudiante.*,persoan.id AS Identificaciont,persoan.nombre AS Nombret,persoan.apellido AS apellidoT,tutor.direccion,tutor.parentesco FROM `estudiante` INNER JOIN estudiante_tutor ON estudiante.id=estudiante_tutor.estudiante_id INNER JOIN tutor ON estudiante_tutor.tutor_id=tutor.id INNER JOIN persoan ON tutor.id=persoan.id where estudiante.id=
     */

    public String[][] Datos_estudiante(String id) {
        int Numero = Numero_tutores(id);
        String Datos[][] = new String[11][Numero];

        try {

            Connection cn = conexion();

            PreparedStatement pstm = cn.prepareStatement(" SELECT estudiante.*,"
                    + "persona.id AS Identificaciont,persona.nombre AS Nombret,persona.apellido AS ApellidoT,persona.telefono,persona.celular,"
                    + "tutor.direccion,tutor.parentesco "
                    + "FROM `estudiante` "
                    + "INNER JOIN estudiante_tutor ON estudiante.id = estudiante_tutor.estudiante_id "
                    + "INNER JOIN tutor ON estudiante_tutor.tutor_id = tutor.id "
                    + "INNER JOIN persona ON tutor.id = persona.id "
                    + "WHERE estudiante.id ='" + id + "'");
            //Se crea un objeto donde se almacena el resultado
            //Y con el comando executeQuery se ejecuta la consulta en la base de datos
            ResultSet res = pstm.executeQuery();
            //Recorre el resultado para mostrarlo en los jtf
            int i = 0;
            while (res.next()) {
                //jTF_identificacion.setText(res.getString( "id_persona" ));

                /* Datos del Estudiante */
                Datos[0][i] = res.getString("id");
                Datos[1][i] = res.getString("nombre");
                Datos[2][i] = res.getString("apellido");
                Datos[3][i] = res.getString("fecha_nacimiento");
                /* Datos del del tutor */
                Datos[4][i] = res.getString("Identificaciont");
                Datos[5][i] = res.getString("Nombret");
                Datos[6][i] = res.getString("ApellidoT");
                Datos[7][i] = res.getString("telefono");
                Datos[8][i] = res.getString("celular");
                Datos[9][i] = res.getString("direccion");
                Datos[10][i] = res.getString("parentesco");

                i++;
            }
//            JOptionPane.showMessageDialog(null, count);
            res.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
        return Datos;
    }

    /*
     *   Retorna la cantidad de matriculas que posee un estudiante
     */
    public int Numero_matriculas(String id) {
        int Numero = 0;

        try {
            Connection cn = conexion();

            PreparedStatement pstm = cn.prepareStatement(" SELECT COUNT(*) "
                    + " FROM matricula  "
                    + " WHERE estudiante_id = '" + id + "'");
            //Se crea un objeto donde se almacena el resultado
            //Y con el comando executeQuery se ejecuta la consulta en la base de datos
            ResultSet res = pstm.executeQuery();
            //Recorre el resultado para mostrarlo en los jtf
            while (res.next()) {
                //jTF_identificacion.setText(res.getString( "id_persona" ));
                Numero = (res.getInt("COUNT(*)"));

            }
//            JOptionPane.showMessageDialog(null, count);
            res.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
        return Numero;
    }

    /*
     * Inicio
     * bloque de sentencias para eliminar cuando hubo un error
     * El orden para eliminar los datos es:
     * 1.matricula_deportes
     * 2.matricula
     * 3.estudiante_tutor
     * 4.tutor
     * 5.persoan
     * 6.estudiante
     * DELETE matricula_deporte,matricula FROM matricula_deporte
     * INNER JOIN matricula ON matricula_deporte.matricula_codigo=matricula.codigo 
     * WHERE matricula_deporte.matricula_codigo="764922081804812";
     * Antes de eliminar al tutor hay que preguntar si tiene mas de un estudiante asociado
     * en el caso de ser verdad solo borraremos los datos del estudiante y estudiante_tutor
     * en caso de que el acudiente solo este asociado a un solo estudainte 
     * se procede a borrar tanto al estudiante como al tutor(persona)
     * 
     */
    public void Eliminar(String Identificacion_estudiante, int codigo) {

    }

    /*
     * Fin
     * bloque de sentencias para eliminar cuando hubo un error
     */
    /*
     * Preguntamos si el tutor existe
     */
    public int Existe_tutor(String Identificaion_tutor) {
        int Count = 0;
        try {

            Connection cn = conexion();

            PreparedStatement pstm = cn.prepareStatement(" SELECT COUNT(*) "
                    + " FROM estudiante_tutor  "
                    + " WHERE estudiante_tutor.tutor_id = '" + Identificaion_tutor + "'");
            //Se crea un objeto donde se almacena el resultado
            //Y con el comando executeQuery se ejecuta la consulta en la base de datos
            ResultSet res = pstm.executeQuery();
            //Recorre el resultado para mostrarlo en los jtf
            while (res.next()) {
                //jTF_identificacion.setText(res.getString( "id_persona" ));
                Count = (res.getInt("COUNT(*)"));

            }
//            JOptionPane.showMessageDialog(null, count);
            res.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
        return Count;
    }

    public int Existe_deporte(String Nombre_deporte) {
        int Count = 0;
        try {

            Connection cn = conexion();

            PreparedStatement pstm = cn.prepareStatement(" SELECT COUNT(*) "
                    + " FROM deporte  "
                    + " WHERE deporte.nombre = '" + Nombre_deporte + "'");
            //Se crea un objeto donde se almacena el resultado
            //Y con el comando executeQuery se ejecuta la consulta en la base de datos
            ResultSet res = pstm.executeQuery();
            //Recorre el resultado para mostrarlo en los jtf
            while (res.next()) {
                //jTF_identificacion.setText(res.getString( "id_persona" ));
                Count = (res.getInt("COUNT(*)"));

            }
//            JOptionPane.showMessageDialog(null, count);
            res.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
        return Count;
    }

    /*
     getters y setters
     */
    public static String getCorreoF() {
        return correoF;
    }

    public static void setCorreoF(String aCorreoF) {
        correoF = aCorreoF;
    }

    public void Insertar_deporte(String Codigo, String Nombre_deportes) {
        try {

            Connection cn = conexion();
            PreparedStatement rs = cn.prepareStatement("INSERT INTO deporte"
                    + "(codigo_deporte,nombre)"
                    + "VALUES (?,?)");

            rs.setString(1, Codigo);
            rs.setString(2, Nombre_deportes);

            rs.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public String[][] Ver_deportes() {
        String deportes[][] = new String[2][cuantosDeportes()];

        try {

            Connection cn = conexion();

            PreparedStatement pstm = cn.prepareStatement(" SELECT *"
                    + "FROM deporte");
            //Se crea un objeto donde se almacena el resultado
            //Y con el comando executeQuery se ejecuta la consulta en la base de datos
            ResultSet res = pstm.executeQuery();
            //Recorre el resultado para mostrarlo en los jtf
            int i = 0;
            while (res.next()) {
                //jTF_identificacion.setText(res.getString( "id_persona" ));

                /* Datos del Estudiante */
                deportes[0][i] = res.getString("codigo_deporte");
                deportes[1][i] = res.getString("nombre");

                i++;
            }
//            JOptionPane.showMessageDialog(null, count);
            res.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
        return deportes;
    }

    public String[] Ver_categorias() {
        String deportes[] = new String[Cuantas_categorais()];

        try {

            Connection cn = conexion();

            PreparedStatement pstm = cn.prepareStatement(" SELECT descripcion_categoria"
                    + " FROM categoria");
            ResultSet res = pstm.executeQuery();
            int i = 0;
            while (res.next()) {
                deportes[i] = res.getString("descripcion_categoria");
                i++;
            }
            res.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
        return deportes;
    }

    public int Cuantas_categorais() {
        int cuantas = 0;

        try {

            Connection cn = conexion();
            PreparedStatement pstm = cn.prepareStatement(" SELECT COUNT(*)"
                    + "FROM categoria");
            ResultSet res = pstm.executeQuery();
            while (res.next()) {
                cuantas = res.getInt("COUNT(*)");
            }
            res.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return cuantas;
    }

    public String[] Ver_horarios() {
        String deportes[] = new String[Cuantos_horarios()];

        try {

            Connection cn = conexion();

            PreparedStatement pstm = cn.prepareStatement(" SELECT descripcion_horario"
                    + " FROM horario");
            ResultSet res = pstm.executeQuery();
            int i = 0;
            while (res.next()) {
                deportes[i] = res.getString("descripcion_horario");
                i++;
            }
            res.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
        return deportes;
    }

    public int Cuantos_horarios() {
        int cuantas = 0;

        try {

            Connection cn = conexion();
            PreparedStatement pstm = cn.prepareStatement(" SELECT COUNT(*)"
                    + "FROM horario");
            ResultSet res = pstm.executeQuery();
            while (res.next()) {
                cuantas = res.getInt("COUNT(*)");
            }
            res.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return cuantas;
    }

    public String Codigo_deporte(String deporte) {
        String cuantas = "";

        try {

            Connection cn = conexion();
            PreparedStatement pstm = cn.prepareStatement(" SELECT codigo_deporte"
                    + " FROM deporte"
                    + " WHERE nombre='" + deporte + "'");
            ResultSet res = pstm.executeQuery();
            while (res.next()) {
                cuantas = res.getString("codigo_deporte");
            }
            res.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return cuantas;
    }

    public String Codigo_horario(String horario) {
        String cuantas = "";

        try {

            Connection cn = conexion();
            PreparedStatement pstm = cn.prepareStatement(" SELECT codigo_horario"
                    + " FROM horario"
                    + " WHERE descripcion_horario='" + horario + "'");
            ResultSet res = pstm.executeQuery();
            while (res.next()) {
                cuantas = res.getString("codigo_horario");
            }
            res.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return cuantas;
    }

    public String Codigo_categoria(String categoria) {
        String cuantas = "";

        try {

            Connection cn = conexion();
            PreparedStatement pstm = cn.prepareStatement(" SELECT codigo_categoria"
                    + " FROM categoria"
                    + " WHERE descripcion_categoria='" + categoria + "'");
            ResultSet res = pstm.executeQuery();
            while (res.next()) {
                cuantas = res.getString("codigo_categoria");
            }
            res.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return cuantas;
    }

    public int Deporte_categoria(String Codigo_deporte, String Codigo_categoria, String Codigo_horario) {
        int cuantas = 0;

        try {

            Connection cn = conexion();
            PreparedStatement pstm = cn.prepareStatement(" SELECT COUNT(*)"
                    + " FROM deporte_categoria_horario"
                    + " WHERE codigo_deporte='" + Codigo_deporte + "' AND codigo_categoria='" + Codigo_categoria + "' AND codigo_horario='" + Codigo_horario + "'");
            ResultSet res = pstm.executeQuery();
            while (res.next()) {
                cuantas = res.getInt("COUNT(*)");
            }
            res.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return cuantas;
    }

    public int Insertar_deporte_categoria_horario(String Codigo_deporte, String Codigo_categoria, String Codigo_horario) {
        int bien = 0;

        String Codigo = "";
        Random rm = new Random();
        for (int i = 0; i < 5; i++) {
            Codigo += rm.nextInt(10);
        }

        try {

            Connection cn = conexion();
            PreparedStatement rs = cn.prepareStatement("INSERT INTO deporte_categoria_horario"
                    + "(codigo_relacion,codigo_categoria,codigo_horario,codigo_deporte)"
                    + "VALUES (?,?,?,?)");

            rs.setString(1, Codigo);
            rs.setString(2, Codigo_categoria);
            rs.setString(3, Codigo_horario);
            rs.setString(4, Codigo_deporte);

            rs.executeUpdate();
            bien = 1;
        } catch (Exception e) {
            bien = 0;
            JOptionPane.showMessageDialog(null, e);
        }
        return bien;
    }

    public int Deporte_categoria_count(String Codigo_deporte) {
        int cuantas = 0;

        try {

            Connection cn = conexion();
            PreparedStatement pstm = cn.prepareStatement(" SELECT COUNT(*)"
                    + " FROM deporte_categoria_horario"
                    + " WHERE codigo_deporte='" + Codigo_deporte + "'");
            ResultSet res = pstm.executeQuery();
            while (res.next()) {
                cuantas = res.getInt("COUNT(*)");
            }
            res.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return cuantas;
    }

    public String[][] Categorias_deporte(String Codigo_deporte) {
        String Categorias[][] = new String[2][Deporte_categoria_count(Codigo_deporte)];

        try {

            Connection cn = conexion();
            PreparedStatement pstm = cn.prepareStatement(" SELECT codigo_categoria,codigo_horario"
                    + " FROM deporte_categoria_horario"
                    + " WHERE codigo_deporte='" + Codigo_deporte + "'");
            ResultSet res = pstm.executeQuery();
            int i = 0;
            while (res.next()) {
                Categorias[0][i] = res.getString("codigo_categoria");
                Categorias[1][i] = res.getString("codigo_horario");
                i++;
            }
            res.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return Categorias;
    }

    public String Descripcion_categoria(String Codigo_categoria) {
        String cuantas = "";

        try {

            Connection cn = conexion();
            PreparedStatement pstm = cn.prepareStatement(" SELECT descripcion_categoria"
                    + " FROM categoria"
                    + " WHERE codigo_categoria='" + Codigo_categoria + "'");
            ResultSet res = pstm.executeQuery();
            while (res.next()) {
                cuantas = res.getString("descripcion_categoria");
            }
            res.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return cuantas;
    }

    public String Descripcion_horario(String Codigo_horario) {
        String cuantas = "";

        try {

            Connection cn = conexion();
            PreparedStatement pstm = cn.prepareStatement(" SELECT descripcion_horario"
                    + " FROM horario"
                    + " WHERE codigo_horario='" + Codigo_horario + "'");
            ResultSet res = pstm.executeQuery();
            while (res.next()) {
                cuantas = res.getString("descripcion_horario");
            }
            res.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return cuantas;
    }

    /*
     * @return cantidad de relacion
     */
    public int Existe_deporte_categoria(String Codigo_deporte, String Codigo_categoria) {
        int Count = 0;
        try {

            Connection cn = conexion();
            PreparedStatement pstm = cn.prepareStatement(" SELECT COUNT(*)"
                    + " FROM deporte_categoria_horario "
                    + " WHERE codigo_categoria='" + Codigo_categoria + "' AND codigo_deporte='" + Codigo_deporte + "'");
            ResultSet res = pstm.executeQuery();
            while (res.next()) {
                Count = res.getInt("COUNT(*)");
            }
            res.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return Count;
    }

    public int matricularCurso(String Fecha_nacimiento, String Deporte, String Identificacion_estudiante, String Horario) {
        int bueno = 0;
        String Codigo = "";
        Random rm = new Random();
        for (int i = 0; i < 5; i++) {
            Codigo += rm.nextInt(10);
        }

        String Codigo_relacion = Codigo_relacion(Fecha_nacimiento, Deporte, Horario);
        try {

            Connection cn = conexion();
            PreparedStatement rs = cn.prepareStatement("INSERT INTO matricula"
                    + "(codigo_matricula,id,codigo_relacion)"
                    + "VALUES (?,?,?)");

            rs.setString(1, Codigo);
            rs.setString(2, Identificacion_estudiante);
            rs.setString(3, Codigo_relacion);

            rs.executeUpdate();
            bueno = 1;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return bueno;
    }

    public String Codigo_relacion(String Fecha, String Deporte, String Horario) {
        String Codigo_relacion = "";
        int categoria = 0;

        int valor = 0;
        try {
            Integer.parseInt(Fecha);
        } catch (Exception e) {
            valor = 1;
        }

        if (valor == 1) {//No es un numero entonces procedemos a  calcular la edad
            Fechas f = new Fechas();
            categoria = f.calcularCategoria(Fecha);
        } else {
            categoria = Integer.parseInt(Fecha);
        }

        try {

            Connection cn = conexion();
            PreparedStatement pstm = cn.prepareStatement(" SELECT codigo_relacion"
                    + " FROM deporte_categoria_horario"
                    + " WHERE codigo_categoria='" + Codigo_categoria("Sub" + categoria) + "' AND codigo_horario='" + Codigo_horario(Horario) + "' AND codigo_deporte='" + Codigo_deporte(Deporte) + "'");
            ResultSet res = pstm.executeQuery();
            while (res.next()) {
                Codigo_relacion = res.getString("codigo_relacion");
            }
            res.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return Codigo_relacion;
    }

    public String Codigo_relacion2(String categoria, String Deporte, String Horario) {
        String Codigo_relacion = "";

        try {

            Connection cn = conexion();
            PreparedStatement pstm = cn.prepareStatement(" SELECT codigo_relacion"
                    + " FROM deporte_categoria_horario"
                    + " WHERE codigo_categoria='" + Codigo_categoria(categoria) + "' AND codigo_horario='" + Codigo_horario(Horario) + "' AND codigo_deporte='" + Codigo_deporte(Deporte) + "'");
            ResultSet res = pstm.executeQuery();
            while (res.next()) {
                Codigo_relacion = res.getString("codigo_relacion");
            }
            res.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return Codigo_relacion;
    }

    public int Existe_entrenador(String Identtificaion_profesor) {
        int Count = 0;
        try {

            Connection cn = conexion();
            PreparedStatement pstm = cn.prepareStatement(" SELECT COUNT(*) "
                    + "FROM `entrenador`"
                    + " WHERE id='" + Identtificaion_profesor + "'");
            ResultSet res = pstm.executeQuery();
            while (res.next()) {
                Count = res.getInt("COUNT(*)");
            }
            res.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return Count;
    }

    public void Insertar_clase(String Identtificaion_profesor, String Codigo_relacion) {
        try {

            Connection cn = conexion();
            PreparedStatement rs = cn.prepareStatement("INSERT INTO clase"
                    + "(codigo_relacion,id)"
                    + "VALUES (?,?)");

            rs.setString(1, Codigo_relacion);
            rs.setString(2, Identtificaion_profesor);

            rs.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public int Cuantos_profesores() {
        int Count = 0;
        try {

            Connection cn = conexion();
            PreparedStatement pstm = cn.prepareStatement(" SELECT COUNT(*)"
                    + " FROM entrenador");
            ResultSet res = pstm.executeQuery();
            while (res.next()) {
                Count = res.getInt("COUNT(*)");
            }
            res.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return Count;
    }

    public String[][] Profesores() {
        String datos[][] = new String[5][Cuantos_profesores()];

        try {

            Connection cn = conexion();
            PreparedStatement pstm = cn.prepareStatement(" SELECT persona.id,persona.nombre,persona.apellido,persona.telefono,persona.celular "
                    + "FROM persona "
                    + "INNER JOIN entrenador on entrenador.id = persona.id");
            ResultSet res = pstm.executeQuery();
            int i = 0;
            while (res.next()) {
                datos[0][i] = res.getString("id");
                datos[1][i] = res.getString("nombre");
                datos[2][i] = res.getString("apellido");
                datos[3][i] = res.getString("telefono");
                datos[4][i] = res.getString("celular");
                i++;
            }
            res.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return datos;
    }

    public boolean Insertar_docente(String Identificaion) {
        try {

            Connection cn = conexion();
            PreparedStatement rs = cn.prepareStatement("INSERT INTO entrenador"
                    + "(id)"
                    + "VALUES (?)");

            rs.setString(1, Identificaion);

            rs.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    public boolean Insertar_usuario(String Email, String clave, String Identificacion) {
        try {

            Connection cn = conexion();
            PreparedStatement rs = cn.prepareStatement("INSERT INTO usuario"
                    + "(e_mail,clave,tipo_usuario,cambio_contraseña,id)"
                    + "VALUES (?,?,?,?,?)");

            rs.setString(1, Email);
            rs.setString(2, clave);
            rs.setInt(3, 0);//cero para los docentes
            rs.setInt(4, 1);
            rs.setString(5, Identificacion);

            rs.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    public String Identificacion_usuario(String correo) {
        String Identificacion_docente = "";
        try {

            Connection cn = conexion();
            PreparedStatement pstm = cn.prepareStatement(" SELECT id "
                    + " FROM usuario "
                    + " WHERE e_mail='" + correo + "'");
            ResultSet res = pstm.executeQuery();
            int i = 0;
            while (res.next()) {
                Identificacion_docente = res.getString("id");
                i++;
            }
            res.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return Identificacion_docente;
    }

    public String Nombre_docente(String Identificaicon) {
        String Nombre = "";
        try {
            Connection cn = conexion();
            PreparedStatement pstm = cn.prepareStatement(" SELECT nombre"
                    + " FROM persona"
                    + " WHERE id='" + Identificaicon + "'");
            ResultSet res = pstm.executeQuery();
            while (res.next()) {
                Nombre = res.getString("nombre");
            }
        } catch (Exception e) {
        }
        return Nombre;
    }

    public String[][] Horarios_docente(String Identificacion_docente) {
        String datos[][] = new String[4][Cuantos_horarios_docente(Identificacion_docente)];

        try {
            Connection cn = conexion();
            PreparedStatement pstm = cn.prepareStatement(" SELECT clase.codigo_relacion,"
                    + " deporte.nombre,"
                    + " categoria.descripcion_categoria,"
                    + " horario.descripcion_horario "
                    + " FROM  clase  "
                    + " INNER JOIN deporte_categoria_horario on clase.codigo_relacion = deporte_categoria_horario.codigo_relacion"
                    + " INNER JOIN categoria on deporte_categoria_horario.codigo_categoria = categoria.codigo_categoria"
                    + " INNER JOIN deporte on deporte_categoria_horario.codigo_deporte=deporte.codigo_deporte"
                    + " inner JOIN horario on deporte_categoria_horario.codigo_horario=horario.codigo_horario"
                    + " WHERE clase.id='33333333412'");
            ResultSet res = pstm.executeQuery();
            int i = 0;
            while (res.next()) {
                datos[0][i] = res.getString("codigo_relacion");
                datos[1][i] = res.getString("nombre");
                datos[2][i] = res.getString("descripcion_categoria");
                datos[3][i] = res.getString("descripcion_horario");
                i++;
            }
        } catch (Exception e) {
        }

        return datos;
    }

    public int Cuantos_horarios_docente(String Identificacion_docente) {
        int Count = 0;
        try {
            Connection cn = conexion();
            PreparedStatement pstm = cn.prepareStatement(" SELECT COUNT(*)"
                    + " FROM clase"
                    + " WHERE id='" + Identificacion_docente + "'");
            ResultSet res = pstm.executeQuery();
            while (res.next()) {
                Count = res.getInt("COUNT(*)");
            }
        } catch (Exception e) {
        }
        return Count;
    }

    public String[][] Clase_estudiante(String Codigo_relacion) {
        String datos[][] = new String[3][Cuantos_en_la_clase(Codigo_relacion)];

        try {
            Connection cn = conexion();
            PreparedStatement pstm = cn.prepareStatement(" SELECT estudiante.id, estudiante.nombre,estudiante.apellido "
                    + "FROM estudiante "
                    + "INNER JOIN matricula ON matricula.id=estudiante.id "
                    + "WHERE matricula.codigo_relacion='" + Codigo_relacion + "'");
            ResultSet res = pstm.executeQuery();
            int i = 0;
            while (res.next()) {
                datos[0][i] = res.getString("id");
                datos[1][i] = res.getString("nombre");
                datos[2][i] = res.getString("apellido");
                i++;
            }
        } catch (Exception e) {
        }

        return datos;
    }

    public int Cuantos_en_la_clase(String Codigo_relacion) {
        int Count = 0;
        try {
            Connection cn = conexion();
            PreparedStatement pstm = cn.prepareStatement(" SELECT COUNT(*) "
                    + "FROM matricula "
                    + "WHERE codigo_relacion='" + Codigo_relacion + "'");
            ResultSet res = pstm.executeQuery();
            while (res.next()) {
                Count = res.getInt("COUNT(*)");
            }
        } catch (Exception e) {
        }
        return Count;
    }

    public boolean Insertar_seguimiento(String Id_estudiante, String Codigor, String Cumplimiento, String Descripcion) {
        String Coidgo_matricula = Codigo_matricula(Id_estudiante, Codigor);
        String Codigo = "";
        Random rm = new Random();
        for (int i = 0; i < 5; i++) {
            Codigo += rm.nextInt(10);
        }
        try {
            Connection cn = conexion();
            PreparedStatement rs = cn.prepareStatement("INSERT INTO seguimiento"
                    + "(codigo,cumplimiento,descripcion,codigo_matricula)"
                    + "VALUES (?,?,?,?)");

            rs.setString(1, Codigo);
            rs.setString(2, Cumplimiento);
            rs.setString(3, Descripcion);
            rs.setString(4, Coidgo_matricula);

            rs.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String Codigo_matricula(String Id_estudiante, String Codigor) {
        String matricula = "";
        try {
            Connection cn = conexion();
            PreparedStatement pstm = cn.prepareStatement(" SELECT codigo_matricula "
                    + "FROM matricula "
                    + "WHERE id='" + Id_estudiante + "' AND codigo_relacion='" + Codigor + "'");
            ResultSet res = pstm.executeQuery();
            while (res.next()) {
                matricula = res.getString("codigo_matricula");
            }
        } catch (Exception e) {
        }
        return matricula;
    }

    public int Cambio_clave(String Id_docente) {//Preguntamos si ya cambio una vez la clave
        int i = 0;
        try {
            Connection cn = conexion();
            PreparedStatement pstm = cn.prepareStatement(" SELECT cambio_contraseña "
                    + "FROM usuario "
                    + "WHERE id='" + Id_docente + "'");
            ResultSet res = pstm.executeQuery();
            while (res.next()) {
                i = res.getInt("cambio_contraseña");
            }
        } catch (Exception e) {
        }
        return i;
    }

    public boolean Cambiar_clave(String Nueva_clave, String Id) {
        try {
            Connection cn = conexion();
            PreparedStatement rs = cn.prepareStatement("UPDATE usuario SET clave=?, cambio_contraseña=? "
                    + " WHERE (id='" + Id + "')");

            rs.setString(1, Nueva_clave);
            rs.setInt(2, 0);

            rs.executeUpdate();
            JOptionPane.showMessageDialog(null, "Se actualizo correctamente ");
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar" + e);
            return false;
        }
    }
}
