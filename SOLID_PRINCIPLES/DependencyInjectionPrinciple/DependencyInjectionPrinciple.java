package SOLID_PRINCIPLES.DependencyInjectionPrinciple;

interface Database{
    void save(String data);
}

class MySQLDatabase implements Database{
    @Override
    public void save(String data) {
        System.out.println("Saving data to MySQL database: " + data);
    }
}

class MongoDBDatabase implements Database{
    @Override
    public void save(String data) {
        System.out.println("Saving data to MongoDB database: " + data);
    }
}

class userService{
    private Database database;

    public userService(Database database) {
        this.database = database;
    }

    public void saveUser(String userData) {
        database.save(userData);
    }
}


public class DependencyInjectionPrinciple {
    public static void main(String[] args) {
        Database mySQLDatabase = new MySQLDatabase();
        userService userService1 = new userService(mySQLDatabase);
        userService1.saveUser("User data for MySQL");

        Database mongoDBDatabase = new MongoDBDatabase();
        userService userService2 = new userService(mongoDBDatabase);
        userService2.saveUser("User data for MongoDB");
    }   
}
