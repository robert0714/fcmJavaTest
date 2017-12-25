package develop.odata.fcm.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
 
import develop.odata.fcm.domain.FcmRecord;
import develop.odata.fcm.endpoint.FcmRequest;

@RepositoryRestResource(collectionResourceRel = "fcmRecord", path = "fcmRecord")
public interface IFcmRecordRepository extends MongoRepository<FcmRecord, FcmRequest>, QueryByExampleExecutor<FcmRecord> {

	 
	List<FcmRecord> findBydoneIsFalse();
//	List<FcmRecord> findBysendDatasendDateGreaterThan ();
}
