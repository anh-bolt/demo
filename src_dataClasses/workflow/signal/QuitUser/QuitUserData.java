package workflow.signal.QuitUser;

/**
 */
@SuppressWarnings("all")
@javax.annotation.processing.Generated(comments="This is the java file of the ivy data class QuitUserData", value={"ch.ivyteam.ivy.scripting.streamInOut.IvyScriptJavaClassBuilder"})
public class QuitUserData extends ch.ivyteam.ivy.scripting.objects.CompositeObject
{
  /** SerialVersionUID */
  private static final long serialVersionUID = 6418332396399011273L;

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

  private transient ch.ivyteam.ivy.scripting.objects.List<ch.ivyteam.ivy.workflow.ICase> cases;

  /**
   * Gets the field cases.
   * @return the value of the field cases; may be null.
   */
  public ch.ivyteam.ivy.scripting.objects.List<ch.ivyteam.ivy.workflow.ICase> getCases()
  {
    return cases;
  }

  /**
   * Sets the field cases.
   * @param _cases the new value of the field cases.
   */
  public void setCases(ch.ivyteam.ivy.scripting.objects.List<ch.ivyteam.ivy.workflow.ICase> _cases)
  {
    cases = _cases;
  }

}
