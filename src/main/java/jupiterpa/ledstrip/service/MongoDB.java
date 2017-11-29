package jupiterpa.ledstrip.service;

import org.bson.Document;
import org.springframework.stereotype.Component;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;
import java.util.List;

import jupiterpa.ledstrip.model.LED;

@Component
public class MongoDB {
	MongoClient client = new MongoClient();
	MongoDatabase db = client.getDatabase("ledstrip");
	MongoCollection<Document> coll = db.getCollection("led");
	
	public MongoDB() { }

	public LED insert(LED led) {
		coll.insertOne(toDocument(led));
		return find(led.getRow(),led.getColumn());
	}
	public LED find(int row, int column) {
		FindIterable<Document> res = coll.find( and( eq("row",row), eq("column",column) ) );
		if (res.iterator().hasNext()) {
		  Document doc = res.first();
		  return toLED(doc);
		} else {
			return null;
		}
	}
	public List<LED> findAll() {
		FindIterable<Document> res = coll.find( );
		ArrayList<LED> list = new ArrayList<LED>();
		MongoCursor<Document> i = res.iterator();
		while (i.hasNext()) {
			list.add(toLED(i.next()));
		}
		return list;
		
	}
	public LED update(LED led) {
		coll.findOneAndReplace( and( eq("row",led.getRow()), eq("column",led.getColumn()) ),
				        toDocument(led) );
		return find(led.getRow(),led.getColumn());
	}
	
	Document toDocument(LED led) {
		Document doc = 
		  new Document("row",led.getRow())
		      .append("column", led.getColumn())
		      .append("red", led.getRed())
		      .append("green", led.getGreen())
		      .append("blue", led.getBlue());
		return doc;
	}
	LED toLED(Document doc) {
		LED led = 
			new LED(
				doc.getInteger("row"),
				doc.getInteger("column"),
				doc.getInteger("red"),
				doc.getInteger("green"),
				doc.getInteger("blue"));
		return led;
	}
}
