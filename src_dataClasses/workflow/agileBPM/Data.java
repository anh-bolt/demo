package workflow.agileBPM;

/**
 */
@SuppressWarnings("all")
@javax.annotation.processing.Generated(comments="This is the java file of the ivy data class Data", value={"ch.ivyteam.ivy.scripting.streamInOut.IvyScriptJavaClassBuilder"})
public class Data extends ch.ivyteam.ivy.scripting.objects.CompositeObject
{
  /** SerialVersionUID */
  private static final long serialVersionUID = 8667013655859057117L;

  private ch.ivyteam.ivy.scripting.objects.List<workflow.agileBPM.TaskDef> definedTasks;

  /**
   * Gets the field definedTasks.
   * @return the value of the field definedTasks; may be null.
   */
  public ch.ivyteam.ivy.scripting.objects.List<workflow.agileBPM.TaskDef> getDefinedTasks()
  {
    return definedTasks;
  }

  /**
   * Sets the field definedTasks.
   * @param _definedTasks the new value of the field definedTasks.
   */
  public void setDefinedTasks(ch.ivyteam.ivy.scripting.objects.List<workflow.agileBPM.TaskDef> _definedTasks)
  {
    definedTasks = _definedTasks;
  }

  private workflow.agileBPM.CaseDef caseInfo;

  /**
   * Gets the field caseInfo.
   * @return the value of the field caseInfo; may be null.
   */
  public workflow.agileBPM.CaseDef getCaseInfo()
  {
    return caseInfo;
  }

  /**
   * Sets the field caseInfo.
   * @param _caseInfo the new value of the field caseInfo.
   */
  public void setCaseInfo(workflow.agileBPM.CaseDef _caseInfo)
  {
    caseInfo = _caseInfo;
  }

  private workflow.agileBPM.TaskDef nextTask;

  /**
   * Gets the field nextTask.
   * @return the value of the field nextTask; may be null.
   */
  public workflow.agileBPM.TaskDef getNextTask()
  {
    return nextTask;
  }

  /**
   * Sets the field nextTask.
   * @param _nextTask the new value of the field nextTask.
   */
  public void setNextTask(workflow.agileBPM.TaskDef _nextTask)
  {
    nextTask = _nextTask;
  }

  private java.lang.String ok;

  /**
   * Gets the field ok.
   * @return the value of the field ok; may be null.
   */
  public java.lang.String getOk()
  {
    return ok;
  }

  /**
   * Sets the field ok.
   * @param _ok the new value of the field ok.
   */
  public void setOk(java.lang.String _ok)
  {
    ok = _ok;
  }

  private java.lang.String answer;

  /**
   * Gets the field answer.
   * @return the value of the field answer; may be null.
   */
  public java.lang.String getAnswer()
  {
    return answer;
  }

  /**
   * Sets the field answer.
   * @param _answer the new value of the field answer.
   */
  public void setAnswer(java.lang.String _answer)
  {
    answer = _answer;
  }

  private ch.ivyteam.ivy.scripting.objects.DateTime started;

  /**
   * Gets the field started.
   * @return the value of the field started; may be null.
   */
  public ch.ivyteam.ivy.scripting.objects.DateTime getStarted()
  {
    return started;
  }

  /**
   * Sets the field started.
   * @param _started the new value of the field started.
   */
  public void setStarted(ch.ivyteam.ivy.scripting.objects.DateTime _started)
  {
    started = _started;
  }

  private ch.ivyteam.ivy.scripting.objects.List<workflow.agileBPM.TaskDef> additionalTasks;

  /**
   * Gets the field additionalTasks.
   * @return the value of the field additionalTasks; may be null.
   */
  public ch.ivyteam.ivy.scripting.objects.List<workflow.agileBPM.TaskDef> getAdditionalTasks()
  {
    return additionalTasks;
  }

  /**
   * Sets the field additionalTasks.
   * @param _additionalTasks the new value of the field additionalTasks.
   */
  public void setAdditionalTasks(ch.ivyteam.ivy.scripting.objects.List<workflow.agileBPM.TaskDef> _additionalTasks)
  {
    additionalTasks = _additionalTasks;
  }

}
