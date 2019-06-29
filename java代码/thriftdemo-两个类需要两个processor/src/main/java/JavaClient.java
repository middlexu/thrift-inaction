import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import thrift.generated.Person;
import thrift.generated.PersonService;
import thrift.generated.Student;
import thrift.generated.StudentService;

/**
 * @author 15510
 * @create 2019-06-29 18:13
 */
public class JavaClient {
    public static void main(String[] args) {
        TTransport transport = new TFramedTransport(new TSocket("localhost", 8888), 600);
        TProtocol protocol = new TCompactProtocol(transport);

        //通过TMultiplexedProtocol获取对应的服务
        TMultiplexedProtocol protocolPerson = new TMultiplexedProtocol(protocol, "personService");
        TMultiplexedProtocol protocolStudent = new TMultiplexedProtocol(protocol, "studentService");

        PersonService.Client personClient = new PersonService.Client(protocolPerson);

        StudentService.Client studentClient = new StudentService.Client(protocolStudent);
        try {
            transport.open();

            Person person = personClient.getPersonByUsername("张三");
            System.out.println(person.getUsername());
            System.out.println(person.getAge());
            System.out.println(person.isMarried());

            System.out.println("------------");

            Person person1 = new Person();
            person1.setUsername("李四");
            person1.setAge(30);
            person1.setMarried(true);
            personClient.savePerson(person1);

            System.out.println("------------");
            Student student = studentClient.getStudentByUsername("老王");
            System.out.println(student.getUsername());
            System.out.println(student.getAge());
            System.out.println(student.getId());



        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            transport.close();
        }
    }

}
