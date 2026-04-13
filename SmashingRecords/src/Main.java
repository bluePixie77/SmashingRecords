import gui.DataBase;
import processing.core.PApplet;
import gui.GUI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static gui.smashRecFonts.Sizes.medidaParrafo;

public class Main extends PApplet {

    // Atributos
        // GUI
    GUI gui;
    public static DataBase db;
    boolean loginWrong = false;
    
    public static void main(String[] args) {
        PApplet.main("Main");
    }

    public void settings(){
        fullScreen();
    }
    public void setup(){
        db = new DataBase("admin", "l0n3lyr04d", "SmashingRecords");
        db.connect();

        gui = new GUI(this, db);
    }

    public void draw(){
        // Dibuixa la pantalla corresponent
        switch(gui.pantallaActual){
            case PLOGO:      gui.displayPantallaPLogo(this);
                             break;
            case INICIO:     gui.displayPantallaInicioSesion(this);
                             break;
            case REGISTRO:   gui.displayPantallaRegistro(this);
                             break;
            case USUARIO:    gui.displayPantallaUsuario(this);
                             break;
            case VINILOS:    gui.displayPantallaVinilos(this);
                             break;
            case CDS:        gui.displayPantallaCDs(this);
                             break;
            case CONCIERTOS: gui.displayPantallaConciertos(this);
                             break;
            case ESTADISTICAS:gui.displayPantallaEstadisticas(this);
                             break;
            case AGREGAR:    gui.displayPantallaAgregarMusica(this);
                             break;
            case AGREGAR_CONCERT:gui.displayPantallaAgregarConcert(this);
                             break;
            case DETALLE:    gui.displayPantallaAgregarMusica(this);
                             break;
            case DETALLE_CONCIERTO:gui.displayPantallaAgregarConcert(this);
                             break;
        }
        updateCursor(this);

        pushStyle();
        if(loginWrong && gui.pantallaActual== GUI.PANTALLA.INICIO){
            fill(gui.pink); textAlign(CENTER, CENTER); textFont(gui.appFonts.getThirdFont()); textSize(medidaParrafo+4);
            text("Nombre y/o contraseña incorrectos", width*0.5f, height*0.68f);
        }
        popStyle();
    }

    public void keyTyped(){
        gui.tFInicioSesion1.keyTyped(key);
        gui.tFInicioSesion2.keyTyped(key);
        gui.tFBuscador.keyTyped(key);
        gui.tANotasUsuario.keyTyped(key);
        gui.tANotasAgregar.keyTyped(key);
        if (gui.pantallaActual == GUI.PANTALLA.AGREGAR || gui.pantallaActual == GUI.PANTALLA.DETALLE) {
            for (gui.smashRecPantallas.TextField tf : gui.tFMusica) tf.keyTyped(key);
        } else if (gui.pantallaActual == GUI.PANTALLA.AGREGAR_CONCERT || gui.pantallaActual == GUI.PANTALLA.DETALLE_CONCIERTO) {
            for (gui.smashRecPantallas.TextField tf : gui.tFConcierto) tf.keyTyped(key);
        }
        if (gui.pantallaActual == GUI.PANTALLA.REGISTRO) {
            gui.tFRegistroNombre.keyTyped(key);
            gui.tFRegistroCorreo.keyTyped(key);
            gui.tFRegistroPass.keyTyped(key);
        }
    }
    public void keyPressed(){
        gui.tFInicioSesion1.keyPressed(keyCode);
        gui.tFInicioSesion2.keyPressed(keyCode);
        gui.tFBuscador.keyPressed(keyCode);
        gui.tANotasUsuario.keyPressed(keyCode);
        gui.tANotasAgregar.keyPressed(keyCode);
        if (gui.pantallaActual == GUI.PANTALLA.AGREGAR || gui.pantallaActual == GUI.PANTALLA.DETALLE) {
            for (gui.smashRecPantallas.TextField tf : gui.tFMusica) tf.keyPressed(keyCode);
        } else if (gui.pantallaActual == GUI.PANTALLA.AGREGAR_CONCERT || gui.pantallaActual == GUI.PANTALLA.DETALLE_CONCIERTO) {
            for (gui.smashRecPantallas.TextField tf : gui.tFConcierto) tf.keyPressed(keyCode);
        }

        if (keyCode == ENTER || keyCode == RETURN) {
            if (gui.pantallaActual == GUI.PANTALLA.VINILOS ||
                    gui.pantallaActual == GUI.PANTALLA.CDS ||
                    gui.pantallaActual == GUI.PANTALLA.CONCIERTOS) {
                gui.textoBusqueda = gui.tFBuscador.getText().trim();
                gui.recargarCards(this);
            }
        }
        if (gui.pantallaActual == GUI.PANTALLA.REGISTRO) {
            gui.tFRegistroNombre.keyPressed(keyCode);
            gui.tFRegistroCorreo.keyPressed(keyCode);
            gui.tFRegistroPass.keyPressed(keyCode);
        }
    }

    public void mousePressed(){
        if(gui.pantallaActual== GUI.PANTALLA.INICIO) {
            if (gui.b6.mouseOverButton(this)) {
                println("B6 has been pressed.");
                String nom = gui.tFInicioSesion1.getText();
                String pass = gui.tFInicioSesion2.getText();
                if (db.loginCorrecto(nom, pass)) {
                    gui.usuarioActual = nom;
                    gui.datosUsuarioActual = db.getDatosUsuario(nom);
                    String fotoDB = db.getFotoPerfilUsuario(nom);
                    if (!fotoDB.isEmpty()) {
                        gui.imgPerfil = loadImage(gui.rutaCarpeta + fotoDB);
                        gui.titolPerfil = fotoDB;
                    }
                    gui.tANotasUsuario.setText(db.getDescripcionUsuario(nom));
                    gui.pantallaActual = GUI.PANTALLA.USUARIO;
                } else {
                    loginWrong = true;
                }
            } else if(gui.bIrRegistro.mouseOverButton(this)) {
                gui.pantallaActual = GUI.PANTALLA.REGISTRO;
            }
            gui.tFInicioSesion1.isPressed(this);
            gui.tFInicioSesion2.isPressed(this);
        }
        else if(gui.pantallaActual == GUI.PANTALLA.REGISTRO) {
            if (gui.bCrearCuenta.mouseOverButton(this)) {
                String nombre = gui.tFRegistroNombre.getText().trim();
                String correo = gui.tFRegistroCorreo.getText().trim();
                String pass   = gui.tFRegistroPass.getText().trim();

                if (nombre.isEmpty() || correo.isEmpty() || pass.isEmpty()) {
                    gui.mensajeRegistro = "Rellena todos los campos.";
                    gui.registroOk = false;
                } else {
                    String resultado = db.registrarUsuario(nombre, correo, pass);
                    if (resultado.equals("ok")) {
                        gui.mensajeRegistro = "¡Cuenta creada! Ya puedes iniciar sesión.";
                        gui.registroOk = true;
                        // Limpiar campos
                        gui.tFRegistroNombre.setText("");
                        gui.tFRegistroCorreo.setText("");
                        gui.tFRegistroPass.setText("");
                    } else {
                        gui.mensajeRegistro = resultado;
                        gui.registroOk = false;
                    }
                }
                gui.tiempoMensajeRegistro = 240;
            }
            if (gui.bVolverInicio.mouseOverButton(this)) {
                gui.tFRegistroNombre.setText("");
                gui.tFRegistroCorreo.setText("");
                gui.tFRegistroPass.setText("");
                gui.pantallaActual = GUI.PANTALLA.INICIO;
            }
            gui.tFRegistroNombre.isPressed(this);
            gui.tFRegistroCorreo.isPressed(this);
            gui.tFRegistroPass.isPressed(this);
        }
        else if(gui.pantallaActual== GUI.PANTALLA.USUARIO){
            if(gui.b1.mouseOverButton(this)){
                gui.mostrarListaDeseos = false; // reset al entrar
                gui.pantallaActual = GUI.PANTALLA.VINILOS;
                gui.textoBusqueda = "";
                gui.tFBuscador.setText("");
                gui.recargarCards(this);
            }else if(gui.b2.mouseOverButton(this)){
                gui.mostrarListaDeseos = false;
                gui.pantallaActual = GUI.PANTALLA.CDS;
                gui.textoBusqueda = "";
                gui.tFBuscador.setText("");
                gui.recargarCards(this);
            }else if(gui.b3.mouseOverButton(this)){
                gui.mostrarListaDeseos = false;
                gui.pantallaActual = GUI.PANTALLA.CONCIERTOS;
                gui.textoBusqueda = "";
                gui.tFBuscador.setText("");
                gui.recargarCards(this);
            }else if(gui.b4.mouseOverButton(this)){
                gui.pantallaActual = GUI.PANTALLA.ESTADISTICAS;
                gui.actualizarDatosGraficos(this);
            }else if(gui.b5.mouseOverButton(this)){
                gui.pantallaActual = GUI.PANTALLA.USUARIO;
            }else if(gui.b7.mouseOverButton(this)){
                gui.tFInicioSesion1.setText("");
                gui.tFInicioSesion2.setText("");
                gui.usuarioActual = "";
                gui.imgPerfil = null;
                gui.titolPerfil = "";
                gui.filePerfil = null;
                gui.pantallaActual = GUI.PANTALLA.INICIO;
            } else if(gui.bGuardarNotasUsuario.mouseOverButton(this)) {
                db.actualizarDescripcionUsuario(gui.usuarioActual, gui.tANotasUsuario.getText());
                gui.tiempoNotasGuardadas = 180; // 3 segundos a 60fps
            } else if(gui.bCambiarFotoPerfil.mouseOverButton(this)) {
                selectInput("Selecciona una imagen de perfil...", "fileSelectedPerfil");
            }
            gui.tANotasUsuario.isPressed(this);
        }

        else if(gui.pantallaActual== GUI.PANTALLA.VINILOS){
            if(gui.b1.mouseOverButton(this)){
                gui.mostrarListaDeseos = false;
                gui.pantallaActual = GUI.PANTALLA.VINILOS;
                gui.textoBusqueda = "";
                gui.tFBuscador.setText("");
                gui.recargarCards(this);
            }else if(gui.b2.mouseOverButton(this)){
                gui.mostrarListaDeseos = false;
                gui.pantallaActual = GUI.PANTALLA.CDS;
                gui.textoBusqueda = "";
                gui.tFBuscador.setText("");
                gui.recargarCards(this);
            }else if(gui.b3.mouseOverButton(this)){
                gui.mostrarListaDeseos = false;
                gui.pantallaActual = GUI.PANTALLA.CONCIERTOS;
                gui.textoBusqueda = "";
                gui.tFBuscador.setText("");
                gui.recargarCards(this);
            }else if(gui.b4.mouseOverButton(this)){
                gui.pantallaActual = GUI.PANTALLA.ESTADISTICAS;
                gui.actualizarDatosGraficos(this);
            }else if(gui.b5.mouseOverButton(this)){
                gui.pantallaActual = GUI.PANTALLA.USUARIO;
            }else if(gui.rBPlus.mouseOverButton(this)){
                gui.pantallaAnterior = gui.pantallaActual; // Guardamos si venimos de VINILOS, CDS o CONCIERTOS
                gui.pantallaActual = GUI.PANTALLA.AGREGAR;

            } else if (gui.rBHeart.mouseOverButton(this)) {
                gui.mostrarListaDeseos = !gui.mostrarListaDeseos;
                gui.recargarCards(this);
            } else if (gui.rBFilter.mouseOverButton(this)) {
                gui.ciclarOrden();
                gui.recargarCards(this);
            } else if(gui.bNext.mouseOverButton(this)){
                gui.pcMusica.nextPage();
            } else if(gui.bPrev.mouseOverButton(this) && gui.bPrev.isEnabled()){
                gui.pcMusica.prevPage();
            }  else if(gui.bDetalle.mouseOverButton(this) && gui.pcMusica.selectedCard != -1) {
            int idx = gui.pcMusica.selectedCard;
            gui.idSeleccionado = Integer.parseInt(gui.pcMusica.cards[idx].getRawData()[0]);
            String[] datos = db.getDetalleViniloCD(gui.idSeleccionado);
            gui.modoDetalle = true;
            gui.pantallaAnterior = gui.pantallaActual;
            gui.cargarDatosDetalle(this, datos, false);
            gui.pantallaActual = GUI.PANTALLA.DETALLE;
            } else{
                    gui.pcMusica.checkCardSelection(this);
            }
            gui.tFBuscador.isPressed(this);
        }else if(gui.pantallaActual== GUI.PANTALLA.CDS){
            if(gui.b1.mouseOverButton(this)){
                gui.mostrarListaDeseos = false;
                gui.pantallaActual = GUI.PANTALLA.VINILOS;
                gui.textoBusqueda = "";
                gui.tFBuscador.setText("");
                gui.recargarCards(this);
            }else if(gui.b2.mouseOverButton(this)){
                gui.mostrarListaDeseos = false;
                gui.pantallaActual = GUI.PANTALLA.CDS;
                gui.textoBusqueda = "";
                gui.tFBuscador.setText("");
                gui.recargarCards(this);
            }else if(gui.b3.mouseOverButton(this)){
                gui.mostrarListaDeseos = false;
                gui.pantallaActual = GUI.PANTALLA.CONCIERTOS;
                gui.textoBusqueda = "";
                gui.tFBuscador.setText("");
                gui.recargarCards(this);
            }else if(gui.b4.mouseOverButton(this)){
                gui.pantallaActual = GUI.PANTALLA.ESTADISTICAS;
                gui.actualizarDatosGraficos(this);
            }else if(gui.b5.mouseOverButton(this)){
                gui.pantallaActual = GUI.PANTALLA.USUARIO;
            }else if(gui.rBPlus.mouseOverButton(this)){
                gui.pantallaAnterior = gui.pantallaActual; // Guardamos si venimos de VINILOS, CDS o CONCIERTOS
                gui.pantallaActual = GUI.PANTALLA.AGREGAR;

            } else if(gui.rBHeart.mouseOverButton(this)) {
            gui.mostrarListaDeseos = !gui.mostrarListaDeseos;
            gui.recargarCards(this);
            } else if (gui.rBFilter.mouseOverButton(this)) {
                gui.ciclarOrden();
                gui.recargarCards(this);
            } else if(gui.bNext.mouseOverButton(this)){
                gui.pcMusica.nextPage();
            } else if(gui.bPrev.mouseOverButton(this) && gui.bPrev.isEnabled()){
                gui.pcMusica.prevPage();
            } else if(gui.bDetalle.mouseOverButton(this) && gui.pcMusica.selectedCard != -1) {
            int idx = gui.pcMusica.selectedCard;
            gui.idSeleccionado = Integer.parseInt(gui.pcMusica.cards[idx].getRawData()[0]);
            String[] datos = db.getDetalleViniloCD(gui.idSeleccionado);
            gui.modoDetalle = true;
            gui.pantallaAnterior = gui.pantallaActual;
            gui.cargarDatosDetalle(this, datos, false);
            gui.pantallaActual = GUI.PANTALLA.DETALLE;
            } else{
                gui.pcMusica.checkCardSelection(this);
            }
            gui.tFBuscador.isPressed(this);
        }else if(gui.pantallaActual== GUI.PANTALLA.CONCIERTOS){
            if(gui.b1.mouseOverButton(this)){
                gui.mostrarListaDeseos = false;
                gui.pantallaActual = GUI.PANTALLA.VINILOS;
                gui.textoBusqueda = "";
                gui.tFBuscador.setText("");
                gui.recargarCards(this);
            }else if(gui.b2.mouseOverButton(this)){
                gui.mostrarListaDeseos = false;
                gui.pantallaActual = GUI.PANTALLA.CDS;
                gui.textoBusqueda = "";
                gui.tFBuscador.setText("");
                gui.recargarCards(this);
            }else if(gui.b3.mouseOverButton(this)){
                gui.mostrarListaDeseos = false;
                gui.pantallaActual = GUI.PANTALLA.CONCIERTOS;
                gui.textoBusqueda = "";
                gui.tFBuscador.setText("");
                gui.recargarCards(this);
            }else if(gui.b4.mouseOverButton(this)){
                gui.pantallaActual = GUI.PANTALLA.ESTADISTICAS;
                gui.actualizarDatosGraficos(this);
            }else if(gui.b5.mouseOverButton(this)){
                gui.pantallaActual = GUI.PANTALLA.USUARIO;
            }else if(gui.rBPlus.mouseOverButton(this)){
                gui.pantallaAnterior = gui.pantallaActual; // Guardamos si venimos de VINILOS, CDS o CONCIERTOS
                gui.pantallaActual = GUI.PANTALLA.AGREGAR_CONCERT;
            } else if(gui.rBHeart.mouseOverButton(this)) {
                gui.mostrarListaDeseos = !gui.mostrarListaDeseos;
                gui.recargarCards(this);
            } else if (gui.rBFilter.mouseOverButton(this)) {
                gui.ciclarOrden();
                gui.recargarCards(this);
            } else if(gui.bNext.mouseOverButton(this) && gui.bNext.isEnabled()){
                gui.pcConcert.nextPage();
            } else if(gui.bPrev.mouseOverButton(this) && gui.bPrev.isEnabled()){
                gui.pcConcert.prevPage();
            } else if (gui.bDetalle.mouseOverButton(this) && gui.pcConcert.selectedCard != -1) {
                int idx = gui.pcConcert.selectedCard;
                gui.idSeleccionado = Integer.parseInt(gui.pcConcert.cards[idx].getRawData()[0]);
                String[] datos = db.getDetalleConcierto(gui.idSeleccionado);
                gui.modoDetalle = true;
                gui.pantallaAnterior = gui.pantallaActual;
                gui.cargarDatosDetalle(this, datos, true);
                gui.pantallaActual = GUI.PANTALLA.DETALLE_CONCIERTO;
            } else {
                gui.pcConcert.checkCardSelection(this);
            }
            gui.tFBuscador.isPressed(this);
        }else if(gui.pantallaActual== GUI.PANTALLA.ESTADISTICAS) {
            if (gui.b1.mouseOverButton(this)) {
                gui.mostrarListaDeseos = false;
                gui.pantallaActual = GUI.PANTALLA.VINILOS;
                gui.textoBusqueda = "";
                gui.tFBuscador.setText("");
                gui.recargarCards(this);
            } else if (gui.b2.mouseOverButton(this)) {
                gui.mostrarListaDeseos = false;
                gui.pantallaActual = GUI.PANTALLA.CDS;
                gui.textoBusqueda = "";
                gui.tFBuscador.setText("");
                gui.recargarCards(this);
            } else if (gui.b3.mouseOverButton(this)) {
                gui.mostrarListaDeseos = false;
                gui.pantallaActual = GUI.PANTALLA.CONCIERTOS;
                gui.textoBusqueda = "";
                gui.tFBuscador.setText("");
                gui.recargarCards(this);
            } else if (gui.b4.mouseOverButton(this)) {
                gui.pantallaActual = GUI.PANTALLA.ESTADISTICAS;
                gui.actualizarDatosGraficos(this);
            } else if (gui.b5.mouseOverButton(this)) {
                gui.pantallaActual = GUI.PANTALLA.USUARIO;
            } else if (gui.bCatVinilos.mouseOverButton(this) && gui.bCatVinilos.enabled) {
                gui.categoriaActual = GUI.CatEstadistica.VINILOS;
                gui.actualizarDatosGraficos(this);
                gui.actualizarEstadoBotones();
            } else if (gui.bCatCDs.mouseOverButton(this) && gui.bCatCDs.enabled) {
                gui.categoriaActual = GUI.CatEstadistica.CDS;
                gui.actualizarDatosGraficos(this);
                gui.actualizarEstadoBotones();
            } else if (gui.bCatConciertos.mouseOverButton(this) && gui.bCatConciertos.enabled) {
                gui.categoriaActual = GUI.CatEstadistica.CONCIERTOS;
                gui.actualizarDatosGraficos(this);
                gui.actualizarEstadoBotones();
            }
            // Navegación de páginas (cambiar de tipo de gráfico)
            if (gui.pcStats != null) {
                if (gui.bNext.mouseOverButton(this)) gui.pcStats.nextPage();
                if (gui.bPrev.mouseOverButton(this)) gui.pcStats.prevPage();
            }
        }else if(gui.pantallaActual == GUI.PANTALLA.AGREGAR || gui.pantallaActual == GUI.PANTALLA.AGREGAR_CONCERT ||
                gui.pantallaActual == GUI.PANTALLA.DETALLE || gui.pantallaActual == GUI.PANTALLA.DETALLE_CONCIERTO){
            if (gui.bOk.mouseOverButton(this)) {

                // PRINTS DE DIAGNÓSTICO — borrar después
                System.out.println("=== OK pulsado ===");
                System.out.println("pantallaActual: " + gui.pantallaActual);
                System.out.println("pantallaAnterior: " + gui.pantallaAnterior);
                System.out.println("modoDetalle: " + gui.modoDetalle);
                System.out.println("tFMusica[0].getText(): " + gui.tFMusica[0].getText());
                System.out.println("tFMusica[1].getText(): " + gui.tFMusica[1].getText());
                System.out.println("tFConcierto[0].getText(): " + gui.tFConcierto[0].getText());

                // Datos del formulario (comunes a insertar y actualizar)
                int estrellas = gui.cbl.getNumSelected();
                String notas = gui.tANotasAgregar.getText();
                char lDeseo = gui.esListaDeseos ? 'S' : 'N';
                String generosFinal = "";
                for (int i = 0; i < gui.cbGenero.length; i++) {
                    if (gui.cbGenero[i].isChecked()) {
                        if (!generosFinal.isEmpty()) generosFinal += ", ";
                        generosFinal += gui.nombresGenero[i];
                    }
                }

                if (gui.pantallaAnterior == GUI.PANTALLA.VINILOS || gui.pantallaAnterior == GUI.PANTALLA.CDS) {
                    String titulo  = gui.tFMusica[0].getText();
                    String artista = gui.tFMusica[1].getText();
                    String fecha   = gui.tFMusica[2].getText();
                    String edicion = gui.tFMusica[3].getText();
                    char tipo = (gui.pantallaAnterior == GUI.PANTALLA.VINILOS) ? 'V' : 'C';
                    String ubi = gui.nombresUbicacion[gui.rbgUbicacion.selectedOption];
                    String ori = gui.nombresOrigen[gui.rbgOrigen.selectedOption];

                    if (gui.modoDetalle) {
                        db.actualizarViniloCD(gui.idSeleccionado, titulo, artista, fecha,
                                edicion, ubi, generosFinal, ori, notas, estrellas, lDeseo);
                        if (gui.file != null && !gui.titol.isEmpty()) {
                            copiar(gui.file, gui.rutaCarpeta, gui.titol);
                            db.actualizarImagenVinilo(gui.titol, gui.idSeleccionado);
                        }
                    } else {
                        int idGenerado = db.insertarViniloCD(titulo, artista, fecha, edicion, ubi,
                                generosFinal, ori, notas, gui.usuarioActual,
                                tipo, estrellas, lDeseo);
                        if (gui.file != null && !gui.titol.isEmpty() && idGenerado != -1) {
                            copiar(gui.file, gui.rutaCarpeta, gui.titol);
                            db.insertarImagen(gui.titol, idGenerado, -1);
                        }
                    }

                } else if (gui.pantallaAnterior == GUI.PANTALLA.CONCIERTOS) {
                    String titulo  = gui.tFConcierto[0].getText();
                    String artista = gui.tFConcierto[1].getText();
                    String fecha   = gui.tFConcierto[2].getText();
                    String lugar   = gui.tFConcierto[3].getText();

                    if (gui.modoDetalle) {
                        db.actualizarConcierto(gui.idSeleccionado, titulo, artista, fecha,
                                lugar, generosFinal, notas, estrellas, lDeseo);
                        if (gui.file != null && !gui.titol.isEmpty()) {
                            copiar(gui.file, gui.rutaCarpeta, gui.titol);
                            db.actualizarImagenConcierto(gui.titol, gui.idSeleccionado);
                        }
                    } else {
                        int idGenerado = db.insertarConcierto(titulo, artista, fecha, lugar,
                                generosFinal, notas, gui.usuarioActual,
                                estrellas, lDeseo);
                        if (gui.file != null && !gui.titol.isEmpty() && idGenerado != -1) {
                            copiar(gui.file, gui.rutaCarpeta, gui.titol);
                            db.insertarImagen(gui.titol, -1, idGenerado);
                        }
                    }
                }

                gui.modoDetalle = false;
                gui.mostrarListaDeseos = false;
                gui.resetPantallaAgregar();
                gui.pantallaActual = gui.pantallaAnterior;
                gui.recargarCards(this);
            }
            if (gui.rBHeartAgregar.mouseOverButton(this)) {
                gui.esListaDeseos = !gui.esListaDeseos;
            }
            if (gui.pantallaActual == GUI.PANTALLA.AGREGAR
                    || gui.pantallaActual == GUI.PANTALLA.DETALLE) {
                for(gui.smashRecPantallas.TextField tf : gui.tFMusica) tf.isPressed(this);
            } else {
                for(gui.smashRecPantallas.TextField tf : gui.tFConcierto) tf.isPressed(this);
            }
            if(gui.bCancelar.mouseOverButton(this)) {
                // Volvemos exactamente de donde vinimos
                gui.resetPantallaAgregar();
                gui.pantallaActual = gui.pantallaAnterior;
            } else if (gui.rBDelete.mouseOverButton(this)) {
                    gui.popUpConfirmacionEliminar.setVisible(true);
            }else if(gui.bLoadImage.mouseOverButton(this) && !gui.popUpConfirmacionEliminar.visible){
                // Obrim el dialeg
                selectInput("Selecciona una imatge ...", "fileSelected");
            } else if(gui.bSaveImageToDB.mouseOverButton(this) && !gui.popUpConfirmacionEliminar.visible){
                if(gui.file != null && !gui.titol.isEmpty()){
                    copiar(gui.file, gui.rutaCarpeta, gui.titol);
                    gui.imagenGuardadaOk = true;
                } else {
                    gui.imagenGuardadaOk = false;
                }
                gui.tiempoMensajeGuardado = 180; // 3 segundos a 60fps
            }
            gui.cbl.checkMouse(this);
            gui.tANotasAgregar.isPressed(this);

            // Checkboxes género
            for (int i = 0; i < gui.cbGenero.length; i++) {
                if (gui.cbGenero[i].onMouseOver(this)) {
                    gui.cbGenero[i].toggle();
                }
            }
            // RadioButtons
            gui.rbgUbicacion.updateOnClick(this);
            gui.rbgOrigen.updateOnClick(this);
            // Botones PopUp eliminar
            if (gui.popUpConfirmacionEliminar.visible) {
                if (gui.popUpConfirmacionEliminar.bAceptar.mouseOverButton(this)) {
                    if (gui.pantallaAnterior == GUI.PANTALLA.VINILOS || gui.pantallaAnterior == GUI.PANTALLA.CDS) {
                        db.eliminarViniloCD(gui.idSeleccionado);
                    } else {
                        db.eliminarConcierto(gui.idSeleccionado);
                    }
                    gui.popUpConfirmacionEliminar.setVisible(false);
                    gui.resetPantallaAgregar();
                    gui.pantallaActual = gui.pantallaAnterior;
                    gui.recargarCards(this);
                } else if (gui.popUpConfirmacionEliminar.bCancelar.mouseOverButton(this)) {
                    gui.popUpConfirmacionEliminar.setVisible(false);
                }
            }

        }
        /*  b1 // Vinilos
            b2 // CDs
            b3 // Conciertos
            b4 // Estadísticas
            b5 // Sesión (de la sidebar)
            b6 // Iniciar sesión
            b7 // Cerrar sesión */
    }

    public void updateCursor(PApplet p5){
        boolean hand = false;
        // Botones siempre activos (sidebar)
        if (gui.pantallaActual != GUI.PANTALLA.INICIO &&
                gui.pantallaActual != GUI.PANTALLA.REGISTRO &&
                gui.pantallaActual != GUI.PANTALLA.PLOGO &&
                gui.pantallaActual != GUI.PANTALLA.AGREGAR &&
                gui.pantallaActual != GUI.PANTALLA.AGREGAR_CONCERT &&
                gui.pantallaActual != GUI.PANTALLA.DETALLE &&
                gui.pantallaActual != GUI.PANTALLA.DETALLE_CONCIERTO) {
            hand = hand ||
                    gui.b1.updateHandCursor(p5) && gui.b1.isEnabled() ||
                    gui.b2.updateHandCursor(p5) && gui.b2.isEnabled() ||
                    gui.b3.updateHandCursor(p5) && gui.b3.isEnabled() ||
                    gui.b4.updateHandCursor(p5) && gui.b4.isEnabled() ||
                    gui.b5.updateHandCursor(p5) && gui.b5.isEnabled();
        }

        // Por pantalla
        switch (gui.pantallaActual) {
            case INICIO:
                hand = hand ||
                        gui.b6.updateHandCursor(p5) && gui.b6.isEnabled() ||
                        gui.bIrRegistro.updateHandCursor(p5) && gui.bIrRegistro.isEnabled();
                break;
            case REGISTRO:
                hand = hand ||
                        gui.bCrearCuenta.updateHandCursor(p5) && gui.bCrearCuenta.isEnabled() ||
                        gui.bVolverInicio.updateHandCursor(p5) && gui.bVolverInicio.isEnabled();
                break;
            case USUARIO:
                hand = hand ||
                        gui.b7.updateHandCursor(p5) && gui.b7.isEnabled() ||
                        gui.bGuardarNotasUsuario.updateHandCursor(p5) && gui.bGuardarNotasUsuario.isEnabled() ||
                        gui.bCambiarFotoPerfil.updateHandCursor(p5) && gui.bCambiarFotoPerfil.isEnabled();
                break;
            case VINILOS:
            case CDS:
                hand = hand ||
                        gui.rBPlus.updateHandCursor(p5) && gui.rBPlus.enabled ||
                        gui.rBFilter.updateHandCursor(p5) && gui.rBFilter.enabled ||
                        gui.rBHeart.mouseOverButton(p5) && gui.rBHeart.enabled ||
                        gui.bNext.mouseOverButton(p5) && gui.bNext.isEnabled() ||
                        gui.bPrev.mouseOverButton(p5) && gui.bPrev.isEnabled() ||
                        gui.bDetalle.updateHandCursor(p5) && gui.pcMusica.selectedCard != -1;
                break;
            case CONCIERTOS:
                hand = hand ||
                        gui.rBPlus.updateHandCursor(p5) && gui.rBPlus.enabled ||
                        gui.rBFilter.updateHandCursor(p5) && gui.rBFilter.enabled ||
                        gui.rBHeart.mouseOverButton(p5) && gui.rBHeart.enabled ||
                        gui.bNext.mouseOverButton(p5) && gui.bNext.isEnabled() ||
                        gui.bPrev.mouseOverButton(p5) && gui.bPrev.isEnabled() ||
                        gui.bDetalle.updateHandCursor(p5) && gui.pcConcert.selectedCard != -1;
                break;
            case ESTADISTICAS:
                hand = hand ||
                        gui.bNext.mouseOverButton(p5) && gui.bNext.isEnabled() ||
                        gui.bPrev.mouseOverButton(p5) && gui.bPrev.isEnabled() ||
                        gui.bCatVinilos.updateHandCursor(p5) ||
                        gui.bCatCDs.updateHandCursor(p5) ||
                        gui.bCatConciertos.updateHandCursor(p5);
                break;
            case AGREGAR:
            case AGREGAR_CONCERT:
            case DETALLE:
            case DETALLE_CONCIERTO:
                hand = hand ||
                        gui.bOk.mouseOverButton(p5) && gui.bOk.isEnabled() ||
                        gui.bCancelar.mouseOverButton(p5) && gui.bCancelar.isEnabled() ||
                        gui.rBHeartAgregar.mouseOverButton(p5) && gui.rBHeartAgregar.enabled ||
                        gui.rBDelete.mouseOverButton(p5) && gui.rBDelete.enabled ||
                        gui.bLoadImage.mouseOverButton(p5) && !gui.popUpConfirmacionEliminar.visible ||
                        gui.bSaveImageToDB.mouseOverButton(p5) && !gui.popUpConfirmacionEliminar.visible ||
                        gui.cbl.checkCursor(p5) ||
                        gui.popUpConfirmacionEliminar.visible && gui.popUpConfirmacionEliminar.bAceptar.mouseOverButton(p5) ||
                        gui.popUpConfirmacionEliminar.visible && gui.popUpConfirmacionEliminar.bCancelar.mouseOverButton(p5);
                break;
            default:
                break;
        }

        if (hand) cursor(HAND);
        else cursor(ARROW);
    }

    // Cargar Imagen
    public void fileSelected(File selection) {
        if (selection == null) {
            println("Ninguna selección.");
        } else {
            // Referencia al documento imagen
            gui.file = selection;

            // Obtenemos la ruta del documento seleccionado
            String rutaImatge = selection.getAbsolutePath();

            gui.imgElegida = loadImage(rutaImatge);  // Actualizamos imatge
            gui.titol = selection.getName();  // Actualizamos título (igual)
        }
    }
    public void fileSelectedPerfil(File selection) {
        if (selection == null) {
            println("Ninguna selección.");
        } else {
            gui.filePerfil = selection;
            gui.imgPerfil = loadImage(selection.getAbsolutePath());
            gui.titolPerfil = selection.getName();
            copiar(selection, gui.rutaCarpeta, gui.titolPerfil);
            db.actualizarFotoPerfilUsuario(gui.usuarioActual, gui.titolPerfil);
        }
    }

    // Copia un documento en otra ubicación
    public void copiar(File file, String rutaCopia, String titol){
        Path original = Paths.get(file.getAbsolutePath());
        Path copia    = Paths.get(rutaCopia+"/"+titol);
        try{
            Files.copy(original, copia);
            println("OK: documento copiado en la carpeta.");
        } catch (IOException e) {
            println("ERROR: No se ha podido copiar el documento.");
        }
    }
}