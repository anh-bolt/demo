package workflow.businessdata.BusinessDataDossierBrowser;

/**
 */
@SuppressWarnings("all")
@javax.annotation.processing.Generated(comments="This is the java file of the ivy data class BusinessDataDossierBrowserData", value={"ch.ivyteam.ivy.scripting.streamInOut.IvyScriptJavaClassBuilder"})
public class BusinessDataDossierBrowserData extends ch.ivyteam.ivy.scripting.objects.CompositeObject
{
  /** SerialVersionUID */
  private static final long serialVersionUID = 2725166342832178040L;

  private java.util.List<workflow.businessdata.Dossier> dossiers;

  /**
   * Gets the field dossiers.
   * @return the value of the field dossiers; may be null.
   */
  public java.util.List<workflow.businessdata.Dossier> getDossiers()
  {
    return dossiers;
  }

  /**
   * Sets the field dossiers.
   * @param _dossiers the new value of the field dossiers.
   */
  public void setDossiers(java.util.List<workflow.businessdata.Dossier> _dossiers)
  {
    dossiers = _dossiers;
  }

  private java.lang.String searchText;

  /**
   * Gets the field searchText.
   * @return the value of the field searchText; may be null.
   */
  public java.lang.String getSearchText()
  {
    return searchText;
  }

  /**
   * Sets the field searchText.
   * @param _searchText the new value of the field searchText.
   */
  public void setSearchText(java.lang.String _searchText)
  {
    searchText = _searchText;
  }

  private ch.ivyteam.ivy.scripting.objects.Date searchFromDate;

  /**
   * Gets the field searchFromDate.
   * @return the value of the field searchFromDate; may be null.
   */
  public ch.ivyteam.ivy.scripting.objects.Date getSearchFromDate()
  {
    return searchFromDate;
  }

  /**
   * Sets the field searchFromDate.
   * @param _searchFromDate the new value of the field searchFromDate.
   */
  public void setSearchFromDate(ch.ivyteam.ivy.scripting.objects.Date _searchFromDate)
  {
    searchFromDate = _searchFromDate;
  }

  private ch.ivyteam.ivy.scripting.objects.Date searchToDate;

  /**
   * Gets the field searchToDate.
   * @return the value of the field searchToDate; may be null.
   */
  public ch.ivyteam.ivy.scripting.objects.Date getSearchToDate()
  {
    return searchToDate;
  }

  /**
   * Sets the field searchToDate.
   * @param _searchToDate the new value of the field searchToDate.
   */
  public void setSearchToDate(ch.ivyteam.ivy.scripting.objects.Date _searchToDate)
  {
    searchToDate = _searchToDate;
  }

  private java.lang.String searchType;

  /**
   * Gets the field searchType.
   * @return the value of the field searchType; may be null.
   */
  public java.lang.String getSearchType()
  {
    return searchType;
  }

  /**
   * Sets the field searchType.
   * @param _searchType the new value of the field searchType.
   */
  public void setSearchType(java.lang.String _searchType)
  {
    searchType = _searchType;
  }

  private java.lang.String lastEditedId;

  /**
   * Gets the field lastEditedId.
   * @return the value of the field lastEditedId; may be null.
   */
  public java.lang.String getLastEditedId()
  {
    return lastEditedId;
  }

  /**
   * Sets the field lastEditedId.
   * @param _lastEditedId the new value of the field lastEditedId.
   */
  public void setLastEditedId(java.lang.String _lastEditedId)
  {
    lastEditedId = _lastEditedId;
  }

  private transient ch.ivyteam.ivy.business.data.store.search.Result searchResult;

  /**
   * Gets the field searchResult.
   * @return the value of the field searchResult; may be null.
   */
  public ch.ivyteam.ivy.business.data.store.search.Result getSearchResult()
  {
    return searchResult;
  }

  /**
   * Sets the field searchResult.
   * @param _searchResult the new value of the field searchResult.
   */
  public void setSearchResult(ch.ivyteam.ivy.business.data.store.search.Result _searchResult)
  {
    searchResult = _searchResult;
  }

  private java.lang.Number limit;

  /**
   * Gets the field limit.
   * @return the value of the field limit; may be null.
   */
  public java.lang.Number getLimit()
  {
    return limit;
  }

  /**
   * Sets the field limit.
   * @param _limit the new value of the field limit.
   */
  public void setLimit(java.lang.Number _limit)
  {
    limit = _limit;
  }

}
