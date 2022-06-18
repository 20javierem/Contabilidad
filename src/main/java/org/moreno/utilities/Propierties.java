package org.moreno.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Propierties {
    private Properties properties;
    private FileInputStream inputStream;
    private File carpeta = new File(System.getProperty("user.home") + "/.Devel");
    private File archivo;

    public Propierties(){
        try {
            inicializar();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void inicializar() throws IOException {
        if (!carpeta.exists()) {
            System.out.println("Primera ves");
            carpeta.mkdir();
        }
        archivo= new File(carpeta.getAbsolutePath()+"/config.properties");
        if(!archivo.exists()){
            System.out.println("Primera ves");
            archivo.createNewFile();
            inputStream = new FileInputStream(archivo.getAbsolutePath());
            properties= new Properties();
            properties.load(inputStream);
            inputStream.close();
            setKey("2QXDJJUCSSUW2GC");
            guardar();
            setUsuario("");
            setActualizado(true);
            setContraseña("");
            setEstadoImprecion("preguntar");
            setTema("genome");
            guardar();
        }else{
            inputStream = new FileInputStream(archivo.getAbsolutePath());
            properties= new Properties();
            properties.load(inputStream);
            inputStream.close();
        }
    }

    public void guardar() {
        try {
            FileOutputStream outputStream = new FileOutputStream(archivo);
            properties.store(outputStream, "[Config file]");
            outputStream.flush();
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void setUsuario(String usuario) {
        properties.put("usuario", usuario);
    }
    public String getUsuario() {
        return properties.getProperty("usuario");
    }
    public void setActualizado(boolean estado) {
        properties.put("Actualizado", String.valueOf(estado));
    }
    public boolean getActualizado() {
        return Boolean.parseBoolean(properties.getProperty("Actualizado"));
    }
    public void setContraseña(String contraseña){
        properties.put("contrasena",contraseña);
    }
    public void setEstadoImprecion(String preguntar){
        properties.put("estadoImprimir",preguntar);
    }
    public String getEstadoImprecion() {
        return properties.getProperty("estadoImprimir");
    }
    public String getContraseña(){
        return properties.getProperty("contrasena");
    }
    public String getKey() {
        return properties.getProperty("key");
    }
    public void setKey(String key){
        properties.put("key",key);
    }
    public void setTokenApiPeru(String token){
        properties.put("TokenApiPeru",token);
    }
    public String getTokenApiperu() {
        return properties.getProperty("TokenApiPeru");
    }
    public void setTema(String tema){
        properties.put("tema", tema);
    }
    public String getTema() {
        return properties.getProperty("tema");
    }
}
