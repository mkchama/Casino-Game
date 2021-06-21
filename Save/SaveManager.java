/**
 *  Code from save management tutorial implemented
 *  @URL: https://www.youtube.com/watch?v=-xW0pBZqpjU
 */

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SaveManager {

    public static void save(Serializable data, String fileName) throws Exception
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName))))
            {
                oos.writeObject(data);
            }
    }

    public static void load(String fileName) throws Exception
    {
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(fileName))))
        {
            ois.readObject();
        }
    }

}