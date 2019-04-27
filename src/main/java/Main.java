import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.BsonDocument;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.jongo.Jongo;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Main {

    public static void main(String[] args) {
        //Create Client for MongoDB
        MongoClient client = new MongoClient();

        //Map Java DTO's to JSON
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        //Fetch database with Mapper
        MongoDatabase database = client.getDatabase("javaMongoDB").withCodecRegistry(pojoCodecRegistry);

        MongoCollection<Person> personCollection = database.getCollection("persons", Person.class);
        Person brian = new Person();
        brian.setName("brian");
        personCollection.insertOne(brian);
        System.out.println("Person inserted");

        Person johnny = new Person();
        Address johaddress = new Address();

        johnny.setName("johnny");
        johaddress.setNumber(10);
        johaddress.setStreet("Vejlands Alle");
        johnny.setAddress(johaddress);
        personCollection.insertOne(johnny);
        System.out.println("Johnny inserterd");

        FindIterable<Person> people = personCollection.find(eq("name", "johnny"));
        Person retrievedPerson = people.first();
        System.out.println("Found: " + (retrievedPerson != null ? retrievedPerson.getName() : null));


    }
}
