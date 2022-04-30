import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Archiving {
    private static Archiving instance;
    private File file;
    private Archiving(){}
    public static Archiving getInstance(){
        if(instance == null){
            instance = new Archiving();
        }
        return instance;
    }


    public void Zip(String source_dir, String zip_file) throws Exception
    {

        FileOutputStream fout = new FileOutputStream(zip_file);
        // Определение кодировки для кириллицы
        ZipOutputStream zout = new ZipOutputStream(fout, Charset.forName("CP866"));

        // Создание объекта File object архивируемой директории
        File fileSource = new File(source_dir);

        addDirectory(zout, fileSource);

        zout.close();

        System.out.println("Zip файл создан!");

        File file  = new File("archiving_and_send_to_email.zip");
        File newFile = new File("archiving_and_send_to_email.tar");
        boolean success = file.renameTo(newFile);
        if (success) {
            System.out.println("Переимнование Zip файла в Tar файл прошло успешно!");
            System.out.println(newFile.getName());
        }


        EmailImpl.getInstance().sendEmail(newFile.getName());
    }


    private void addDirectory(ZipOutputStream zout, File fileSource)
            throws Exception
    {
        File[] files = fileSource.listFiles();
        System.out.println("Добавление директории <" + fileSource.getName() + ">");
        for(int i = 0; i < files.length; i++) {
            // Если file является директорией, то рекурсивно вызываем
            // метод addDirectory
            if(files[i].isDirectory()) {
                addDirectory(zout, files[i]);
                continue;
            }
            System.out.println("Добавление файла <" + files[i].getName() + ">");

            FileInputStream fis = new FileInputStream(files[i]);

            zout.putNextEntry(new ZipEntry(files[i].getPath()));

            byte[] buffer = new byte[4048];
            int length;
            while((length = fis.read(buffer)) > 0)
                zout.write(buffer, 0, length);

            zout.closeEntry();
            fis.close();
        }
    }
}
