import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class ClienteFTP {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        FTPClient cliente=new FTPClient();
        String serverFTP="192.168.146.148";
        System.out.println("Conectando con el servidor");
        try {
            cliente.connect(serverFTP);
            System.out.print("Introduce el usuario: ");
            String usuario = teclado.nextLine();
            if(!usuario.equals("anonymous")) {
                System.out.print("Introduce la contraseña: ");
                String pass = teclado.nextLine();
                boolean login = cliente.login(usuario, pass);

                if(login) {
                    System.out.println(cliente.getReplyString());
                    int codigo = cliente.getReplyCode();
                    if (FTPReply.isPositiveCompletion(codigo)) {
                        int cod = -1;
                        while(cod != 0) {
                            System.out.println("\n\t1. Listar archivos\n\t2. Descargar archivos\n\t3. Subir archivos\n\t0. Salir");
                            System.out.print("Escoge una opción: ");
                            cod = teclado.nextInt();

                            if(cod == 1){
                                FTPFile[] files = cliente.listFiles("./");
                                for (FTPFile f: files){
                                    System.out.println(f.getName());
                                }
                            }else if(cod == 2){
                                System.out.print("Que archivo desea descargar: ");
                                teclado.nextLine();
                                String archivo = teclado.nextLine();
                                FileOutputStream output = new FileOutputStream(archivo);
                                cliente.setFileType(FTP.BINARY_FILE_TYPE);
                                cliente.enterLocalActiveMode();

                                if (!cliente.retrieveFile(archivo, output)) {
                                    System.out.println("Descarga fallida!");
                                }else{
                                    System.out.println("Se descargo correctamente el archivo");
                                }

                            }else if(cod == 3) {
                                System.out.print("Que archivo desea subir: ");
                                teclado.nextLine();
                                String ruta = teclado.nextLine();
                                File file = new File(ruta);
                                if(file.exists()) {
                                    FileInputStream input = new FileInputStream(file);
                                    cliente.setFileType(FTP.BINARY_FILE_TYPE);
                                    cliente.enterLocalActiveMode();

                                    if (!cliente.storeFile(file.getName(), input)) {
                                        System.out.println("Subida fallida!");
                                    }else{
                                        System.out.println("Subió satisfactoriamente el archivo");
                                    }
                                }else{
                                    System.out.println("El archivo no existe");
                                }

                            }
                        }
                    }
                }else{
                    System.out.println("Usuario y o contraseña incorrectos");
                }
            }else{
                System.out.print("Introduce la contraseña: ");
                String pass = teclado.nextLine();
                boolean login = cliente.login(usuario, "");

                if(login) {
                    System.out.println(cliente.getReplyString());
                    int codigo = cliente.getReplyCode();
                    if (FTPReply.isPositiveCompletion(codigo)) {
                        int cod = -1;
                        while(cod != 0) {
                            System.out.println("\n\t1. Listar archivos\n\t2. Descargar archivos\n\t0. Salir");
                            System.out.print("Escoge una opción: ");
                            cod = teclado.nextInt();

                            if(cod == 1){
                                FTPFile[] files = cliente.listFiles("./");
                                for (FTPFile f: files){
                                    System.out.println(f.getName());
                                }
                            }else if(cod == 2){
                                System.out.print("Que archivo desea descargar: ");
                                teclado.nextLine();
                                String archivo = teclado.nextLine();
                                FileOutputStream output = new FileOutputStream(archivo);
                                cliente.setFileType(FTP.BINARY_FILE_TYPE);
                                cliente.enterLocalActiveMode();

                                if (!cliente.retrieveFile(archivo, output)) {
                                    System.out.println("Subida fallida!");
                                }else{
                                    System.out.println("Se descargo correctamente el archivo");
                                }
                            }
                        }
                    }
                }else{
                    System.out.println("Usuario y o contraseña incorrectos");
                }
            }
            cliente.disconnect();
            System.out.println("fin de la conexión");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
