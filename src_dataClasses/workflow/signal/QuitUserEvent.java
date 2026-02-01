package workflow.signal;

/**
 */
@SuppressWarnings("all")
@javax.annotation.processing.Generated(comments="This is the java file of the ivy data class QuitUserEvent", value={"ch.ivyteam.ivy.scripting.streamInOut.IvyScriptJavaClassBuilder"})
public class QuitUserEvent extends ch.ivyteam.ivy.scripting.objects.CompositeObject
{
  /** SerialVersionUID */
  private static final long serialVersionUID = 2924147474759816280L;

  private java.lang.String userKey;

  /**
   * Gets the field userKey.
   * @return the value of the field userKey; may be null.
   */
  public java.lang.String getUserKey()
  {
    return userKey;
  }

  /**
   * Sets the field userKey.
   * @param _userKey the new value of the field userKey.
   */
  public void setUserKey(java.lang.String _userKey)
  {
    userKey = _userKey;
  }

  private java.lang.String reason;

  /**
   * Gets the field reason.
   * @return the value of the field reason; may be null.
   */
  public java.lang.String getReason()
  {
    return reason;
  }

  /**
   * Sets the field reason.
   * @param _reason the new value of the field reason.
   */
  public void setReason(java.lang.String _reason)
  {
    reason = _reason;
  }

}
