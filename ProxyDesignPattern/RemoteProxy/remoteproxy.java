package ProxyDesignPattern.RemoteProxy;

interface iDataService{
    String fetchdata(); 
}

class ReadDataService implements iDataService{
    public ReadDataService(){
        System.out.println("Connecting to the database...");
    }

    @Override
    public String fetchdata() {
        System.out.println("Fetching data from the database...");
        return "Data fetched from the database.";
    }
}

class DataserviceProxy implements iDataService{
    private ReadDataService readDataService;
    private boolean isConnected;

  

    public DataserviceProxy(boolean isConnected){
        this.isConnected = isConnected;
        if(isConnected){
            this.readDataService = new ReadDataService();
        }
    }

    @Override
    public String fetchdata() {
        if(isConnected){
            return readDataService.fetchdata();
        } else {
            return "Access Denied: Not connected to the database.";
        }
    }
}

public class remoteproxy {
    public static void main(String[] args) {
        iDataService connectedProxy = new DataserviceProxy(true);
        System.out.println(connectedProxy.fetchdata());

        System.out.println();

        iDataService disconnectedProxy = new DataserviceProxy(false);
        System.out.println(disconnectedProxy.fetchdata());
    }
}
