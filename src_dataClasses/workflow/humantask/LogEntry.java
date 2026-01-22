package workflow.humantask;

/**
 */
@SuppressWarnings("all")
@javax.annotation.processing.Generated(comments="This is the java file of the ivy data class LogEntry", value={"ch.ivyteam.ivy.scripting.streamInOut.IvyScriptJavaClassBuilder"})
public class LogEntry extends ch.ivyteam.ivy.scripting.objects.CompositeObject
{
  /** SerialVersionUID */
  private static final long serialVersionUID = -2224668287177261176L;

  private workflow.humantask.User user;

  /**
   * Gets the field user.
   * @return the value of the field user; may be null.
   */
  public workflow.humantask.User getUser()
  {
    return user;
  }

  /**
   * Sets the field user.
   * @param _user the new value of the field user.
   */
  public void setUser(workflow.humantask.User _user)
  {
    user = _user;
  }

  private java.lang.String activity;

  /**
   * Gets the field activity.
   * @return the value of the field activity; may be null.
   */
  public java.lang.String getActivity()
  {
    return activity;
  }

  /**
   * Sets the field activity.
   * @param _activity the new value of the field activity.
   */
  public void setActivity(java.lang.String _activity)
  {
    activity = _activity;
  }

  private ch.ivyteam.ivy.scripting.objects.DateTime timestamp;

  /**
   * Gets the field timestamp.
   * @return the value of the field timestamp; may be null.
   */
  public ch.ivyteam.ivy.scripting.objects.DateTime getTimestamp()
  {
    return timestamp;
  }

  /**
   * Sets the field timestamp.
   * @param _timestamp the new value of the field timestamp.
   */
  public void setTimestamp(ch.ivyteam.ivy.scripting.objects.DateTime _timestamp)
  {
    timestamp = _timestamp;
  }

}
