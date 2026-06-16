package AdapterDesignPattern;

interface report{
    //String getjsonreport();

    String getjsonreport(String data);
}


//Adaptee
class xmlreportprovider{

    String getxmlreport(String data){
        int sep = data.indexOf(":");
        String name = data.substring(0, sep);
        String id = data.substring(sep + 1);
        return "<report><name>" + name + "</name><id>" + id + "</id></report>";
    }
}

//Adapter
class xmlreportadapter implements report{
    private xmlreportprovider xmlProvider;

    public xmlreportadapter(xmlreportprovider xmlProvider) {
        this.xmlProvider = xmlProvider;
    }

    @Override
    public String getjsonreport(String data) {
        String xmlData = xmlProvider.getxmlreport(data);
        // Convert XML to JSON (simplified for demonstration)
        String name = xmlData.substring(xmlData.indexOf("<name>") + 6, xmlData.indexOf("</name>"));
        String id = xmlData.substring(xmlData.indexOf("<id>") + 4, xmlData.indexOf("</id>"));
        return "{\"name\":\"" + name + "\", \"id\":\"" + id + "\"}";
    }

    public String getjsonreport() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getjsonreport'");
    }
}


class client{
    public void generateReport(report report, String data) {
        String jsonReport = report.getjsonreport(data);
        System.out.println("Generated JSON Report: " + jsonReport);
    }
}

public class adapterdesignpattern {
    public static void main(String[] args) {
        xmlreportprovider xmlProvider = new xmlreportprovider();
        report adapter = new xmlreportadapter(xmlProvider);

        String data = "Akhil:180360";
        client client = new client();

        client.generateReport(adapter, data);
        //client.generateReport(adapter);
    }

}
