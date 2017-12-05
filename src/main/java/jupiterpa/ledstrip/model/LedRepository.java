package jupiterpa.ledstrip.model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface LedRepository extends MongoRepository<Led, Long>{
	public Led findByRowAndColumn(int row,int column);
}
