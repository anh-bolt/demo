package workflow.signal;

/**
 */
@SuppressWarnings("all")
@javax.annotation.processing.Generated(comments="This is the java file of the ivy data class CreateUserProcess", value={"ch.ivyteam.ivy.scripting.streamInOut.IvyScriptJavaClassBuilder"})
public class CreateUserProcess extends ch.ivyteam.ivy.scripting.objects.CompositeObject
{
  /** SerialVersionUID */
  private static final long serialVersionUID = -1871537594296973365L;

  private workflow.signal.User user;

  /**
   * Gets the field user.
   * @return the value of the field user; may be null.
   */
  public workflow.signal.User getUser()
  {
    return user;
  }

  /**
   * Sets the field user.
   * @param _user the new value of the field user.
   */
  public void setUser(workflow.signal.User _user)
  {
    user = _user;
  }

  private workflow.signal.QuitUserEvent quitUserEvent;

  /**
   * Gets the field quitUserEvent.
   * @return the value of the field quitUserEvent; may be null.
   */
  public workflow.signal.QuitUserEvent getQuitUserEvent()
  {
    return quitUserEvent;
  }

  /**
   * Sets the field quitUserEvent.
   * @param _quitUserEvent the new value of the field quitUserEvent.
   */
  public void setQuitUserEvent(workflow.signal.QuitUserEvent _quitUserEvent)
  {
    quitUserEvent = _quitUserEvent;
  }

}
