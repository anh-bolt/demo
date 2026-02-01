package workflow.signal;

/**
 */
@SuppressWarnings("all")
@javax.annotation.processing.Generated(comments="This is the java file of the ivy data class QuitUserProcess", value={"ch.ivyteam.ivy.scripting.streamInOut.IvyScriptJavaClassBuilder"})
public class QuitUserProcess extends ch.ivyteam.ivy.scripting.objects.CompositeObject
{
  /** SerialVersionUID */
  private static final long serialVersionUID = 5022456867135972352L;

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
