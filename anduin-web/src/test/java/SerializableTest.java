import com.zobgo.web.demo.dto.PersonDTO;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @author zhangzongbo
 * @date 18-12-6 下午2:31
 */

@Slf4j
public class SerializableTest {

    public static void main(String[] args) {
        SerializePerson();

        DeSerialize();
    }

    private static void SerializePerson(){
        PersonDTO person = new PersonDTO();
        person.setId(1000L);
        person.setAge(19);
        person.setName("test");
        ObjectOutputStream outputStream = null;
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream("person.txt"));
            outputStream.writeObject(person);
            log.info("Serialize Success!");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static void DeSerialize(){
        ObjectInputStream inputStream = null;
        PersonDTO person = null;
        try {
            inputStream = new ObjectInputStream(new FileInputStream("person.txt"));
            try {
                person =(PersonDTO) inputStream.readObject();
                log.info("person: {}",person);
                log.info("DeSerialize Success");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
