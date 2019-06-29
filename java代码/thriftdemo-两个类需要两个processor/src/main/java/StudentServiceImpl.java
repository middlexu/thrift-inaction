import org.apache.thrift.TException;
import thrift.generated.DataException;
import thrift.generated.Student;
import thrift.generated.StudentService;

/**
 * @author 15510
 * @create 2019-06-29 20:22
 */
public class StudentServiceImpl implements StudentService.Iface {
    @Override
    public Student getStudentByUsername(String username) throws DataException, TException {
        System.out.println("Got client param: " + username);
        Student student = new Student();
        student.setId(22);
        student.setUsername(username);
        student.setAge(20);

        return student;
    }
}
