package ng.autotopup.engagement_utils.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-05-25T18:13:38.888+0100")
@StaticMetamodel(JEntity.class)
public class JEntity_ {
	public static volatile SingularAttribute<JEntity, Long> pk;
	public static volatile SingularAttribute<JEntity, Boolean> deleted;
	public static volatile SingularAttribute<JEntity, Date> dateCreated;
	public static volatile SingularAttribute<JEntity, Date> dateModified;
}
