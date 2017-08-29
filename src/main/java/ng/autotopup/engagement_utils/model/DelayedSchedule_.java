package ng.autotopup.engagement_utils.model;

import java.sql.Timestamp;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-05-26T14:40:53.854+0100")
@StaticMetamodel(DelayedSchedule.class)
public class DelayedSchedule_ extends JEntity_ {
	public static volatile SingularAttribute<DelayedSchedule, String> message;
	public static volatile SingularAttribute<DelayedSchedule, String> msisdn;
	public static volatile SingularAttribute<DelayedSchedule, Timestamp> timestamp;
	public static volatile SingularAttribute<DelayedSchedule, Boolean> engaged;
}
