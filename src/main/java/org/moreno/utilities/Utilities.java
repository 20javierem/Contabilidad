package org.moreno.utilities;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import org.moreno.App;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class Utilities {
    private static TrayIcon mainTrayIcon;
    private static DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
    private static DefaultTableCellRenderer izquierda = new DefaultTableCellRenderer();
    private static Image trayIconImage = Toolkit.getDefaultToolkit().createImage(App.class.getResource("Icons/x32/fedora.png"));
    private static SystemTray mainTray;
    private static boolean primera=true;
    public static DateFormat formatoFecha=new SimpleDateFormat("dd-MM-yyyy");
    public static DateFormat formatoFechaHora=new SimpleDateFormat("dd-MM-yyyy : hh:mm a");
    public static DateFormat formatoHora=new SimpleDateFormat("HH:mm a");
    public static DateFormat año=new SimpleDateFormat("yyyy");
    public static NumberFormat moneda = NumberFormat.getCurrencyInstance();
    public static String getFormatoFecha(){
        return "dd-MM-yyyy";
    }

    public static void tema(String tema){
        try {
            switch (tema){
                case "genome":
                    UIManager.setLookAndFeel(new FlatIntelliJLaf());
                    break;
                default:
                    UIManager.setLookAndFeel(new FlatDarkLaf());
                    break;
            }
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

    }

    public static boolean precioEsValido(KeyEvent e, String precio){
        int caracter = e.getKeyChar();
        if(caracter==46){
            if(precio.lastIndexOf('.')==-1){
                if(precio.length()>0){
                    return true;
                }
                return false;
            }else{
                return false;
            }
        }else{
            if(caracter >= 48 && caracter <= 57){
                return true;
            }else{
                return false;
            }
        }
    }

    public static void buttonSelectedOrEntered(JButton boton){
            boton.setBackground(Colors.buttonSelected1);
    }

    public static void buttonSelectedOrEntered2(JButton boton){
        boton.setBackground(Colors.buttonSelected2);
    }

    public static void despintarButton(JButton boton){
        boton.setForeground(new JButton().getForeground());
        boton.setBackground(new JButton().getBackground());
    }
    public static void buttonExited(JButton boton){
        boton.setBackground(Colors.buttonExited1);
    }

    public static Date convertLocalTimeToDate(LocalTime time) {
        Instant instant = time.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant();
        return toDate(instant);
    }

    private static Date toDate(Instant instant) {
        BigInteger millis = BigInteger.valueOf(instant.getEpochSecond()).multiply(
                BigInteger.valueOf(1000));
        millis = millis.add(BigInteger.valueOf(instant.getNano()).divide(
                BigInteger.valueOf(1_000_000)));
        return new Date(millis.longValue());
    }

    public static Date localDateToDate(LocalDate dateToConvert) {
        return Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public static LocalDate dateToLocalDate(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static String getCodeOfName(String nameProduct){
        nameProduct=nameProduct.trim();
        boolean stade=true;
        String code="";
        if(!nameProduct.isEmpty()){
            int aux;
            do {
                code+=nameProduct.substring(0,1);
                aux=nameProduct.indexOf(" ");
                if(aux!=-1){
                    nameProduct=nameProduct.substring(aux+1);
                }else{
                    stade=false;
                }
            }while (stade);
        }
        return code;
    }

    public static void sendNotification(String title, String subtitle, TrayIcon.MessageType tipoMensaje) {
        if(isWindows(System.getProperty("os.name"))){
            if(primera){
                instanciar();
            }
            if(mainTray.getTrayIcons().length==0){
                try {
                    mainTray.add(mainTrayIcon);
                    mainTrayIcon.setToolTip("Gestor de notificaciones");
                } catch (AWTException e) {
                    e.printStackTrace();
                }
            }
            mainTrayIcon.setImageAutoSize(true);
            try {
                mainTrayIcon.displayMessage(title,  subtitle, tipoMensaje);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void instanciar(){
        mainTray = SystemTray.getSystemTray();
        mainTrayIcon=new TrayIcon(trayIconImage);
        primera=false;
    }

    public static boolean isWindows(String OS) {
        return (OS.indexOf("Win") >= 0);
    }

    public static Vector invertirVector(Vector vector){
        Object ventaAUX;
        for(int i=0;i<vector.size()/2;i++){
            ventaAUX=vector.get(i);
            vector.set(i, vector.get(vector.size() - i-1));
            vector.set((vector.size()-i-1), ventaAUX);
        }
        return vector;
    }

    public static Integer calcularaños(Date fecha){
        Calendar hoy=Calendar.getInstance();
        Calendar nacimiento=Calendar.getInstance();
        nacimiento.setTime(fecha);
        int años= hoy.get(Calendar.YEAR)-nacimiento.get(Calendar.YEAR);
        int meses= hoy.get(Calendar.MONTH)-nacimiento.get(Calendar.MONTH);
        int dias= hoy.get(Calendar.DAY_OF_MONTH)-nacimiento.get(Calendar.DAY_OF_MONTH);
        switch (meses){
            case 0:
                if(dias<0){
                    años-=1;
                }
                break;
            default:
                if(meses<0){
                    años-=1;
                }
        }
        return años<0?0:años;
    }

    public static SecretKeySpec getSecretKey(){
        Propierties propiedades=new Propierties();
        byte[] salt = "12345678".getBytes();
        int iterationCount = 40000;
        int keyLength = 128;
        try {
            return createSecretKey(propiedades.getKey().toCharArray(),salt,iterationCount,keyLength);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static SecretKeySpec createSecretKey(char[] password, byte[] salt, int iterationCount, int keyLength) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterationCount, keyLength);
        SecretKey keyTmp = keyFactory.generateSecret(keySpec);
        return new SecretKeySpec(keyTmp.getEncoded(), "AES");
    }
    public static String encriptar(String contraseña){
        return encrypt(contraseña,getSecretKey());
    }
    public static String desencriptar(String contraseña){
        return decrypt(contraseña,getSecretKey());
    }
    public static String encrypt(String dataToEncrypt, SecretKeySpec key) {
        try {
            Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            pbeCipher.init(Cipher.ENCRYPT_MODE, key);
            AlgorithmParameters parameters = pbeCipher.getParameters();
            IvParameterSpec ivParameterSpec = parameters.getParameterSpec(IvParameterSpec.class);
            byte[] cryptoText = pbeCipher.doFinal(dataToEncrypt.getBytes("UTF-8"));
            byte[] iv = ivParameterSpec.getIV();
            return base64Encode(iv) + ":" + base64Encode(cryptoText);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error, notifique al administrador");
        }
        return null;
    }
    private static String base64Encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static String decrypt(String string, SecretKeySpec key) {
        try {
            String iv = string.split(":")[0];
            String property = string.split(":")[1];
            Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            pbeCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(base64Decode(iv)));
            return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] base64Decode(String property) throws IOException {
        return Base64.getDecoder().decode(property);
    }
}
