import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import thrift.generated.PersonService;
import thrift.generated.StudentService;

/**
 * @author 15510
 * @create 2019-06-29 18:08
 */
public class JavaServer {
    public static void main(
        String[] args) throws Exception {
        TNonblockingServerSocket serverSocket = new TNonblockingServerSocket(8888);
        THsHaServer.Args arg = new THsHaServer.Args(serverSocket).minWorkerThreads(2).maxWorkerThreads(4);

        PersonService.Processor<PersonServiceImpl> personProcessor = new PersonService.Processor<>(new PersonServiceImpl());
        StudentService.Processor<StudentServiceImpl> studentProcessor = new StudentService.Processor<>(new StudentServiceImpl());

        //将服务注册到TMultiplexedProcessor中
        TMultiplexedProcessor processors = new TMultiplexedProcessor();
        processors.registerProcessor("personService", personProcessor);
        processors.registerProcessor("studentService", studentProcessor);

        arg.protocolFactory(new TCompactProtocol.Factory());
        arg.transportFactory(new TFramedTransport.Factory());
        arg.processor(processors);


        TServer server = new THsHaServer(arg);

        System.out.println("Thrift Server Started!");

        server.serve();
    }
}
