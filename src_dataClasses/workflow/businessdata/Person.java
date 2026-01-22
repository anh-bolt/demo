package workflow.businessdata;

/**
 */
@SuppressWarnings("all")
@javax.annotation.processing.Generated(comments="This is the java file of the ivy data class Person", value={"ch.ivyteam.ivy.scripting.streamInOut.IvyScriptJavaClassBuilder"})
public class Person extends ch.ivyteam.ivy.scripting.objects.CompositeObject
{
  /** SerialVersionUID */
  private static final long serialVersionUID = 6529429058468667563L;

  private workflow.businessdata.Address address;

  /**
   * Gets the field address.
   * @return the value of the field address; may be null.
   */
  public workflow.businessdata.Address getAddress()
  {
    return address;
  }

  /**
   * Sets the field address.
   * @param _address the new value of the field address.
   */
  public void setAddress(workflow.businessdata.Address _address)
  {
    address = _address;
  }

  private java.lang.String lastName;

  /**
   * Gets the field lastName.
   * @return the value of the field lastName; may be null.
   */
  public java.lang.String getLastName()
  {
    return lastName;
  }

  /**
   * Sets the field lastName.
   * @param _lastName the new value of the field lastName.
   */
  public void setLastName(java.lang.String _lastName)
  {
    lastName = _lastName;
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

}
