from thrift import Thrift
from thrift.protocol import TCompactProtocol
from thrift.server import TServer
from thrift.transport import TSocket, TTransport

from py.thrift.generated import PersonService
from src.main.PersonServiceImpl import PersonServiceImpl

try:
    personServiceHandler = PersonServiceImpl()
    processor = PersonService.Processor(personServiceHandler)

    serverSocket = TSocket.TServerSocket(port=8888)
    transportFactory = TTransport.TFramedTransportFactory()
    protocolFactory = TCompactProtocol.TCompactProtocolFactory()

    server = TServer.TThreadPoolServer(processor, serverSocket, transportFactory, protocolFactory)
    server.serve()

except Thrift.TException as ex:
    print(ex.message)
