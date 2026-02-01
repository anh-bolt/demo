package workflow.credit;

/**
 */
@SuppressWarnings("all")
@javax.annotation.processing.Generated(comments="This is the java file of the ivy data class Person", value={"ch.ivyteam.ivy.scripting.streamInOut.IvyScriptJavaClassBuilder"})
public class Person extends ch.ivyteam.ivy.scripting.objects.CompositeObject
{
  /** SerialVersionUID */
  private static final long serialVersionUID = 2144829440005204014L;

  private java.lang.String name;

  /**
   * Gets the field name.
   * @return the value of the field name; may be null.
   */
  public java.lang.String getName()
  {
    return name;
  }

  /**
   * Sets the field name.
   * @param _name the new value of the field name.
   */
  public void setName(java.lang.String _name)
  {
    name = _name;
  }

  private java.lang.String firstName;

  /**
   * Gets the field firstName.
   * @return the value of the field firstName; may be null.
   */
  public java.lang.String getFirstName()
  {
    return firstName;
  }

  /**
   * Sets the field firstName.
   * @param _firstName the new value of the field firstName.
   */
  public void setFirstName(java.lang.String _firstName)
  {
    firstName = _firstName;
  }

  private ch.ivyteam.ivy.scripting.objects.Date birthDate;

  /**
   * Gets the field birthDate.
   * @return the value of the field birthDate; may be null.
   */
  public ch.ivyteam.ivy.scripting.objects.Date getBirthDate()
  {
    return birthDate;
  }

  /**
   * Sets the field birthDate.
   * @param _birthDate the new value of the field birthDate.
   */
  public void setBirthDate(ch.ivyteam.ivy.scripting.objects.Date _birthDate)
  {
    birthDate = _birthDate;
  }

  private java.lang.Boolean isMale;

  /**
   * Gets the field isMale.
   * @return the value of the field isMale; may be null.
   */
  public java.lang.Boolean getIsMale()
  {
    return isMale;
  }

  /**
   * Sets the field isMale.
   * @param _isMale the new value of the field isMale.
   */
  public void setIsMale(java.lang.Boolean _isMale)
  {
    isMale = _isMale;
  }

  private workflow.credit.Address address;

  /**
   * Gets the field address.
   * @return the value of the field address; may be null.
   */
  public workflow.credit.Address getAddress()
  {
    return address;
  }

  /**
   * Sets the field address.
   * @param _address the new value of the field address.
   */
  public void setAddress(workflow.credit.Address _address)
  {
    address = _address;
  }

}
