package workflow.businessdata.ModifyCountry;

/**
 */
@SuppressWarnings("all")
@javax.annotation.processing.Generated(comments="This is the java file of the ivy data class ModifyCountryData", value={"ch.ivyteam.ivy.scripting.streamInOut.IvyScriptJavaClassBuilder"})
public class ModifyCountryData extends ch.ivyteam.ivy.scripting.objects.CompositeObject
{
  /** SerialVersionUID */
  private static final long serialVersionUID = -4657575840644140167L;

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

  private java.lang.String addressId;

  /**
   * Gets the field addressId.
   * @return the value of the field addressId; may be null.
   */
  public java.lang.String getAddressId()
  {
    return addressId;
  }

  /**
   * Sets the field addressId.
   * @param _addressId the new value of the field addressId.
   */
  public void setAddressId(java.lang.String _addressId)
  {
    addressId = _addressId;
  }

}
